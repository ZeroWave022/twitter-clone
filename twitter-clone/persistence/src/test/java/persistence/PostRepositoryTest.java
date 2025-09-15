package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Post;
import core.User;

class PostRepositoryTest {
  private Path dataDir;
  private PostRepository userRepository;

  @BeforeEach
  void setup() throws IOException {
    this.dataDir = Files.createTempDirectory("users");
    this.userRepository = new PostRepository(this.dataDir);
  }

  @AfterEach
  void cleanup() throws IOException {
    // Delete the temp directory and its files
    try (Stream<Path> walk = Files.walk(this.dataDir)) {
      walk.forEach(filePath -> {
        try {
          Files.delete(filePath);
        } catch (IOException e) {
        }
      });
    }
  }

  @Test
  void test_emptyState() {
    List<Post> users = this.userRepository.getAllPosts();
    assertEquals(0, users.size());
  }

  @Test
  void test_addPost() {

    User user = new User(null, "username", "display name", "password123");
    String message = "Very imporant message";
    String id = "123";
    Post post = new Post(user, message, id);

    boolean wasAdded = this.userRepository.addPost(post);
    assertTrue(wasAdded);

    List<Post> posts = this.userRepository.getAllPosts();
    assertEquals(1, posts.size());

    PostRepository loadedRepository = new PostRepository(this.dataDir);
    List<Post> loadedPosts = loadedRepository.getAllPosts();
    assertEquals(1, loadedPosts.size());
  }
}
