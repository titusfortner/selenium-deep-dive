package com.titusfortner.deep_dive;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WorkingTest {
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
