package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import org.springframework.util.FileSystemUtils;

class JsonUserRepositoryTest {
  @TempDir
  private Path dataDir;
  private JsonUserRepository userRepository;
  private User user;
  private Long id;
  private String username;

  @BeforeEach
  void setup() throws IOException {
    this.userRepository = new JsonUserRepository(dataDir.toAbsolutePath().toString());
    String username = "username";
    Long id = 123L;
    User user = new User(id, username, "display name", "password123");
    this.username = username;
    this.id = id;
    this.user = user;
  }

  @AfterEach
  void cleanup() throws IOException {
    // Delete the temp directory and its files
    FileSystemUtils.deleteRecursively(this.dataDir);
  }

  @Test
  void test_emptyState() {
    List<User> users = this.userRepository.findAll();
    assertEquals(0, users.size());
  }

  @Test
  void test_addUser() {
    User savedUser = this.userRepository.save(user);
    assertTrue(savedUser.getId() != null);

    List<User> users = this.userRepository.findAll();
    assertEquals(1, users.size());

    JsonUserRepository loadedRepository = new JsonUserRepository(
        this.dataDir.toAbsolutePath().toString());
    List<User> loadedUsers = loadedRepository.findAll();
    assertEquals(1, loadedUsers.size());
  }

  @Test
  void test_findById() {
    // returns empty when not found
    assertTrue(userRepository.findById(id).isEmpty());
    userRepository.save(user);

    // return user when found
    assertEquals(userRepository.findById(id).get().getId(), user.getId());
  }

  @Test
  void test_existsById() {
    assertFalse(userRepository.existsById(id));
    userRepository.save(user);
    assertTrue(userRepository.existsById(id));
  }

  @Test
  void test_findByUsername() {
    assertTrue(userRepository.findByUsername(username).isEmpty());
    userRepository.save(user);
    assertEquals(userRepository.findByUsername(username).get(), user);

  }

  @Test
  void test_deleteById() {
    userRepository.save(user);
    assertTrue(userRepository.findById(id).isPresent());

    userRepository.deleteById(id);
    assertTrue(userRepository.findById(id).isEmpty());
  }
}
