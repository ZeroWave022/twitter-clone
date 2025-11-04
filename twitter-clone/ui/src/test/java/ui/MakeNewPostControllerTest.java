package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Post;
import core.payload.response.PostResponse;
import core.payload.response.UserResponse;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testfx.util.WaitForAsyncUtils;
import persistence.OrmPostRepository;
import service.PostService;
import service.UserService;

@SpringBootTest(classes = ui.config.AppConfig.class)
class MakeNewPostControllerTest extends UiTestBase {

  @Autowired
  private PostService postService;
  @Autowired
  private UserService userService;

  private UserResponse user;
  private PostResponse seedPost;

  @BeforeEach
  void setupData() {
    new OrmPostRepository().dropDatabase();

    // Seed backend state
    userService.logIn("username", "password123");
    user = userService.getLoggedInUser();
    seedPost = postService.createPost("Seed content", null);

    // Now log in through the UI so the app navigates to the feed correctly
    typeIntoTextInput("#usernameField", "username");
    typeIntoTextInput("#passwordField", "password123");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    // Sanity: feed is visible
    assertNotNull(lookup("#feedList").queryAs(ListView.class),
        "Feed should be visible after login");
  }

  @AfterEach
  void cleanup() {
    new OrmPostRepository().dropDatabase();
  }

  // Helpers for compose view nodes
  private TextArea postText() {
    return lookup("#postText").queryAs(TextArea.class);
  }

  private Button publishBtn() {
    return lookup("#publishBtn").queryAs(Button.class);
  }

  private Label charCount() {
    return lookup("#charCount").queryAs(Label.class);
  }

  /** Open the compose view directly (keeps controller code unchanged). */
  private void openComposeView() {
    interact(() -> {
      try {
        App.setRoot("makeNewPost.fxml");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  @DisplayName("Initial state in compose: publish disabled and counter 0/MAX")
  void initial_state() {
    openComposeView();

    TextArea area = postText();
    Button publish = publishBtn();
    Label counter = charCount();

    assertNotNull(area);
    assertNotNull(publish);
    assertNotNull(counter);

    WaitForAsyncUtils.waitForFxEvents();
    assertTrue(publish.isDisabled(), "Publish should be disabled initially");
    assertEquals("0/" + Post.MAX_CONTENT_LENGTH, counter.getText());
  }

  @Test
  @DisplayName("Publishing a valid post creates it and returns to feed")
  void publish_valid_post_creates_and_returns_to_feed() {
    ListView<?> feedBefore = lookup("#feedList").queryAs(ListView.class);
    int beforeCount = feedBefore.getItems().size();

    openComposeView();

    String content = "Hello from MakeNewPostController test!";
    clickOn(postText());
    write(content);
    WaitForAsyncUtils.waitForFxEvents();

    assertFalse(publishBtn().isDisabled(), "Publish should be enabled for valid content");
    clickOn(publishBtn());
    WaitForAsyncUtils.waitForFxEvents();

    ListView<?> feedAfter = lookup("#feedList").queryAs(ListView.class);
    assertNotNull(feedAfter, "Should return to feed after publishing");
    int afterCount = feedAfter.getItems().size();
    assertEquals(beforeCount + 1, afterCount, "Feed should contain one more post");

    var contentLabel = lookup("#contentLabel").match(n -> n instanceof Label
        && ((Label) n).getText() != null && ((Label) n).getText().contains(content)).tryQuery();
    assertTrue(contentLabel.isPresent(), "Newly created content should be visible in the feed");
  }

  @Test
  @DisplayName("Over-limit content keeps publish disabled and styles error")
  void over_limit_disables_publish_and_styles_counter() {
    openComposeView();

    TextArea area = postText();

    clickOn(area);
    interact(area::clear);
    WaitForAsyncUtils.waitForFxEvents();

    Button publish = publishBtn();
    String tooLong = "x".repeat(Post.MAX_CONTENT_LENGTH + 1);
    interact(() -> area.setText(tooLong));
    WaitForAsyncUtils.waitForFxEvents();

    Label counter = charCount();
    assertTrue(publish.isDisabled(), "Publish must be disabled when content exceeds limit");
    assertTrue(counter.getStyle().contains("#d32f2f"),
        "Counter text should be red when over the limit");
    assertTrue(area.getStyle().contains("-fx-border-color"),
        "TextArea should show an error border when over the limit");

    interact(publish::fire);
    WaitForAsyncUtils.waitForFxEvents();

    assertNotNull(lookup("#postText").queryAs(TextArea.class),
        "Still on compose view since publish is disabled");
  }

  @Test
  @DisplayName("Valid → blank re-disables publish")
  void blank_content_disables_publish() {
    openComposeView();

    TextArea area = postText();
    Button publish = publishBtn();

    interact(() -> area.setText("valid"));
    WaitForAsyncUtils.waitForFxEvents();
    assertFalse(publish.isDisabled(), "Publish should be enabled for valid content");

    interact(area::clear);
    WaitForAsyncUtils.waitForFxEvents();
    assertTrue(publish.isDisabled(), "Publish should be disabled when content is blank");
  }
}
