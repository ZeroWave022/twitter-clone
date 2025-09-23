package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;

public class ControllerTest extends UiTestBase {

  @Test
  @DisplayName("The test user is able to log in with correct password")
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
  void testIncorrectPassword() {
    WaitForAsyncUtils.waitForFxEvents();

    clickOn("#usernameField").write("test user");
    clickOn("#passwordField").write("wrong password");
    clickOn("#logInBtn");

    // WaitForAsyncUtils.waitForFxEvents();
    NodeQuery nq = lookup("#errorLabel");
    assertFalse(nq.queryAll().isEmpty());
    FxAssert.verifyThat(nq, LabeledMatchers.hasText("Incorrect password"));
  }
}
