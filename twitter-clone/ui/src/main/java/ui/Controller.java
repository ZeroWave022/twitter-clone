package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import core.Post;
import core.User;
import service.PostService;
import service.UserService;

@Component
public class Controller {
  UserService userService;
  PostService postService;

  @Autowired
  @SuppressFBWarnings(value = { "EI_EXPOSE_REP2",
      "URF_UNREAD_FIELD" }, justification = "We need to inject the UserRepository service. "
          + "PostService will be used in the future.")
  public Controller(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @FXML
  private TextField usernameField;

  @FXML
  private TextField passwordField;

  @FXML
  @SuppressWarnings("unused")
  private Button logInBtn;

  @FXML
  private Label errorLabel;

  @FXML
  private ListView<Post> feedList;

  // @FXML
  // @SuppressWarnings("unused")
  // private void logIn() throws IOException {
  // // clear any errors
  // errorLabel.setText("");

  // // Save input to variables
  // String username = usernameField.getText();
  // String password = passwordField.getText();

  // if (userService.logIn(username, password)) {
  // App.setRoot("feed.fxml");
  // } else {
  // errorLabel.setText("Incorrect password");
  // }
  // }

  @FXML
  @SuppressWarnings("unused")
  private void switchToFeed() throws IOException {
    App.setRoot("feed.fxml");
  }

  @FXML
  @SuppressWarnings("unused")
  private void switchToMakeNewPost() throws IOException {
    App.setRoot("makeNewPost.fxml");
  }

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
  private void logIn() throws IOException {
    errorLabel.setText("Incorrect password");
    App.setRoot("feed.fxml");
  }

}
