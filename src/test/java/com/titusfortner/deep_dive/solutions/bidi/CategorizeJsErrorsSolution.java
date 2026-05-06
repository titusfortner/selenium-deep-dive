package com.titusfortner.deep_dive.solutions.bidi;

import com.titusfortner.deep_dive.TestBase;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CategorizeJsErrorsSolution extends TestBase {

  @BeforeEach
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.enableBiDi();
    startChrome(options);
  }

  @Test
  void categorizeNytJsLogs() throws InterruptedException {
    CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<String> errors = new CopyOnWriteArrayList<>();

    ((RemoteWebDriver) driver).script().addConsoleMessageHandler(entry -> messages.add(entry.getText()));
    ((RemoteWebDriver) driver).script().addJavaScriptErrorHandler(entry -> errors.add(entry.getText()));

    driver.get("https://www.nytimes.com");
    Thread.sleep(8000);

    List<String> thirdPartyMessages = messages.stream()
        .filter(m -> m.toLowerCase().contains("nyt"))
        .collect(Collectors.toList());
    List<String> thirdPartyErrors = errors.stream()
        .filter(e -> !e.toLowerCase().contains("nyt"))
        .collect(Collectors.toList());

    Assertions.assertFalse(thirdPartyMessages.isEmpty(), "expected at least one log entry");
    Assertions.assertFalse(thirdPartyErrors.isEmpty(), "expected at least one log entry");
  }
}
