package api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import persistence.OrmPostRepository;
import persistence.OrmUserRepository;
import persistence.PostRepository;
import persistence.UserRepository;

/**
 * Spring configuration class. Defines beans used in dependency injection
 */
@Configuration
@ComponentScan(basePackages = { "ui", "persistence", "service" })
public class AppConfig {
  /**
   * Returns a {@link UserRepository} backed by SQLite storage.
   *
   * @return the repository instance
   */
  @Bean
  public UserRepository ormUserRepository() {
    return new OrmUserRepository();
  }

  /**
   * Returns a {@link PostRepository} backed by SQLite storage.
   *
   * @return the repository instance
   */
  @Bean
  public PostRepository ormPostRepository() {
    return new OrmPostRepository();
  }
}
