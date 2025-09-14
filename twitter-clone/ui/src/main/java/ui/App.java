package ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene newScene = new Scene(root, 1280, 720);
        stage.setTitle("Twitter Clone");
        stage.setScene(newScene);
        stage.show();
        setScene(newScene);
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
