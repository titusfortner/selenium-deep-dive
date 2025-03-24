package com.titusfortner.deep_dive.browser;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;

public class AlertHandlingTest extends TestBase {
  @BeforeEach
  public void start() {
    startChrome();
  }

  @Test
  public void alertHandling() {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("alert('Hello World');");

    Alert alert = driver.switchTo().alert();

    // "Hello World"
    alert.getText();

    // Close Alert
    alert.dismiss();
  }
}
