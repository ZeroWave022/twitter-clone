package core;

public class User {
  private String username;
  private String displayName;
  private String password;

  public User(String username, String displayName, String password) {
    this.username = username;
    this.displayName = displayName;
    this.password = password;
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
