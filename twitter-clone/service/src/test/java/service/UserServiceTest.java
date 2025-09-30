package service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import core.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  private User user;

  @BeforeEach
  void setup() {
    this.userService = new UserService(userRepository);
    this.user = new User(1L, "john", "John Pork", "pass");
  }

  @Test
  void getUserByUsernameReturnsEmptyWhenNotFound() {
    when(userRepository.findByUsername("abcd")).thenReturn(Optional.empty());
    Optional<User> result = userService.getUserByUsername("abcd");
    assertTrue(result.isEmpty());
  }

  @Test
  void getUserByUsernameReturnsUserWhenFound() {
    when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    Optional<User> result = userService.getUserByUsername(user.getUsername());
    assertEquals(result.get(), user);
  }

  @Test
  void createUserSavesUserWhenUsernameIsUnique() {
    when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
    when(userRepository.save(user)).thenReturn(user);
    User result = assertDoesNotThrow(() -> userService.createUser(user));
    assertEquals(result, user);
  }

  @Test
  void createUserThrowsExceptionWhenUsernameExists() {
    when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
  }
}
