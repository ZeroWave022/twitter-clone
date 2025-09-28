package ui;

import core.Post;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

// This class is based on the example from this StackOverflow post:
// https://stackoverflow.com/a/77508842
public class PostCell extends ListCell<Post> {

  @Override
  protected void updateItem(Post post, boolean empty) {
    super.updateItem(post, empty);
    if (empty || post == null) {
      setGraphic(null);
    } else {
      try {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("post.fxml"));
        loader.setControllerFactory(App.getSpringContext()::getBean);
        HBox postItem = loader.load();
        PostController postController = loader.getController();

        postController.setData(post);
        setGraphic(postItem);

      } catch (Exception e) {
        setGraphic(null);
      }
    }
    // Removes the default alternating background color for filled cells
    setStyle("-fx-background-color: transparent;");
  }
}
