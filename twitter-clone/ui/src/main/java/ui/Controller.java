package ui;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import core.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.PostService;
import service.UserService;

@Component
public class Controller {
  UserService userService;
  PostService postService;

  @Autowired
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
  @SuppressWarnings("unused")
  private void logIn() throws IOException {
    try {

      // clear any errors
      errorLabel.setText("");

      // Save input to variables
      String username = usernameField.getText();
      String password = passwordField.getText();

      Optional<User> maybeUser = userService.getUserByUsername(username);

      // User exists, confirm password
      if (maybeUser.isPresent()) {
        User user = maybeUser.get();
        if (user.getPassword().equals(password)) {
          App.setRoot("feed.fxml");
        } else {
          errorLabel.setText("Incorrect Password");
        }
      }
      // New user gets to log in
      else {
        User newUser = new User(null, username, username, password); // make new user, repo handles ID
        userService.createUser(newUser); // add user to db
        App.setRoot("feed.fxml"); // switch to feed
      }
    } catch (IOException e) {
      errorLabel.setText("Something went wrong");
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
}
