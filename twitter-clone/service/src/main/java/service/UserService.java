package service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.User;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import persistence.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private User loggedInUser;

  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We need to inject the UserRepository service")
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @SuppressFBWarnings(value = "EI_EXPOSE_REP")
  @Transactional(readOnly = true)
  public User getLoggedInUser() {
    return loggedInUser;
  }

  private void setLoggedInUser(User loggedInUser) {
    this.loggedInUser = loggedInUser;
  }

  public boolean logIn(String username, String password) {
    Optional<User> maybeUser = getUserByUsername(username);

    if (maybeUser.isPresent()) {
      User user = maybeUser.get();
      if (user.getPassword().equals(password)) {
        setLoggedInUser(user);
        return true;
      }
      return false;
    }
    // New user gets to log in
    User newUser = new User(null, username, username, password); // make new user, repo handles ID
    createUser(newUser);
    setLoggedInUser(newUser);
    return true;
  }

  @Transactional(readOnly = true)
  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Transactional
  public User createUser(User user) {
    boolean userExists = this.userRepository.findByUsername(user.getUsername()).isPresent();
    if (userExists) {
      throw new IllegalArgumentException("Username \"" + user.getUsername() + "\" already exists");
    }

    return this.userRepository.save(user);
  }
}
