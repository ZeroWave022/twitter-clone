package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

  @BeforeEach
  void setup() throws IOException {
    userRepository.dropDatabase();
  }

  @Test
  void test_emptyState() {
    List<User> users = this.userRepository.findAll();
    assertEquals(0, users.size());
  }

  @Test
  void test_addUser() {
    User user = new User(null, "username", "display name", "password123");
    userRepository.save(user);
    assertTrue(user.getId() != null);

    List<User> users = this.userRepository.findAll();
    assertEquals(1, users.size());
  }

  @Test
  void test_modifyUser() {
    User user = new User(null, "username", "display name", "password123");
    userRepository.save(user);
    assertTrue(user.getId() != null);

    Optional<User> fetchedUser = userRepository.findById(user.getId());
    assertEquals("display name", fetchedUser.get().getDisplayName());

    fetchedUser.get().setDisplayName("new display name");
    userRepository.update(fetchedUser.get());

    Optional<User> modifiedUser = userRepository.findById(user.getId());
    assertEquals("new display name", modifiedUser.get().getDisplayName());
  }
}
