package ui;

import core.Post;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.PostService;
import service.UserService;

@Component
public class FeedController {
  UserService userService;
  PostService postService;

  @Autowired
  @SuppressFBWarnings(value = { "EI_EXPOSE_REP2",
      "URF_UNREAD_FIELD" }, justification = "We need to inject the UserRepository service. "
          + "PostService will be used in the future.")
  public FeedController(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @FXML
  private ListView<Post> feedList;

  @FXML
  public void initialize() {
    // Disable the higlight on selectio of posts
    feedList.setSelectionModel(null);
    feedList.setFocusTraversable(false);

    // Use custom cell factory to display each Post using the post.fxml layout and
    // PostCell class
    feedList.setCellFactory(listView -> new PostCell());

    feedList.getItems().setAll(postService.getAllPosts());
  }

  @FXML
  @SuppressWarnings("unused")
  private void switchToMakeNewPost() throws IOException {
    App.setRoot("makeNewPost.fxml");
  }

  @FXML
  @SuppressWarnings("unused")
  private void switchToLogin() throws IOException {
    App.setRoot("login.fxml");
  }

}
