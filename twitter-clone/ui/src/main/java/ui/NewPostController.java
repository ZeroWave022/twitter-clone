package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.PostService;
import service.UserService;

@Component
public class NewPostController {
  UserService userService;
  PostService postService;

  @Autowired
  @SuppressFBWarnings(value = { "EI_EXPOSE_REP2",
      "URF_UNREAD_FIELD" }, justification = "We need to inject the UserRepository service. "
          + "PostService will be used in the future.")
  public NewPostController(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @FXML
  private TextArea postText;

  // TODO: Implement method and integrate with tweet code class
  @FXML
  @SuppressWarnings("unused")
  private void publishPost() throws IOException {
    System.out.println("Post wanting to be published");

    // get text from field, fx:id postText

    // Switch back to feed
    // I find it prudent it recalls for auth, however it needs to be implemented
    switchToFeed();
  }

  @FXML
  @SuppressWarnings("unused")
  private void switchToFeed() throws IOException {
    App.setRoot("feed.fxml");
  }

}
