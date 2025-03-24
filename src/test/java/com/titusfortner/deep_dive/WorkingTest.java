package com.titusfortner.deep_dive;

import com.titusfortner.logging.SeleniumLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WorkingTest {
  /** Turn on logging for Selenium for all tests in this class */
  @BeforeAll
  public static void enableLogging() {
    SeleniumLogger.enable();
  }

  /** Verify that Selenium can drive each of these browsers */
  @Test
  public void validateChrome() {
    WebDriver driver = new ChromeDriver();

    driver.quit();
  }

  @Test
  public void validateFirefox() {
    WebDriver driver = new FirefoxDriver();

    driver.quit();
  }

  @Test
  public void validateEdge() {
    WebDriver driver = new EdgeDriver();

    driver.quit();
  }
}
