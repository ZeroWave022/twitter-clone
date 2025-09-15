package persistence;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import core.User;
import persistence.json.JsonRepository;

public class JsonUserRepository {
  private final JsonRepository<User> jsonRepository;
  private List<User> users;

  public JsonUserRepository(Path dataDirPath) {
    this.jsonRepository = new JsonRepository<>(dataDirPath.resolve("users.json"));

    // FIXME: Don't load every user into memory :)
    this.loadUsers();
  }

  private void loadUsers() {
    try {
      this.users = this.jsonRepository.load();
    } catch (IOException e) {
      this.users = new ArrayList<>();
    }
  }

  public void saveUsers() {
    try {
      this.jsonRepository.save(this.users);
    } catch (IOException e) {
      System.err.println(e);
    }
  }

  public void addUser(User user) {
    this.users.add(user);
    this.saveUsers();
  }

  @Override
  public Optional<User> findById(Long id) {
    return this.users.stream().filter(user -> user.getId().equals(id)).findFirst();
  }

  @Override
  public List<User> findAll() {
    return new ArrayList<>(this.users);
  }

  @Override
  public User save(User user) {
    if (user.getId() == null) {
      user.setId(nextId.getAndIncrement());
    }

    this.addUser(user);
    return user;
  }

  @Override
  public void deleteById(Long id) {
    this.users.removeIf(user -> user.getId().equals(id));
  }

  @Override
  public boolean existsById(Long id) {
    return this.users.stream().anyMatch(user -> user.getId().equals(id));
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return this.users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
  }
}
