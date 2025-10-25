package ui;

import core.Post;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

public class PostCell extends ListCell<Post> {

  // Regular post view/controller
  private HBox regularNode;
  private PostController regularController;

  // Retweet view/controller
  private HBox retweetNode;
  private PostController retweetController;

  public PostCell() { /* lazy-load in updateItem() to avoid exceptions at startup */ }

  private void ensureRegularLoaded() {
    if (regularNode != null) return;
    try {
      FXMLLoader loader = new FXMLLoader(App.class.getResource("post.fxml"));
      loader.setControllerFactory(App.getSpringContext()::getBean);
      regularNode = loader.load();
      regularController = loader.getController();
    } catch (Exception e) {
      e.printStackTrace();
      new Alert(AlertType.ERROR, "Could not load post.fxml", ButtonType.OK).show();
    }
  }

  private void ensureRetweetLoaded() {
    if (retweetNode != null) return;
    try {
      FXMLLoader loader = new FXMLLoader(App.class.getResource("retweetPost.fxml"));
      loader.setControllerFactory(App.getSpringContext()::getBean);
      retweetNode = loader.load();
      retweetController = loader.getController();
    } catch (Exception e) {
      e.printStackTrace();
      new Alert(AlertType.ERROR, "Could not load retweetPost.fxml", ButtonType.OK).show();
    }
  }

  @Override
  protected void updateItem(Post post, boolean empty) {
    super.updateItem(post, empty);

    if (empty || post == null) {
      setText(null);
      setGraphic(null);
      setStyle("-fx-background-color: transparent;");
      return;
    }

    // Decide which template to use
    boolean isRetweet = post.getOriginalPost() != null
                        || post.getType() == Post.Type.RETWEET;

    if (isRetweet) {
      ensureRetweetLoaded();
      if (retweetController != null) {
        retweetController.setData(post);   // same controller class handles retweet layout
      }
      setGraphic(retweetNode);
    } else {
      ensureRegularLoaded();
      if (regularController != null) {
        regularController.setData(post);
      }
      setGraphic(regularNode);
    }

    // Remove default alternating color
    setStyle("-fx-background-color: transparent;");
  }
}
