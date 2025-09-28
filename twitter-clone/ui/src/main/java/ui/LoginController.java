package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
public class LoginController {
  UserService userService;

  @Autowired
  @SuppressFBWarnings(value = { "EI_EXPOSE_REP2",
      "URF_UNREAD_FIELD" }, justification = "We need to inject the UserRepository service. "
          + "PostService will be used in the future.")
  public LoginController(UserService userService) {
    this.userService = userService;
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
  private void logIn() throws IOException {
    App.setRoot("feed.fxml");
  }
}
