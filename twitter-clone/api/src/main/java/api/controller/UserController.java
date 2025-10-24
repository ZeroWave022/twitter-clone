package api.controller;

import core.User;
import core.payload.request.UpdateUserRequest;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import persistence.UserRepository;

/**
 * Controller for handling user-related API requests. Provides endpoints for
 * creating, retrieving, updating, and listing users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  /**
   * Constructs a new UserController.
   *
   * @param userRepository the repository to use for user operations
   */
  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Spring Repository is thread-safe")
  public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;

  }

  /**
   * Get all users.
   *
   * @return list of all users
   */
  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Get a user by their ID.
   *
   * @param id the ID of the user
   * @return the user if found, or 404 if not found
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
    return userRepository.findById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Create a new user.
   *
   * @param user the user to create
   * @return the created user
   */
  @PostMapping()
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
  }

  /**
   * Update an existing user.
   *
   * @param id                the ID of the user to update
   * @param updateUserRequest the updated user data
   * @return the updated user if found, or 404 if not found
   */
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable("id") Long id,
      @RequestBody UpdateUserRequest updateUserRequest) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    String username = userDetails.getUsername();

    Optional<User> maybeUser = userRepository.findById(id);
    if (maybeUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    User existingUser = maybeUser.get();
    if (!existingUser.getUsername().equals(username)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    existingUser.setDisplayName(updateUserRequest.getDisplayName());
    existingUser.setUsername(updateUserRequest.getUsername());
    existingUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
    return ResponseEntity.ok(userRepository.update(existingUser));
  }
}
