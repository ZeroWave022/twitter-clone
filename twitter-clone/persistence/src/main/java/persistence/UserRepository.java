package persistence;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;

import core.User;
import persistence.json.JsonRepository;

public class UserRepository {
  private final JsonRepository<User> jsonRepository;
  private List<User> users;

  public UserRepository(Path dataDirPath) {
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

    }
  }

  public List<User> getAllUsers() {
    return new ArrayList<>(this.users);
  }

  public Optional<User> getUserByUsername(String username) {
    return this.users.stream()
        .filter(user -> user.getUsername().equals(username))
        .findFirst();
  }

  public boolean addUser(User newUser) {
    if (this.getUserByUsername(newUser.getUsername()).isPresent()) {
      return false;
    }

    this.users.add(newUser);
    this.saveUsers();
    return true;
  }
}
