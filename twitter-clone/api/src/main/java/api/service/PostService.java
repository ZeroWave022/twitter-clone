package api.service;

import core.Post;
import core.User;
import core.payload.response.PostResponse;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Utility service for converting {@link Post} objects to DTOs.
 */
@Service
public class PostService {
  @Autowired
  private UserService userService;

  /**
   * Converts a {@link Post} to a DTO.
   *
   * @param post the post
   * @return the post as a DTO
   */
  public PostResponse toDto(Post post) {
    return new PostResponse(post.getId(), post.getContent(), post.getLikes(), post.getReTweets(),
        post.getCommentsAmount(), userService.toDto(post.getAuthor()),
        post.getLikedByUsers().stream().map(User::getId).collect(Collectors.toSet()));
  }
}
