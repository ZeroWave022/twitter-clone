package api.controller;

import api.service.PostService;
import core.Post;
import core.User;
import core.payload.request.CreatePostRequest;
import core.payload.response.PostResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persistence.PostRepository;
import persistence.UserRepository;

/**
 * REST controller for managing Post entities. Provides endpoints for CRUD
 * operations on posts.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  @Autowired
  private PostService postService;

  /**
   * Constructs a new PostController.
   *
   * @param postRepository the repository to use for post operations
   */
  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Spring Repository is thread-safe")
  public PostController(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  /**
   * Retrieves a post by its ID.
   *
   * @param id the ID of the post to retrieve
   * @return the post if found, or 404 if not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long id) {
    return postRepository.findById(id, true)
        .map(postService::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Retrieves all posts.
   *
   * @return list of all posts
   */
  @GetMapping
  public ResponseEntity<List<PostResponse>> getAllPosts() {
    return ResponseEntity.ok(
        postRepository.findAll(true)
            .stream()
            .map(postService::toDto)
            .toList());
  }

  /**
   * Creates a new post.
   *
   * @param createPostRequest the post to create
   * @return the created post
   */
  @PostMapping
  public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest createPostRequest) {
    try {
      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
          .getPrincipal();

      User user = userRepository.findByUsername(userDetails.getUsername()).get();

      Post post = new Post();
      post.setAuthor(user);
      post.setContent(createPostRequest.content());
      return ResponseEntity.ok(postService.toDto(postRepository.save(post)));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Toggles whether the authenticated user has liked a post.
   *
   * @param id the post's id
   * @return the updated post DTO
   */
  @PostMapping("/{id}/likes")
  public ResponseEntity<PostResponse> togglePostLike(@PathVariable("id") Long id) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();

    User user = userRepository.findByUsername(userDetails.getUsername()).get();
    return postRepository.findById(id, true)
        .map(post -> postRepository.likePost(post, user))
        .map(postService::toDto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
