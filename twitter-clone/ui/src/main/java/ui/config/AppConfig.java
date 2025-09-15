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

@Configuration
@ComponentScan(basePackages = { "ui", "persistence", "service" })
public class AppConfig {
  private Path dataDir;

  public AppConfig() {
    this.dataDir = Paths.get(System.getProperty("user.home"), "twitter-clone");
  }

  @Bean
  public UserRepository jsonUserRepository() throws IOException {
    Files.createDirectories(this.dataDir);
    return new JsonUserRepository(this.dataDir);
  }

  @Bean
  public PostRepository jsonPostRepository() throws IOException {
    Files.createDirectories(this.dataDir);
    return new JsonPostRepository(this.dataDir);
  }
}
