package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import core.Post;
import core.User;

class JsonPostRepositoryTest {
  @TempDir
  private Path dataDir;
  private JsonPostRepository postRepository;

  @BeforeEach
  void setup() throws IOException {
    this.postRepository = new JsonPostRepository(dataDir.toAbsolutePath().toString());
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
    String message = "Very important message";
    Long id = 123L;
    Post post = new Post(user, message, id);

    this.postRepository.save(post);

    List<Post> posts = this.postRepository.findAll();
    assertEquals(1, posts.size());

    JsonPostRepository loadedRepository = new JsonPostRepository(
        this.dataDir.toAbsolutePath().toString());
    List<Post> loadedPosts = loadedRepository.findAll();
    assertEquals(1, loadedPosts.size());
  }
}
