package com.titusfortner.deep_dive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WorkingTest {
  @Test
  public void validateChrome() {
    WebDriver driver = new ChromeDriver();

    driver.get("https://www.selenium.dev");
    Assertions.assertEquals("https://www.selenium.dev/", driver.getCurrentUrl());
    driver.quit();
  }

  @Test
  public void validateFirefox() {
    WebDriver driver = new FirefoxDriver();

    driver.get("https://www.selenium.dev");
    Assertions.assertEquals("https://www.selenium.dev/", driver.getCurrentUrl());
    driver.quit();
  }

  @Test
  public void validateEdge() {
    WebDriver driver = new EdgeDriver();

    driver.get("https://www.selenium.dev");
    Assertions.assertEquals("https://www.selenium.dev/", driver.getCurrentUrl());
    driver.quit();
  }
}
