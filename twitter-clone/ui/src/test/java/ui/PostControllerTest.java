package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.payload.response.PostResponse;
import core.payload.response.UserResponse;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testfx.api.FxAssert;
import org.testfx.util.WaitForAsyncUtils;
import persistence.OrmPostRepository;
import service.PostService;
import service.UserService;

/** Integration test for {@link PostController}. */
@SpringBootTest(classes = ui.config.AppConfig.class)
class PostControllerTest extends UiTestBase {
  @Autowired
  private PostService postService;

  @SuppressWarnings("unused")
  @Autowired
  private UserService userService;
  private UserResponse user;
  private PostResponse post;

  @BeforeEach
  void setupData() {
    OrmPostRepository postRepository = new OrmPostRepository();
    postRepository.dropDatabase();

    userService.logIn("username", "password123");
    user = userService.getLoggedInUser();
    post = postService.createPost("UI Test content", null);
    userService.logIn("username2", "password123");
    postService.createPost("retweet", post.id());
    userService.logIn("username", "password123");

  }

  @AfterEach
  void cleanupData() {
    new OrmPostRepository().dropDatabase();
  }

  @Test
  @DisplayName("Like button updates model and toggles color")
  void testLikeButtonClick() {
    typeIntoTextInput("#usernameField", "username");
    typeIntoTextInput("#passwordField", "password123");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();
    ListView<?> feedList = lookup("#feedList").queryAs(ListView.class);
    assertNotNull(feedList, "ListView should be loaded");
    WaitForAsyncUtils.waitForFxEvents();
    Object firstCell = feedList.getItems().get(0);
    assertNotNull(firstCell, "First post should exist");

    // like and check
    Button likeBtn = lookup("#likeBtn").nth(0).queryAs(Button.class);
    assertNotNull(likeBtn, "Like button should be loaded");
    assertEquals(0, post.likes());
    interact(likeBtn::fire);
    PostResponse updatedPost = postService.getPostById(post.id()).orElseThrow();
    assertEquals(1, updatedPost.likes());
    assertTrue(updatedPost.likedByUser(user.id()));
    Text likeBtnText = lookup("#likeBtnText").queryAs(Text.class);
    FxAssert.verifyThat(likeBtnText, b -> b.getStyle().contains("#0078ae"));

    // unlike and check
    interact(likeBtn::fire);
    updatedPost = postService.getPostById(post.id()).orElseThrow();
    assertEquals(0, updatedPost.likes());
    assertFalse(updatedPost.likedByUser(user.id()));
    FxAssert.verifyThat(likeBtnText, b -> !b.getStyle().contains("#0078ae"));
  }

  // Test written with help from OpenAI's GPT-5
  @Test
  @DisplayName("Second user likes a post already liked by first user")
  void testSecondUserLikesAlreadyLikedPost() {
    post = postService.likePost(post.id());
    assertEquals(1, post.likes(), "Post should already have 1 like from first user");

    typeIntoTextInput("#usernameField", "seconduser");
    typeIntoTextInput("#passwordField", "pass456");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    // like and check
    Button likeBtn = lookup("#likeBtn").nth(0).queryAs(Button.class);
    interact(likeBtn::fire);

    PostResponse updatedPost = postService.getPostById(post.id()).orElseThrow();
    assertEquals(2, updatedPost.likes(), "Post should now have 2 likes");

    userService.logIn("seconduser", "pass456");
    UserResponse secondUser = userService.getLoggedInUser();
    assertTrue(updatedPost.likedByUser(secondUser.id()),
        "Second user should be registered as liking the post");

    assertTrue(updatedPost.likedByUser(user.id()), "First user's like should still be counted");
    Text likeBtnText = lookup("#likeBtnText").queryAs(Text.class);
    FxAssert.verifyThat(likeBtnText, b -> b.getStyle().contains("#0078ae"));
  }

  @Test
  @DisplayName("Deletion of inner post in retweet")
  void testDeletionOfInnerPostInRetweet() {
    WaitForAsyncUtils.waitForFxEvents();
    typeIntoTextInput("#usernameField", "username");
    typeIntoTextInput("#passwordField", "password123");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();
    Button deleteBtn = lookup("#deleteOriginalBtn").nth(0).queryAs(Button.class);
    clickOn(deleteBtn);

    DialogPane pane = lookup(".dialog-pane").queryAs(DialogPane.class);
    assertNotNull(pane, "DialogPane should be present");

    Button yesBtn = from(pane)
        .lookup((Node n) -> n instanceof Button && "Yes".equals(((Button) n).getText()))
        .queryAs(Button.class);
    interact(yesBtn::fire);

    Label originalPostContentDeletedLabel = lookup("#contentLabel1").nth(0).queryAs(Label.class);
    assertTrue(originalPostContentDeletedLabel.getText().contains("deleted"),
        "Original post content should be marked as deleted");
  }
}
