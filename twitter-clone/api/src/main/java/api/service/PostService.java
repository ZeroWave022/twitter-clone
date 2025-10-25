package api.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core.Post;
import core.User;
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
        userService.toDTO(post.getAuthor()),
        post.getLikedByUsers().stream().map(User::getId).collect(Collectors.toSet()));
  }
}
