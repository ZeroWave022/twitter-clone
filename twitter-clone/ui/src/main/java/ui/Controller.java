package ui;

import core.Post;
import core.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.PostService;
import service.UserService;

@Component
public class Controller {
  UserService userService;
  PostService postService;

  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We need to inject "
      + "the UserRepository service.")
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
  private TextArea postText;

  @FXML
  @SuppressWarnings("unused")
  private void logIn() throws IOException {
    // clear any errors
    errorLabel.setText("");

    // Save input to variables
    String username = usernameField.getText();
    String password = passwordField.getText();

    if (userService.logIn(username, password)) {
      App.setRoot("feed.fxml");
    } else {
      errorLabel.setText("Incorrect password");
    }
  }

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

  @FXML
  @SuppressWarnings("unused")
  private void publishPost() throws IOException {
    User currentUser = userService.getLoggedInUser();

    if (currentUser == null) {
      System.out.println("User not logged in. Cannot create post.");
      return;
    }

    Post post = postService.createPost(new Post(currentUser, postText.getText(), null));
    System.out.println("New post created with ID: " + post.getId());
    System.out.println(post.getAuthor().getUsername() + " wrote: \n" + post.getContent());

    // Switch back to feed
    // I find it prudent it recalls for auth, however it needs to be implemented
    switchToFeed();
  }
}
