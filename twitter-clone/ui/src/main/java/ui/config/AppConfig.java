package ui.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import persistence.JsonPostRepository;
import persistence.JsonUserRepository;
import persistence.PostRepository;
import persistence.UserRepository;

/**
 * Spring configuration class. Defines beans used in dependency injection
 */
@Configuration
@ComponentScan(basePackages = { "ui", "persistence", "service" })
public class AppConfig {
  private Path dataDir;

  /**
   * Initializes the {@link AppConfig} and sets the data directory to "&lt;user
   * home&gt;/twitter-clone".
   */
  public AppConfig() {
    this.dataDir = Paths.get(System.getProperty("user.home"), "twitter-clone");
  }

  /**
   * Returns a {@link UserRepository} backed by JSON storage.
   *
   * @return the repository instance
   * @throws IOException if the data directory cannot be created
   */

  @Bean
  public UserRepository jsonUserRepository() throws IOException {
    Files.createDirectories(this.dataDir);
    return new JsonUserRepository(this.dataDir);
  }

  /**
   * Returns a {@link PostRepository} backed by JSON storage.
   *
   * @return the repository instance
   * @throws IOException if the data directory cannot be created
   */
  @Bean
  public PostRepository jsonPostRepository() throws IOException {
    Files.createDirectories(this.dataDir);
    return new JsonPostRepository(this.dataDir);
  }
}
