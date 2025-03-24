package com.titusfortner.deep_dive;

import com.titusfortner.logging.ChromeDriverLogger;
import com.titusfortner.logging.EdgeDriverLogger;
import com.titusfortner.logging.GeckoDriverLogger;
import com.titusfortner.logging.SeleniumLogger;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.grid.Main;

public class TestBase {
  protected WebDriver driver;
  protected static URL gridUrl;

  @BeforeAll
  public static void enableLogging() throws MalformedURLException {
    SeleniumLogger.enable();
    // Default value
    gridUrl = new URL("http://localhost:4444/");
  }

  public void startPatientChrome(ChromeOptions options) {
    options.setImplicitWaitTimeout(Duration.ofSeconds(2));
    startChrome(options);
  }

  public void startChrome() {
    startChrome(new ChromeOptions());
  }

  public void startChrome(ChromeOptions options) {
    ChromeDriverLogger.enable();
    driver = new ChromeDriver(options);
  }

  public void startEdge() {
    startEdge(new EdgeOptions());
  }

  public void startEdge(EdgeOptions options) {
    EdgeDriverLogger.enable();
    driver = new EdgeDriver(options);
  }

  public void startFirefox() {
    startFirefox(new FirefoxOptions());
  }

  public void startPatientFirefox(FirefoxOptions options) {
    options.setImplicitWaitTimeout(Duration.ofSeconds(2));
    startFirefox(options);
  }

  public void startFirefox(FirefoxOptions options) {
    GeckoDriverLogger.enable();
    driver = new FirefoxDriver(options);
  }

  @AfterEach
  public void quitDriver() {
    try {
      driver.quit();
    } catch (Exception e) {
      // quit gracefully if there's an issue with the driver
    }
  }

  protected static void startGrid() {
    try {
      Main.main(
              new String[] {
                      "standalone",
                      "--selenium-manager",
                      "true",
                      "--enable-managed-downloads",
                      "true",
                      "--log-level",
                      "FINE"
              });
      Thread.sleep(5000);
      // Default grid URL
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
