package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserTest {
  @Test
  void testNoArgConstructorAndSetters() {
    User user = new User();

    user.setId(1L);
    user.setUsername("johndoe");
    user.setDisplayName("John Doe");
    user.setPassword("secret123");

    assertEquals(1L, user.getId());
    assertEquals("johndoe", user.getUsername());
    assertEquals("John Doe", user.getDisplayName());
    assertEquals("secret123", user.getPassword());
  }

  @Test
  void testAllArgsConstructorAndGetters() {
    User user = new User(2L, "janedoe", "Jane Doe", "password456");

    assertEquals(2L, user.getId());
    assertEquals("janedoe", user.getUsername());
    assertEquals("Jane Doe", user.getDisplayName());
    assertEquals("password456", user.getPassword());
  }

  @Test
  void testSettersUpdateValues() {
    User user = new User(3L, "user", "User Display", "initial");

    user.setUsername("updatedUser");
    user.setDisplayName("Updated Display");
    user.setPassword("newPassword");
    user.setId(10L);

    assertEquals(10L, user.getId());
    assertEquals("updatedUser", user.getUsername());
    assertEquals("Updated Display", user.getDisplayName());
    assertEquals("newPassword", user.getPassword());
  }
}
