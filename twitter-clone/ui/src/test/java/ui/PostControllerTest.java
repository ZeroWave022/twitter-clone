package ui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

import core.Post;
import core.User;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.util.WaitForAsyncUtils;
import persistence.OrmPostRepository;
import persistence.OrmUserRepository;
import service.PostService;
import service.UserService;

class PostControllerTest extends UiTestBase {

  private PostService postService;
  private UserService userService;
  private User user;
  private Post post;

  @BeforeEach
  void setupData() {
    // At this point, UiTestBase has already set
    // System.setProperty("app.data.directory")
    // So any new repository will point to the temp DB

    // Clean temp DB
    new OrmPostRepository().dropDatabase();
    new OrmUserRepository().dropDatabase();

    // Persist test user into temp DB
    user = new User(null, "username", "Display Name", "password123");
    OrmUserRepository userRepository = new OrmUserRepository();
    userRepository.save(user);

    // Persist a post authored by this user
    post = new Post(user, "UI test post", null);
    postService = new PostService(new OrmPostRepository());
    postService.createPost(post);

    // UserService also points to temp DB
    userService = new UserService(new OrmUserRepository());
  }

  @AfterEach
  void cleanupData() {
    // Ensure temp DB is cleared after each test
    new OrmPostRepository().dropDatabase();
    new OrmUserRepository().dropDatabase();
  }

  @Test
  @DisplayName("Like button updates model and toggles color")
  void test_likeButtonClick() throws Exception {
    // Step 1: Log in through the login screen
    typeIntoTextInput("#usernameField", "username");
    typeIntoTextInput("#passwordField", "password123");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);

    // Step 2: Wait for feed.fxml to load
    WaitForAsyncUtils.waitForFxEvents();

    // Step 3: Find the ListView
    ListView<?> feedList = lookup("#feedList").queryAs(ListView.class);
    assertNotNull(feedList, "ListView should be loaded");

    // Step 4: Wait for the first post cell to render
    WaitForAsyncUtils.waitForFxEvents();
    Object firstCell = feedList.getItems().get(0);
    assertNotNull(firstCell, "First post should exist");

    // Step 5: Find the like button inside the first cell
    Button likeBtn = lookup("#likeBtn").nth(0).queryAs(Button.class);
    assertNotNull(likeBtn, "Like button should be loaded");

    // Step 6: Verify initial likes
    assertEquals(0, post.getLikes());

    interact(likeBtn::fire);
    Post updatedPost = postService.getPostById(post.getId()).orElseThrow();
    assertEquals(1, updatedPost.getLikes());
    assertTrue(updatedPost.likedByUser(user));
    FxAssert.verifyThat(likeBtn, b -> b.getStyle().contains("blue"));

    interact(likeBtn::fire);
    updatedPost = postService.getPostById(post.getId()).orElseThrow();
    assertEquals(0, updatedPost.getLikes());
    assertFalse(updatedPost.likedByUser(user));
    Thread.sleep(1000);
    FxAssert.verifyThat(likeBtn, b -> !b.getStyle().contains("blue"));
  }

  @Test
  @DisplayName("Second user likes a post already liked by first user")
  void test_secondUserLikesAlreadyLikedPost() throws Exception {
    // --- Simulate first user liking the post ---
    postService.likePost(post, user); // first user likes the post
    postService.update(post); // persist the
    Thread.sleep(1000);
    assertEquals(1, post.getLikes(), "Post should already have 1 like from first user");
    Thread.sleep(1000);
    // --- Second user setup ---
    User secondUser = new User(null, "seconduser", "Second User", "pass456");
    new OrmUserRepository().save(secondUser);

    // --- Log in as second user ---
    typeIntoTextInput("#usernameField", "seconduser");
    typeIntoTextInput("#passwordField", "pass456");
    Button logInBtn = lookup("#logInBtn").queryAs(Button.class);
    interact(logInBtn::fire);
    WaitForAsyncUtils.waitForFxEvents();
    Thread.sleep(1000);
    // --- Find the ListView and first post's like button ---
    ListView<?> feedList = lookup("#feedList").queryAs(ListView.class);
    Button likeBtn = lookup("#likeBtn").nth(0).queryAs(Button.class);
    Thread.sleep(1000);
    // --- Second user clicks like ---
    interact(likeBtn::fire);
    Thread.sleep(1000);
    Post updatedPost = postService.getPostById(post.getId()).orElseThrow();
    assertEquals(2, updatedPost.getLikes(), "Post should now have 2 likes");
    assertTrue(updatedPost.likedByUser(secondUser),
        "Second user should be registered as liking the post");
    assertTrue(updatedPost.likedByUser(user), "First user's like should still be counted");

    Thread.sleep(1000);
    FxAssert.verifyThat(likeBtn, b -> b.getStyle().contains("blue"));
  }

}
