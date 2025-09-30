package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

class JsonUserRepositoryTest {
  @TempDir
  private Path dataDir;
  private JsonUserRepository userRepository;

  @BeforeEach
  void setup() throws IOException {
    this.userRepository = new JsonUserRepository(dataDir.toAbsolutePath().toString());
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
    List<User> users = this.userRepository.findAll();
    assertEquals(0, users.size());
  }

  @Test
  void test_addUser() {
    User user = new User(null, "username", "display name", "password123");

    User savedUser = this.userRepository.save(user);
    assertTrue(savedUser.getId() != null);

    List<User> users = this.userRepository.findAll();
    assertEquals(1, users.size());

    JsonUserRepository loadedRepository = new JsonUserRepository(
        this.dataDir.toAbsolutePath().toString());
    List<User> loadedUsers = loadedRepository.findAll();
    assertEquals(1, loadedUsers.size());
  }
}
