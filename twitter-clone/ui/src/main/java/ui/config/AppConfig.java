package ui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class. Defines beans used in dependency injection
 */
@Configuration
@ComponentScan(basePackages = { "ui", "persistence", "service" })
public class AppConfig {
}
