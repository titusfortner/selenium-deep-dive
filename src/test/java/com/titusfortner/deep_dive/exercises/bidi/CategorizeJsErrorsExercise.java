package com.titusfortner.deep_dive.exercises.bidi;

import com.titusfortner.deep_dive.TestBase;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

/*
 * EXERCISE — Capture and categorize the JS noise nytimes.com produces.
 *
 * Most large sites accumulate console errors and warnings nobody sees
 * because they don't surface in the UI. Use BiDi log handlers to find
 * them, then split first-party (NYT) from third-party.
 *
 * GOAL
 *   1. Register a console message handler AND a JavaScript error handler
 *      BEFORE navigating to nytimes.com.
 *   2. Wait long enough for the page to settle (a few seconds).
 *   3. Print the captured entries, splitting "mentions nytimes/nyt" from
 *      everything else.
 *
 * HINT
 *   ((RemoteWebDriver) driver).script().addConsoleMessageHandler(entry -> ...)
 *   ((RemoteWebDriver) driver).script().addJavaScriptErrorHandler(entry -> ...)
 *   Each entry has .getText(). Append getText() into a thread-safe list.
 *
 * SUCCESS
 *   At least one entry is captured. (NYT typically dumps dozens.) Print
 *   the categorization counts so you can see the breakdown.
 */
public class CategorizeJsErrorsExercise extends TestBase {

  @BeforeEach
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.enableBiDi();
    startChrome(options);
  }

  @Test
  void categorizeNytJsLogs() throws InterruptedException {
    CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<>();

    driver.get("https://www.nytimes.com");
    Thread.sleep(8000); // give async errors time to fire

    // TODO register a console message handler and a JS error handler.
    // Update the lists below to capture messages and errors not from host website

    List<String> thirdPartyMessages = List.of();
    List<String> thirdPartyErrors = List.of();

    Assertions.assertFalse(thirdPartyMessages.isEmpty(), "expected at least one log entry");
    Assertions.assertFalse(thirdPartyErrors.isEmpty(), "expected at least one log entry");
  }
}
