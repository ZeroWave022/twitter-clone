package ui;

import core.payload.response.PostResponse;
import core.util.NumberFormatter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.PostService;
import service.UserService;

/**
 * Controller for the profile page. Displays ones own posts and counters. It
 * also handles navigation.
 */
@Component
public class ProfileController {
  @Autowired
  PostService postService;
  @Autowired
  UserService userService;

  @FXML
  Label displayNameLabel;
  @FXML
  Label usernameLabel;
  @FXML
  Label likeCountLabel;
  @FXML
  Label postCountLabel;
  @FXML
  @SuppressWarnings("unused")
  Button logOutBtn;
  @FXML
  @SuppressWarnings("unused")
  Button backToFeedBtn;
  @FXML
  ListView<PostResponse> feedList;

  private boolean updatingCounters = false;
  List<PostResponse> postsByUser;

  /** Initializes the feed list view and populates posts. */
  @FXML
  public void initialize() {
    // Disable the higlight on selectio of posts
    feedList.setSelectionModel(null);
    feedList.setFocusTraversable(false);

    // Use custom cell factory to display each Post using the post.fxml layout and
    // PostCell class
    feedList.setCellFactory(listView -> new PostCell());

    postsByUser = postService.postsByUser();
    feedList.getItems().setAll(postsByUser);

    displayNameLabel.setText(userService.getLoggedInUser().displayName());
    usernameLabel.setText("@" + userService.getLoggedInUser().username());

    updateProfileCounters(); // initial counters

    // Listen for post updates
    postService.setPostUpdateListener(this::updateProfileCounters);
  }

  /**
   * Updates the profile counters displayed in the UI, including:
   * <ul>
   * <li>Total number of posts for the current user, including retweets</li>
   * <li>Total number of likes across all posts</li>
   * </ul>
   * Debounces rapid calls to prevent duplicate updates.This method fetches the
   * latest posts from the {@link PostService} and recalculates the counters. It
   * should be called whenever posts are added, liked, or removed to keep the UI
   * in sync with the backend.
   */
  public void updateProfileCounters() {
    if (updatingCounters) {
      return;
    }
    updatingCounters = true;

    Platform.runLater(() -> {
      postsByUser = postService.postsByUser();
      postCountLabel.setText(NumberFormatter.formatCount(postsByUser.size()));
      likeCountLabel.setText(NumberFormatter.formatCount(likesCount()));
      updatingCounters = false;
    });
  }

  /**
   * Calculates the total number of likes across all posts of the current user.
   *
   * @return the sum of likes for all posts in {@link #postsByUser}.
   */
  public int likesCount() {
    int totalLikes = 0;
    // fetch latest
    // postsByUser = postService.postsByUser();
    for (PostResponse post : postsByUser) {
      totalLikes += post.likedByUsers().size();
    }
    return totalLikes;
  }

  /**
   * Navigates back to the main feed view.
   */
  @FXML
  @SuppressWarnings("unused")
  public void backToFeed() throws IOException {
    App.setRoot("feed.fxml");
  }

  /**
   * Logs out the current user and navigates to the login view.
   */
  @FXML
  @SuppressWarnings("unused")
  public void logOut() throws IOException {
    userService.logOut();
    App.setRoot("login.fxml");
  }

}
