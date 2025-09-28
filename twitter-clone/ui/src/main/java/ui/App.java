package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = { "ui", "persistence", "service" })
public class App extends Application {
  private static Scene scene;

  private static ConfigurableApplicationContext springContext;

  @Override
  public void init() throws Exception {
    setSpringContext(SpringApplication.run(App.class));
  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
    loader.setControllerFactory(springContext::getBean);
    Parent root = loader.load();
    Scene newScene = new Scene(root, 1280, 720);
    stage.setTitle("Twitter Clone");
    stage.setScene(newScene);
    stage.show();
    setScene(newScene);
  }

  @Override
  public void stop() throws Exception {
    springContext.close();
    Platform.exit();
  }

  private static void setSpringContext(ConfigurableApplicationContext newSpringContext) {
    springContext = newSpringContext;
  }

  private static void setScene(Scene newScene) {
    scene = newScene;
  }

  public static void setRoot(String fxml) throws IOException {
    FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
    loader.setControllerFactory(springContext::getBean);
    scene.setRoot(loader.load());
  }

  public static void main(String[] args) {
    launch(args);
  }
}
