package core;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue
  private Long id;

  private String username;
  private String displayName;
  private String password;

  @OneToMany(mappedBy = "author")
  private List<Post> posts;

  public User() {
  }

  public User(Long id, String username, String displayName, String password) {
    this.id = id;
    this.username = username;
    this.displayName = displayName;
    this.password = password;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
