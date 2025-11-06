package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.Post;
import core.User;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JsonPostRepositoryTest {
  @TempDir
  private Path dataDir;
  private JsonPostRepository postRepository;
  private User user;
  private Long id;
  private Post post;

  @BeforeEach
  void setup() throws IOException {
    this.postRepository = new JsonPostRepository(dataDir.toAbsolutePath().toString());

    // Initialize test user and post
    User user = new User(null, "username", "display name", "password123");
    String message = "Very important message";
    Long id = 123L;
    Post post = new Post(user, message, id);
    this.user = user;
    this.id = id;
    this.post = post;
  }

  @AfterEach
  void cleanup() throws IOException {
    // Delete the temp directory and its files
    try (Stream<Path> walk = Files.walk(this.dataDir)) {
      walk.forEach(filePath -> {
        try {
          Files.delete(filePath);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }
  }

  @Test
  void test_emptyState() {
    List<Post> users = this.postRepository.findAll();
    assertEquals(0, users.size());
  }

  @Test
  void test_addPost() {
    this.postRepository.save(post);

    List<Post> posts = this.postRepository.findAll();
    assertEquals(1, posts.size());

    JsonPostRepository loadedRepository = new JsonPostRepository(
        this.dataDir.toAbsolutePath().toString());
    List<Post> loadedPosts = loadedRepository.findAll();
    assertEquals(1, loadedPosts.size());
  }

  @Test
  void test_findAllWithRelations() {
    assertThrows(UnsupportedOperationException.class, () -> postRepository.findAll(true));
  }

  @Test
  void test_findByIdWithRelations() {
    assertThrows(UnsupportedOperationException.class, () -> postRepository.findById(id, true));
  }

  @Test
  void test_findById() {
    postRepository.save(post);
    assertEquals(post, postRepository.findById(id).get());

    // returns empty when not found
    assertTrue(postRepository.findById(999L).isEmpty());
  }

  @Test
  void test_existsById() {
    postRepository.save(post);
    assertTrue(postRepository.existsById(id));

    // returns false when not found
    assertFalse(postRepository.existsById(999L));

  }

  @Test
  void test_likePost() {
    assertThrows(UnsupportedOperationException.class, () -> postRepository.likePost(post, user));
  }

  @Test
  void test_updateRetweetCount() {
    assertThrows(UnsupportedOperationException.class,
        () -> postRepository.updateRetweetCount(post));
  }

  @Test
  void test_deleteById() {
    postRepository.save(post);
    assertTrue(postRepository.findById(id).isPresent());

    postRepository.deleteById(id);
    assertTrue(postRepository.findById(id).isEmpty());
  }
}
