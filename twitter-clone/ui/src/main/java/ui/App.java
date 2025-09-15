package ui;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication(scanBasePackages = { "twitter.ui", "twitter.persistence" })
public class App extends Application {
  private static Scene scene;

  private ConfigurableApplicationContext springContext;

  @Override
  public void init() throws Exception {
    springContext = SpringApplication.run(App.class);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
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

  private static void setScene(Scene newScene) {
    scene = newScene;
  }

  public static void setRoot(String fxml) throws IOException {
    scene.setRoot(FXMLLoader.load(App.class.getResource(fxml)));
  }

  public static void main(String[] args) {
    launch(args);
  }
}
