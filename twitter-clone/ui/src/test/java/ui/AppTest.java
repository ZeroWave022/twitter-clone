package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;

// Taken from individual mandatory deliverable in IT1901
/** Integration tests for {@link App}. */
public class AppTest extends UiTestBase {
  @Test
  @DisplayName("The application should load the log in view")
  void shouldLoadPrimaryView() {
    WaitForAsyncUtils.waitForFxEvents();

    NodeQuery nq = lookup("#usernameField");
    assertFalse(nq.queryAll().isEmpty());

    nq = lookup("#passwordField");
    assertFalse(nq.queryAll().isEmpty());

    nq = lookup("#logInBtn");
    assertFalse(nq.queryAll().isEmpty());

    FxAssert.verifyThat("#logInBtn", LabeledMatchers.hasText("Log in"));
  }
}
