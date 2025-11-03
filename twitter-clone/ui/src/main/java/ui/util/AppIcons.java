package ui.util;

import java.util.Objects;
import javafx.scene.image.Image;

/**
 * A utility class that loads and stores all commonly used UI icons. These
 * images are loaded once and can be shared throughout the application.
 */
public final class AppIcons {

  private AppIcons() { // prevent instantiation
  }

  private static Image load(String path) {
    return new Image(Objects.requireNonNull(AppIcons.class.getResource(path), path + " not found")
        .toExternalForm());
  }

  public static final Image LIKE_ON = load("/icons/likeOn.png");
  public static final Image LIKE_OFF = load("/icons/likeOff.png");
  public static final Image RETWEET_ON = load("/icons/retweetOn.png");
  public static final Image RETWEET_OFF = load("/icons/retweetOff.png");
}
