package com.titusfortner.deep_dive.exercises.browser;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WindowType;

/*
 * EXERCISE — Switch to the right tab among several.
 *
 * Three tabs are open. We're currently focused on the wrong one. Find
 * the Selenium docs tab (its <title> contains "Selenium") and switch to
 * it. The naive mistake here is calling switchTo().window("Selenium") —
 * window() takes a *handle*, not a title or URL. You have to iterate
 * handles, switch into each, check the title, and break.
 *
 * GOAL
 *   Make the assertion at the bottom pass without changing the lines that
 *   open the three tabs.
 *
 * HINT
 *   driver.getWindowHandles() returns a Set<String>. Iterate, switch,
 *   check getTitle(), break when you find the right one.
 *
 * SUCCESS
 *   driver.getTitle().contains("Selenium")
 */
public class WindowSwitchingExercise extends TestBase {

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

    // Currently focused on the Wikipedia tab.

    // TODO switch baack to Selenium Tab".

    Assertions.assertTrue(
        driver.getTitle().contains("Selenium"),
        "should be on the Selenium tab, got: " + driver.getTitle());
  }
}
