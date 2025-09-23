package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

import core.User;
import persistence.json.JsonRepository;

@Repository
public class JsonUserRepository implements UserRepository {
  private AtomicLong nextId = new AtomicLong(0);
  private final JsonRepository<User> jsonRepository;
  private List<User> users;

  public JsonUserRepository(@Value("${app.data.directory}") String dataDirPath) throws IOException {
    Path dataDir = Paths.get(dataDirPath);
    Files.createDirectories(dataDir);

    this.jsonRepository = new JsonRepository<>(dataDir.resolve("users.json"), new TypeReference<List<User>>() {
    });

    // FIXME: Don't load every user into memory :)
    this.loadUsers();
    this.nextId.set(this.users.stream().map(User::getId).max(Comparator.naturalOrder()).orElse(0L) + 1);
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
