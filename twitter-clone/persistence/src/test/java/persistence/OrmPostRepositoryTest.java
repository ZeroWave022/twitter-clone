package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Post;
import core.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Integration test for {@link OrmPostRepository}. */
class OrmPostRepositoryTest {
  private OrmUserRepository userRepository = new OrmUserRepository();
  private OrmPostRepository postRepository = new OrmPostRepository();

  @BeforeEach
  void setup() {
    postRepository.dropDatabase();
  }

  @Test
  void test_emptyState() {
    List<Post> users = this.postRepository.findAll();
    assertEquals(0, users.size());
  }

  @Test
  void test_addPost() {
    User user = new User(null, "username", "display name", "password123");
    userRepository.save(user);

    String message = "Very important message";
    Post post = new Post(user, message, null);

    this.postRepository.save(post);
    assertTrue(post.getId() != null);

    List<Post> posts = this.postRepository.findAll();
    assertEquals(1, posts.size());
  }

  @Test
  void test_modifyPost() {
    User user = new User(null, "username", "display name", "password123");
    userRepository.save(user);

    String message = "Very important message";
    Post post = new Post(user, message, null);

    Post savedPost = this.postRepository.save(post);

    String newMessage = "Even more important message";
    savedPost.setContent(newMessage);
    this.postRepository.update(savedPost);

    List<Post> posts = this.postRepository.findAll();
    assertEquals(1, posts.size());
    Optional<Post> fetchedPost = this.postRepository.findById(savedPost.getId());
    assertEquals(newMessage, fetchedPost.get().getContent());
  }

  @Test
  @DisplayName("User likes a post")
  void test_likePostIncrementsLikes() {
    User user = new User(null, "username", "display name", "password123");
    userRepository.save(user);

    Post post = new Post(user, "A post to like", null);
    postRepository.save(post);

    // Like the post
    postRepository.likePost(post, user);

    Post updatedPost = postRepository.findById(post.getId()).orElseThrow();
    assertEquals(1, updatedPost.getLikes());
    assertTrue(updatedPost.getLikedByUsers().contains(user));
  }

  @Test
  @DisplayName("User likes and then unlikes a post")
  void test_likePostToggles() {
    User user = new User(null, "username", "display name", "password123");
    userRepository.save(user);

    Post post = new Post(user, "Another post", null);
    postRepository.save(post);

    // Like once
    postRepository.likePost(post, user);
    Post likedPost = postRepository.findById(post.getId()).orElseThrow();
    assertEquals(1, likedPost.getLikes());

    // Unlike
    postRepository.likePost(post, user);
    Post unlikedPost = postRepository.findById(post.getId()).orElseThrow();
    assertEquals(0, unlikedPost.getLikes());
    assertFalse(unlikedPost.getLikedByUsers().contains(user));
  }
}
