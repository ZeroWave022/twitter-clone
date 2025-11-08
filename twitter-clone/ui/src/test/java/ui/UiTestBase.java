package ui;

import java.nio.file.Path;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import persistence.OrmPostRepository;

// Taken from individual mandatory deliverable in IT1901
/**
 * Base class for UI tests.
 */
public class UiTestBase extends ApplicationTest {
  @TempDir
  Path tempDir;

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
    System.setProperty("app.data.directory", tempDir.toAbsolutePath().toString());
    ApplicationTest.launch(App.class);

    // Clean the database before each test
    new OrmPostRepository().dropDatabase();
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

  void typeIntoTextInput(String selector, String text) {
    TextInputControl field = lookup(selector).queryAs(TextInputControl.class);
    interact(() -> field.setText(text));
  }
}
