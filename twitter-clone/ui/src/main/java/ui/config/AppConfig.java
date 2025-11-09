package ui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class. Defines beans used in dependency injection. The
 * ui module doesn't need any additional beans, but it scans both the ui and
 * service packages to find components.
 */
@Configuration
@ComponentScan(basePackages = { "ui", "service" })
public class AppConfig {
}
