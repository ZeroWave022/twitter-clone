package ui.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import persistence.JsonUserRepository;
import persistence.UserRepository;

@Configuration
@ComponentScan(basePackages = { "ui", "twitter.persistence" })
public class AppConfig {
  private Path dataDir;

  public AppConfig() throws IOException {
    this.dataDir = Paths.get(System.getProperty("user.home"), "twitter-clone");
    Files.createDirectories(this.dataDir);
  }

  @Bean
  public UserRepository jsonUserRepository() {
    return new JsonUserRepository(this.dataDir);
  }
}
