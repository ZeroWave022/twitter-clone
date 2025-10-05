package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import javafx.scene.control.Button;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;

/** Integration tests for {@link LogInController}. */
public class LogInControllerTest extends UiTestBase {
  @Test
  @DisplayName("A new user gets to log in, no matter their password")
  void testCorrectPassword() {
    WaitForAsyncUtils.waitForFxEvents();

    typeIntoTextInput("#usernameField", "username");
    FxAssert.verifyThat("#usernameField", TextInputControlMatchers.hasText("username"));

    typeIntoTextInput("#passwordField", "password");
    FxAssert.verifyThat("#passwordField", TextInputControlMatchers.hasText("password"));

    Button logInButton = lookup("#logInBtn").queryAs(Button.class);
    interact(logInButton::fire);

    NodeQuery nq = lookup(".label");
    assertFalse(nq.queryAll().isEmpty());
    FxAssert.verifyThat(nq, LabeledMatchers.hasText("MY TWITTER FEED"));
  }

  @Test
  @DisplayName("Testing incorrect password")
  void testIncorrectPassword() throws IOException {
    WaitForAsyncUtils.waitForFxEvents();

    // create a new user
    typeIntoTextInput("#usernameField", "username");
    FxAssert.verifyThat("#usernameField", TextInputControlMatchers.hasText("username"));

    typeIntoTextInput("#passwordField", "password");
    FxAssert.verifyThat("#passwordField", TextInputControlMatchers.hasText("password"));

    Button logInButton = lookup("#logInBtn").queryAs(Button.class);
    interact(logInButton::fire);

    // navigate back to the login view
    // FIXME: Click a log out / back button instead
    App.setRoot("login.fxml");

    // attempt login with wrong password
    typeIntoTextInput("#usernameField", "username");
    FxAssert.verifyThat("#usernameField", TextInputControlMatchers.hasText("username"));

    typeIntoTextInput("#passwordField", "wrong password");
    FxAssert.verifyThat("#passwordField", TextInputControlMatchers.hasText("wrong password"));

    logInButton = lookup("#logInBtn").queryAs(Button.class);
    interact(logInButton::fire);

    FxAssert.verifyThat("#errorLabel", LabeledMatchers.hasText("Incorrect password"));
  }
}
