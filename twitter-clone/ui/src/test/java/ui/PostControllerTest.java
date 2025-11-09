package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.payload.response.PostResponse;
import core.payload.response.UserResponse;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testfx.api.FxAssert;
import org.testfx.util.WaitForAsyncUtils;
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
  @Override
  void setup() throws Exception {
    super.setup();

    userService.logIn("username", "password123");
    user = userService.getLoggedInUser();
    post = postService.createPost("UI Test content", null);
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
  @DisplayName("Retweet button is visible for non-authors")
  void test_retweetButtonVisibleForOtherUser() {

    typeIntoTextInput("#usernameField", "seconduser");
    typeIntoTextInput("#passwordField", "pass456");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    Button retweetBtn = lookup("#retweetBtn").nth(0).queryAs(Button.class);
    assertNotNull(retweetBtn, "Retweet button should be present for other users");
    assertTrue(retweetBtn.isVisible(), "Retweet button should be visible for non-authors");
    assertTrue(retweetBtn.isManaged(),
        "Retweet button should participate in layout for non-authors");

    PostResponse fresh = postService.getPostById(post.id()).orElseThrow();

    Text retweetBtnText = lookup("#retweetBtnText").queryAs(Text.class);
    assertEquals(fresh.reTweets(), Integer.valueOf(retweetBtnText.getText()),
        "Retweet caption should show current count");
  }

  // This test was created by openAI chatGPT-5
  @Test
  @DisplayName("Retweet via UI: second user retweets and original count increments")
  void test_clickRetweetButtonAndConfirmDialog_scopedLookups() {
    typeIntoTextInput("#usernameField", "seconduser");
    typeIntoTextInput("#passwordField", "pass456");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    Button rtBtn = lookup("#retweetBtn").nth(0).queryAs(Button.class);
    assertNotNull(rtBtn, "Retweet button should be present");
    Platform.runLater(rtBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    DialogPane pane = lookup(".dialog-pane").queryAs(DialogPane.class);
    assertNotNull(pane, "DialogPane should be present");

    TextArea ta = from(pane).lookup(".text-area").queryAs(TextArea.class);
    assertNotNull(ta, "TextArea should exist in the dialog");
    typeIntoTextInput(".text-area", "Nice post!");
    WaitForAsyncUtils.waitForFxEvents();

    Button ok = from(pane)
        .lookup((Node n) -> n instanceof Button && "Retweet".equals(((Button) n).getText()))
        .queryAs(Button.class);
    assertNotNull(ok, "OK button labeled 'Retweet' should be present");
    interact(ok::fire);
    WaitForAsyncUtils.waitForFxEvents();

    PostResponse updatedOriginal = postService.getPostById(post.id()).orElseThrow();
    assertEquals(1, updatedOriginal.reTweets(), "Original post should have one retweet");
  }

  // This test was created by openAI chatGPT-5
  @Test
  @DisplayName("Retweet dialog prevents messages longer than 280 characters")
  void test_retweetDialogDisablesOkWhenOverLimit() {
    typeIntoTextInput("#usernameField", "seconduser");
    typeIntoTextInput("#passwordField", "pass456");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    PostResponse original = postService.getPostById(post.id()).orElseThrow();
    assertEquals(0, original.reTweets(), "Original post should start with 0 retweets");

    Button rtBtn = lookup("#retweetBtn").nth(0).queryAs(Button.class);
    assertNotNull(rtBtn, "Retweet button should be present");
    Platform.runLater(rtBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    DialogPane pane = lookup(".dialog-pane").queryAs(DialogPane.class);
    assertNotNull(pane, "DialogPane should be present");

    javafx.scene.control.TextArea ta = from(pane).lookup(".text-area")
        .queryAs(javafx.scene.control.TextArea.class);
    assertNotNull(ta, "TextArea should exist in the dialog");

    String overLimitMsg = "x".repeat(281);
    typeIntoTextInput(".text-area", overLimitMsg);
    WaitForAsyncUtils.waitForFxEvents();

    Button ok = from(pane)
        .lookup((Node n) -> n instanceof Button && "Retweet".equals(((Button) n).getText()))
        .queryAs(Button.class);
    assertNotNull(ok, "OK button labeled 'Retweet' should be present");
    assertTrue(ok.isDisabled(), "OK should be disabled when message exceeds 280 characters");

    Button cancel = from(pane)
        .lookup((Node n) -> n instanceof Button && "Cancel".equals(((Button) n).getText()))
        .queryAs(Button.class);
    assertNotNull(cancel, "Cancel button should be present");
    interact(cancel::fire);
    WaitForAsyncUtils.waitForFxEvents();

    PostResponse updatedOriginal = postService.getPostById(post.id()).orElseThrow();
    assertEquals(0, updatedOriginal.reTweets(),
        "No retweet should be created when message exceeds 280 characters");
  }

  @Test
  @DisplayName("Deletion of inner post in retweet")
  void testDeletionOfInnerPostInRetweet() {
    userService.logIn("username2", "password123");
    postService.createPost("retweet", post.id());
    userService.logIn("username", "password123");

    WaitForAsyncUtils.waitForFxEvents();
    typeIntoTextInput("#usernameField", "username");
    typeIntoTextInput("#passwordField", "password123");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    Button deleteBtn = lookup("#deleteOriginalBtn").nth(0).queryAs(Button.class);
    Platform.runLater(() -> interact(deleteBtn::fire));
    WaitForAsyncUtils.waitForFxEvents();

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
