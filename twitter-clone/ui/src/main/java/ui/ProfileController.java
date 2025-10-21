package ui;

import core.Post;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.PostService;
import service.UserService;

@Component
public class ProfileController {
  private final PostService postService;
  private final UserService userService;

  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We need to inject the "
      + "userService and postService.")
  public ProfileController(PostService postService, UserService userService) {
    this.postService = postService;
    this.userService = userService;
  }

  @FXML
  Label displayNameLabel;
  @FXML
  Label usernameLabel;
  @FXML
  Button logOutBtn;
  @FXML
  Button backToFeedBtn;
  @FXML
  ListView<Post> feedList;

  @FXML
  public void initialize() {
    // Disable the higlight on selectio of posts
    feedList.setSelectionModel(null);
    feedList.setFocusTraversable(false);

    // Use custom cell factory to display each Post using the post.fxml layout and
    // PostCell class
    feedList.setCellFactory(listView -> new PostCell());

    feedList.getItems().setAll(postService.postsByUser(userService.getLoggedInUser()));
  }

  @FXML
  @SuppressWarnings("unused")
  public void backToFeed() throws IOException {
    App.setRoot("feed.fxml");
  }

  @FXML
  @SuppressWarnings("unused")
  public void logOut() throws IOException {
    userService.logOut();
    App.setRoot("login.fxml");
  }

}
