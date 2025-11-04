package core.payload.request;

/**
 * DTO for post creation.
 *
 * @param content the text in the post
 */
public record CreatePostRequest(String content, Long originalPost) {
}
