package ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import core.Post;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.PostService;

@Component
@Scope("prototype") // Tells springboot to create individual PostController class for posts
public class PostController {
  private final PostService postService;

  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We need to inject the "
      + "userService and postService.")
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @FXML
  private Label displayNameLabel;
  @FXML
  private Label usernameLabel;
  @FXML
  private Label contentLabel;
  @FXML
  private Button likeBtn;
  @FXML
  private Button retweetBtn;

  private Post currentPost;

  @FXML
  public void setData(Post post) {
    this.currentPost = post;

    displayNameLabel.setText(post.getAuthor().getDisplayName());
    usernameLabel.setText("@" + post.getAuthor().getUsername());
    contentLabel.setText(post.getContent());
    likeBtn.setText(post.getLikes() + " 👍");
    retweetBtn.setText(post.getReTweets() + " 🔄");

    // if (post.likedByUser(post.getAuthor()))
    likeBtn.setStyle("-fx-text-fill: blue;");
    // if (post.reTweetedByUser(userService.getLoggedInUser()))
    // retweetBtn.setStyle("-fx-text-fill: cyan;");
  }

  @FXML
  public void updateLikes() { // do not remove, posts wont show
    if (currentPost != null) {
      postService.likePost(currentPost);
      likeBtn.setText(currentPost.getLikes() + " 👍");
    }

  }

  @FXML
  public void updateRetweets() { // do not remove, posts wont show

  }
}
