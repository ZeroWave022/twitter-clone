package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.User;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Integration test for {@link OrmUserRepository}. */
class OrmUserRepositoryTest {
  private OrmUserRepository userRepository = new OrmUserRepository();
  private User user;

  @BeforeEach
  void setup() throws IOException {

    // Initialize test user
    String username = "username";
    User user = new User(null, username, "display name", "password123");
    this.user = user;
    userRepository.dropDatabase();
  }

  @Test
  void test_emptyState() {
    List<User> users = this.userRepository.findAll();
    assertEquals(0, users.size());
  }

  @Test
  void test_addUser() {
    userRepository.save(user);
    assertTrue(user.getId() != null);

    List<User> users = this.userRepository.findAll();
    assertEquals(1, users.size());
  }

  @Test
  void test_modifyUser() {
    userRepository.save(user);
    assertTrue(user.getId() != null);

    Optional<User> fetchedUser = userRepository.findById(user.getId());
    assertEquals("display name", fetchedUser.get().getDisplayName());

    fetchedUser.get().setDisplayName("new display name");
    userRepository.update(fetchedUser.get());

    Optional<User> modifiedUser = userRepository.findById(user.getId());
    assertEquals("new display name", modifiedUser.get().getDisplayName());
  }

  @Test
  void test_findByUsername() {
    assertTrue(userRepository.findByUsername(user.getUsername()).isEmpty());

    userRepository.save(user);
    assertEquals(userRepository.findByUsername(user.getUsername()).get(), user);
  }

  @Test
  void test_deleteById() {
    userRepository.save(user);
    assertTrue(userRepository.findById(user.getId()).isPresent());

    userRepository.deleteById(user.getId());
    assertTrue(userRepository.findById(user.getId()).isEmpty());
  }

  @Test
  void test_existsById() {
    userRepository.save(user);
    assertTrue(userRepository.existsById(user.getId()));

    userRepository.deleteById(user.getId());
    assertFalse(userRepository.existsById(user.getId()));
  }
}
