package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;

/**
 * Integration tests for {@link Controller}.
 */
public class ControllerTest extends UiTestBase {
  @Test
  @DisplayName("A new user gets to log in, no matter their password")
  void testCorrectPassword() {
    WaitForAsyncUtils.waitForFxEvents();

    clickOn("#usernameField").write("test user");
    clickOn("#passwordField").write("test password12314325678");
    clickOn("#logInBtn");

    WaitForAsyncUtils.waitForFxEvents();
    NodeQuery nq = lookup(".label");
    assertFalse(nq.queryAll().isEmpty());
    FxAssert.verifyThat(nq, LabeledMatchers.hasText("MY TWITTER FEED"));
  }

  @Test
  @DisplayName("Testing incorrect password")
  void testIncorrectPassword() throws IOException {
    WaitForAsyncUtils.waitForFxEvents();

    // create a user
    clickOn("#usernameField").write("username");
    clickOn("#passwordField").write("password");
    clickOn("#logInBtn");

    // navigate back to the login view
    // FIXME: Click a log out / back button instead
    App.setRoot("login.fxml");

    // attempt login with wrong password
    clickOn("#usernameField").write("username");
    clickOn("#passwordField").write("wrong password");
    clickOn("#logInBtn");

    FxAssert.verifyThat(lookup("#errorLabel"), LabeledMatchers.hasText("Incorrect password"));
  }
}
