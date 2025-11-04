package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.payload.response.PostResponse;
import core.payload.response.UserResponse;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.util.WaitForAsyncUtils;
import persistence.OrmPostRepository;
import service.PostService;
import service.UserService;

@SpringBootTest(classes = ui.config.AppConfig.class)
class ProfileControllerTest extends UiTestBase {

  @Autowired
  private PostService postService;

  @Autowired
  private UserService userService;

  private UserResponse user;
  private PostResponse post1;
  private PostResponse post2;

  @BeforeEach
  void setup() throws Exception {
    super.setup();
    OrmPostRepository repo = new OrmPostRepository();
    repo.dropDatabase();

    userService.logIn("username", "password123");
    user = userService.getLoggedInUser();
    post1 = postService.createPost("Test post 1");
    post2 = postService.createPost("Test post 2");

    // Setup the stage properly so lookups can work
    FxToolkit.setupStage(stage -> {
      try {
        new App().start(stage); // or App.launch()
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    // Wait for the scene graph to render
    WaitForAsyncUtils.waitForFxEvents();

    // Now safe to lookup nodes
    typeIntoTextInput("#usernameField", "username");
    typeIntoTextInput("#passwordField", "password123");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();

    Button profileBtn = lookup("#profileBtn").queryAs(Button.class);
    interact(profileBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();
  }

  @AfterEach
  void cleanup() {
    new OrmPostRepository().dropDatabase();
  }

  @Test
  @DisplayName("Profile page displays correct username, display name, post count, and likes count")
  void testProfileCounters() {
    Label displayNameLabel = lookup("#displayNameLabel").queryAs(Label.class);
    Label usernameLabel = lookup("#usernameLabel").queryAs(Label.class);
    Label postCountLabel = lookup("#postCountLabel").queryAs(Label.class);
    Label likeCountLabel = lookup("#likeCountLabel").queryAs(Label.class);
    assertNotNull(displayNameLabel);
    assertNotNull(usernameLabel);
    assertNotNull(postCountLabel);
    assertNotNull(likeCountLabel);

    assertEquals(user.displayName(), displayNameLabel.getText());
    assertEquals("@" + user.username(), usernameLabel.getText());
    assertEquals("2", postCountLabel.getText());
    assertEquals("0", likeCountLabel.getText());
  }

  @Test
  @DisplayName("Liking posts updates total likes on profile page correctly")
  void testLikeUpdatesProfile() {
    Button likeBtn1 = lookup("#likeBtn").nth(0).queryAs(Button.class);
    Button likeBtn2 = lookup("#likeBtn").nth(1).queryAs(Button.class);

    interact(likeBtn1::fire);
    interact(likeBtn2::fire);
    WaitForAsyncUtils.waitForFxEvents();

    Label likeCountLabel = lookup("#likeCountLabel").queryAs(Label.class);
    assertEquals("2", likeCountLabel.getText());

    interact(likeBtn1::fire);
    WaitForAsyncUtils.waitForFxEvents();
    assertEquals("1", likeCountLabel.getText());
  }

  @Test
  @DisplayName("Profile feed list displays all user's posts in correct order")
  void testProfileFeedList() {
    ListView<PostResponse> feedList = lookup("#feedList").queryAs(ListView.class);
    assertNotNull(feedList);
    assertEquals(2, feedList.getItems().size());
    assertEquals(post1.id(), feedList.getItems().get(0).id());
    assertEquals(post2.id(), feedList.getItems().get(1).id());
  }

  @Test
  @DisplayName("Back and Log Out buttons navigate correctly")
  void testNavigationButtons() throws Exception {
    Button backBtn = lookup("#backToFeedBtn").queryAs(Button.class);
    Button logOutBtn = lookup("#logOutBtn").queryAs(Button.class);

    assertNotNull(backBtn);
    assertNotNull(logOutBtn);

    interact(backBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();
    FxAssert.verifyThat(".label", node -> ((Label) node).getText().contains("MY TWITTER FEED"));

    interact(logOutBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    assertNotNull(logInBtn);
  }
}
