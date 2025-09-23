package ui;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

// Taken from individual mandatory deliverable in IT1901
public class UiTestBase extends ApplicationTest {
  @BeforeAll
  static void beforeAllTests() {
    if (Boolean.getBoolean("headless")) {
      System.setProperty("testfx.robot", "glass");
      System.setProperty("testfx.headless", "true");
      System.setProperty("prism.order", "sw");
      System.setProperty("prism.text", "t2k");
      System.setProperty("java.awt.headless", "true");
    }
  }

  @BeforeEach
  void setup() throws Exception {
    ApplicationTest.launch(App.class);
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.show();
  }

  @AfterEach
  void cleanup() throws TimeoutException {
    FxToolkit.hideStage();
    release(new KeyCode[] {});
    release(new MouseButton[] {});
  }
}
