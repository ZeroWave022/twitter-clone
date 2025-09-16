package persistence;

import java.util.List;
import java.util.Optional;

import core.User;

public interface UserRepository {
  Optional<User> findById(Long id);

  List<User> findAll();

  User save(User user);

  void deleteById(Long id);

  boolean existsById(Long id);

  Optional<User> findByUsername(String username);
}
