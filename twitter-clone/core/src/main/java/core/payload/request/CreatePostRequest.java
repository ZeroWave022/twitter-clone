package core.payload.request;

/**
 * A DTO for creating posts.
 */
public class CreatePostRequest {
  private String text;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
