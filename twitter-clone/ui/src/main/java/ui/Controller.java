package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
  private Stage stage;
  private Scene scene;
  private Parent parent;

  // TODO: Make auth function, possibly move to core
  public boolean auth() { //(String username, String password)
    return true;
  }

  // Meant to be called from log in, auth(String username, String password) needs to be implemented
  public void switchToFeed(ActionEvent e) throws IOException {
    if (!auth()) {
      System.out.println("Credentials not approved");
      return;
    }

    Parent root = FXMLLoader.load(getClass().getResource("feed.fxml"));
    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }


  public void switchToMakeNewPost(ActionEvent e) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("makeNewPost.fxml"));
    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }


  // TODO: Implement method and integrate with tweet code class
  public void publishPost(ActionEvent e) throws IOException {
    System.out.println("Post wanting to be published");
    
    // get text from field, fx:id postText

    // Switch back to feed
    // I find it prudent it recalls for auth, however it needs to be implemented
    switchToFeed(e);
  }
}
