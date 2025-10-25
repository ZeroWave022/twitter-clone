package core.payload.response;

import java.util.Set;

public record PostResponse(Long id, String content, int likes, int reTweets, int commentsAmount, UserResponse author,
    Set<Long> likedByUsers) {
  public PostResponse {
    likedByUsers = likedByUsers == null ? Set.of() : Set.copyOf(likedByUsers);
  }

  public boolean likedByUser(Long userId) {
    return this.likedByUsers.contains(userId);
  }
}
