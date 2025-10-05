package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Post;
import core.User;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.util.WaitForAsyncUtils;
import persistence.OrmPostRepository;
import persistence.OrmUserRepository;
import service.PostService;
import service.UserService;

/** Integration test for {@link PostController}. */
class PostControllerTest extends UiTestBase {

  private PostService postService;
  @SuppressWarnings("unused")
  private UserService userService;
  private User user;
  private Post post;

  @BeforeEach
  void setupData() {
    new OrmPostRepository().dropDatabase();
    new OrmUserRepository().dropDatabase();

    user = new User(null, "username", "Display Name", "password123");
    OrmUserRepository userRepository = new OrmUserRepository();
    userRepository.save(user);

    post = new Post(user, "UI test post", null);
    postService = new PostService(new OrmPostRepository());
    postService.createPost(post);

    userService = new UserService(new OrmUserRepository());
  }

  @AfterEach
  void cleanupData() {
    new OrmPostRepository().dropDatabase();
    new OrmUserRepository().dropDatabase();
  }

  @Test
  @DisplayName("Like button updates model and toggles color")
  void test_likeButtonClick() {
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
    assertEquals(0, post.getLikes());
    interact(likeBtn::fire);
    Post updatedPost = postService.getPostById(post.getId()).orElseThrow();
    assertEquals(1, updatedPost.getLikes());
    assertTrue(updatedPost.likedByUser(user));
    FxAssert.verifyThat(likeBtn, b -> b.getStyle().contains("blue"));

    // unlike and check
    interact(likeBtn::fire);
    updatedPost = postService.getPostById(post.getId()).orElseThrow();
    assertEquals(0, updatedPost.getLikes());
    assertFalse(updatedPost.likedByUser(user));
    FxAssert.verifyThat(likeBtn, b -> !b.getStyle().contains("blue"));
  }

  // Test written with help from OpenAI's GPT-5
  @Test
  @DisplayName("Second user likes a post already liked by first user")
  void test_secondUserLikesAlreadyLikedPost() {
    postService.likePost(post, user);
    postService.update(post);
    assertEquals(1, post.getLikes(), "Post should already have 1 like from first user");
    User secondUser = new User(null, "seconduser", "Second User", "pass456");
    new OrmUserRepository().save(secondUser);
    typeIntoTextInput("#usernameField", "seconduser");
    typeIntoTextInput("#passwordField", "pass456");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    // like and check
    Button likeBtn = lookup("#likeBtn").nth(0).queryAs(Button.class);
    interact(likeBtn::fire);
    Post updatedPost = postService.getPostById(post.getId()).orElseThrow();
    assertEquals(2, updatedPost.getLikes(), "Post should now have 2 likes");
    assertTrue(updatedPost.likedByUser(secondUser),
        "Second user should be registered as liking the post");
    assertTrue(updatedPost.likedByUser(user), "First user's like should still be counted");
    FxAssert.verifyThat(likeBtn, b -> b.getStyle().contains("blue"));
  }

}
