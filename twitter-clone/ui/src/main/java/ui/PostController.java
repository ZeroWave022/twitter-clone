package ui;

import core.Post;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

@Component
public class PostController {

  @FXML
  private Label displayNameLabel;
  @FXML
  private Label usernameLabel;
  @FXML
  private Label contentLabel;

  public void setData(Post post) {
    displayNameLabel.setText(post.getAuthor().getDisplayName());
    usernameLabel.setText("@" + post.getAuthor().getUsername());
    contentLabel.setText(post.getContent());
  }
}
