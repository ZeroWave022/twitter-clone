package service;

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

  @Autowired
  @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "We need to inject the UserRepository service")
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
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
