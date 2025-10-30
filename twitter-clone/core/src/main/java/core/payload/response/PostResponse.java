package core.payload.response;

import java.util.Set;

/**
 * DTO for a post returned from the API.
 *
 * @param id             database row id
 * @param content        content
 * @param likes          amount of likes
 * @param reTweets       amount of retweets
 * @param commentsAmount amount of comments
 * @param author         author as a {@link UserResponse} DTO
 * @param likedByUsers   set of user ids that have liked the post
 */
public record PostResponse(Long id, String content, int likes, int reTweets, int commentsAmount,
    UserResponse author, Set<Long> likedByUsers, Long originalPostId) {
  /**
   * Makes sure all fields are truly read only.
   */
  public PostResponse {
    likedByUsers = likedByUsers == null ? Set.of() : Set.copyOf(likedByUsers);
  }

  /**
   * Checks if a user with a given id has liked the post.
   *
   * @param userId the users id
   * @return whether the user has liked the post
   */
  public boolean likedByUser(Long userId) {
    return this.likedByUsers.contains(userId);
  }
}
