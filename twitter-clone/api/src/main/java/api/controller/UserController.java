package api.controller;

import core.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  @Autowired
  private final UserRepository userRepository;

  @Autowired
  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
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
  public ResponseEntity<User> getUser(@PathVariable Long id) {
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
   * @param id          the ID of the user to update
   * @param updatedUser the updated user data
   * @return the updated user if found, or 404 if not found
   */
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
    return userRepository.findById(id).map(existingUser -> {
      existingUser.setDisplayName(updatedUser.getDisplayName());
      existingUser.setUsername(updatedUser.getUsername());
      existingUser.setPassword(updatedUser.getPassword());
      return ResponseEntity.ok(userRepository.update(existingUser));
    }).orElse(ResponseEntity.notFound().build());
  }
}