package api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core.Post;
import core.payload.response.PostResponse;

@Service
public class PostService {
  @Autowired
  private UserService userService;

  public PostResponse toDTO(Post post) {
    return new PostResponse(
        post.getId(),
        post.getContent(),
        post.getLikes(),
        post.getReTweets(),
        post.getCommentsAmount(),
        userService.toDTO(post.getAuthor()));
  }
}
