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

class JsonPostRepositoryTest {
  private Path dataDir;
  private JsonPostRepository postRepository;

  @BeforeEach
  void setup() throws IOException {
    this.dataDir = Files.createTempDirectory("users");
    this.postRepository = new JsonPostRepository(this.dataDir);
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
    List<Post> users = this.postRepository.findAll();
    assertEquals(0, users.size());
  }

  @Test
  void test_addPost() {
    User user = new User(null, "username", "display name", "password123");
    String message = "Very imporant message";
    String id = "123";
    Post post = new Post(user, message, id);

    this.postRepository.save(post);

    List<Post> posts = this.postRepository.findAll();
    assertEquals(1, posts.size());

    JsonPostRepository loadedRepository = new JsonPostRepository(this.dataDir);
    List<Post> loadedPosts = loadedRepository.findAll();
    assertEquals(1, loadedPosts.size());
  }
}
