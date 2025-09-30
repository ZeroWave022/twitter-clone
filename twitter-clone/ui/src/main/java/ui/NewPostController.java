package ui;

import core.Post;
import core.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.PostService;
import service.UserService;

@Component
public class NewPostController {
  UserService userService;
  PostService postService;

  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We need to inject "
      + "the UserRepository services. ")
  public NewPostController(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @FXML
  private TextArea postText;

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

    switchToFeed();
  }

  @FXML
  @SuppressWarnings("unused")
  private void switchToFeed() throws IOException {
    App.setRoot("feed.fxml");
  }

}
