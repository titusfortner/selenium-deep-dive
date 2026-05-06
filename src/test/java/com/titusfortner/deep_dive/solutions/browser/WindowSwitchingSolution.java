package com.titusfortner.deep_dive.solutions.browser;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WindowType;

public class WindowSwitchingSolution extends TestBase {

  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  void findWindowByTitle() {
    driver.get("https://www.example.com");

    driver.switchTo().newWindow(WindowType.TAB);
    driver.get("https://www.selenium.dev");

    driver.switchTo().newWindow(WindowType.TAB);
    driver.get("https://en.wikipedia.org");

    for (String handle : driver.getWindowHandles()) {
      driver.switchTo().window(handle);
      if (driver.getTitle().contains("Selenium")) {
        break;
      }
    }

    Assertions.assertTrue(
        driver.getTitle().contains("Selenium"),
        "should be on the Selenium tab, got: " + driver.getTitle());
  }
}
