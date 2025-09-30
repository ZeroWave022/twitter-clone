package persistence;

import core.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
  Optional<User> findById(Long id);

  List<User> findAll();

  User save(User user);

  User update(User user);

  void deleteById(Long id);

  boolean existsById(Long id);

  Optional<User> findByUsername(String username);
}
