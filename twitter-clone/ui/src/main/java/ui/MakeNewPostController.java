package ui;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import service.PostService;

@Component
public class MakeNewPostController {
  private final PostService postService;

  @Autowired
  public MakeNewPostController(PostService postService) {
    this.postService = postService;
  }

  @FXML
  private TextArea postText; 

  @FXML
  private Button publishBtn;

  @FXML
  private Button backBtn;

  @FXML
  @SuppressWarnings("unused")
  private void publishPost() throws IOException {
    String content = postText.getText();
    // TODO: validate length against Post.MAX_CONTENT_LENGTH
    // TODO: get logged-in user and send post to PostService
    System.out.println("Publishing post: " + content);

    switchToFeed();
  }

  @FXML
  @SuppressWarnings("unused")
  private void switchToFeed() throws IOException {
    App.setRoot("feed.fxml");
  }
}
