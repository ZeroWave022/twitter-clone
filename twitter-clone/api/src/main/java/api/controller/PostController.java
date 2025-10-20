package api.controller;

import core.Post;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.PostService;

/**
 * REST controller for managing Post entities. Provides endpoints for CRUD
 * operations on posts.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
  @Autowired
  private PostService postService;

  /**
   * Constructs a new PostController.
   *
   * @param postService the service to use for post operations
   */
  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  /**
   * Retrieves a post by its ID.
   *
   * @param id the ID of the post to retrieve
   * @return the post if found, or 404 if not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getPost(@PathVariable Long id) {
    return postService.getPostById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Retrieves all posts.
   *
   * @return list of all posts
   */
  @GetMapping
  public ResponseEntity<List<Post>> getAllPosts() {
    return ResponseEntity.ok(postService.getAllPosts());
  }

  /**
   * Creates a new post.
   *
   * @param post the post to create
   * @return the created post
   */
  @PostMapping
  public ResponseEntity<?> createPost(@RequestBody Post post) {
    try {
      return ResponseEntity.ok(postService.createPost(post));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}