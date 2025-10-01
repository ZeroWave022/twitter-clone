package persistence;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Post;
import core.User;

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
}
