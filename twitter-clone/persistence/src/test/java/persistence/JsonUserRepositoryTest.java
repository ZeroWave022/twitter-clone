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

import core.User;

class JsonUserRepositoryTest {
  private Path dataDir;
  private JsonUserRepository userRepository;

  @BeforeEach
  void setup() throws IOException {
    this.dataDir = Files.createTempDirectory("users");
    this.userRepository = new JsonUserRepository(this.dataDir);
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
    List<User> users = this.userRepository.getAllUsers();
    assertEquals(0, users.size());
  }

  @Test
  void test_addUser() {
    User user = new User("username", "display name", "password123");

    boolean wasAdded = this.userRepository.addUser(user);
    assertTrue(wasAdded);

    List<User> users = this.userRepository.getAllUsers();
    assertEquals(1, users.size());

    JsonUserRepository loadedRepository = new JsonUserRepository(this.dataDir);
    List<User> loadedUsers = loadedRepository.getAllUsers();
    assertEquals(1, loadedUsers.size());
  }
}
