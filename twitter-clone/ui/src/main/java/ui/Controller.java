package ui;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import persistence.UserRepository;

@Component
public class Controller {
  UserRepository userRepository;

  @Autowired
  public Controller(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @FXML
  private TextField username;

  @FXML
  private TextField password;

  @FXML
  private Button logInBtn;

  // TODO: Make auth function, possibly move to core
  public boolean auth() { // (String username, String password)
    return true;
  }

  // Meant to be called from log in, auth(String username, String password) needs
  // to be implemented
  @FXML
  private void switchToFeed() throws IOException {
    if (!auth()) {
      System.out.println("Credentials not approved");
      return;
    }

    App.setRoot("feed.fxml");
  }

  @FXML
  private void switchToMakeNewPost() throws IOException {
    App.setRoot("makeNewPost.fxml");
  }

  // TODO: Implement method and integrate with tweet code class
  @FXML
  private void publishPost() throws IOException {
    System.out.println("Post wanting to be published");

    // get text from field, fx:id postText

    // Switch back to feed
    // I find it prudent it recalls for auth, however it needs to be implemented
    switchToFeed();
  }
}
