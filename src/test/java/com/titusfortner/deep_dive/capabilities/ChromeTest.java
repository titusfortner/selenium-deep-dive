package com.titusfortner.deep_dive.capabilities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.NoSuchDriverException;

public class ChromeTest extends TestBase {
  private final ChromeOptions chromeOptions = new ChromeOptions();

  /**
   * Extensive list of common arguments:
   * https://github.com/GoogleChrome/chrome-launcher/blob/main/docs/chrome-flags-for-tools.md This
   * example shows changing window size and position
   */
  @Test
  public void addArgs() {
    chromeOptions.addArguments("--window-position=100,100", "--window-size=800,600");
    startChrome(chromeOptions);

    Assertions.assertEquals(new Point(100, 100), driver.manage().window().getPosition());
    Assertions.assertEquals(new Dimension(800, 600), driver.manage().window().getSize());
  }

  /** Set the Location of the browser you want to use */
  @Test
  public void setBinary() {
    chromeOptions.setBinary("path/to/chromium");

    Exception thrown =
        Assertions.assertThrows(NoSuchDriverException.class, () -> startChrome(chromeOptions));

    Assertions.assertTrue(thrown.getMessage().contains("Browser path does not exist"));
  }

  /** Add an extension that injects an element on every page */
  @Test
  public void addExtension() {
    chromeOptions.addExtensions(new File("src/test/resources/example.crx"));
    startChrome(chromeOptions);

    driver.get("https://www.selenium.dev/selenium/web/blank.html");

    WebElement injected = driver.findElement(By.id("webextensions-selenium-example"));
    Assertions.assertEquals(
        "Content injected by webextensions-selenium-example", injected.getText());
  }

  /** I couldn't find anything actually useful here, but maybe something exists */
  @Test
  public void changeLocalState() {
    Map<String, Boolean> biometrics = ImmutableMap.of("had_biometrics_available", true);
    Map<String, Object> passwordManager = ImmutableMap.of("password_manager", biometrics);
    chromeOptions.setExperimentalOption("localState", passwordManager);
    startChrome(chromeOptions);

    driver.get("chrome://local-state/");
    String script =
        "return JSON.parse(document.getElementById('content').textContent).password_manager";
    Map<String, Object> managerOutput =
        (Map<String, Object>) ((JavascriptExecutor) driver).executeScript(script);
    Map<String, Object> biometricsOutput =
        (Map<String, Object>) managerOutput.get("had_biometrics_available");
    Assertions.assertTrue((Boolean) biometricsOutput.get("value"));
  }

  /**
   * https://source.chromium.org/chromium/chromium/src/+/main:chrome/common/pref_names.cc The most
   * common is for managing download directory
   */
  @Test
  public void changePrefs() throws IOException {
    String downloadDirectory = Files.createTempDirectory("webdriver").toString();

    Map<String, Object> prefs =
        ImmutableMap.of(
            "download.prompt_for_download", false, "download.default_directory", downloadDirectory);
    chromeOptions.setExperimentalOption("prefs", prefs);

    startPatientChrome(chromeOptions);
    driver.get("https://filesamples.com/formats/csv/");
    driver.findElement(By.id("ez-accept-all")).click();
    driver.findElements(By.cssSelector("[download]")).get(1).click();

    File downloadedFile = new File(downloadDirectory + "/" + "sample3.csv");

    waitForDownload();
    Assertions.assertTrue(downloadedFile.exists());
  }

  /**
   * Tells the driver not to close the browser when the driver exits In Java the driver only exits
   * if `quit()` is called, so this parameter is not useful
   * https://github.com/SeleniumHQ/selenium/issues/11303
   */
  @Test
  @Disabled
  public void detach() {
    chromeOptions.setExperimentalOption("detach", true);
    startChrome(chromeOptions);
  }

  /**
   * Two different drivers with different capabilities acting on the same browser You have to know
   * the debugging port of the browser to be able to do this
   */
  @Test
  public void debuggerAddress() {
    WebDriver driverTwo = null;

    try {
      chromeOptions.addArguments("--remote-debugging-port=9898");
      chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
      startChrome(chromeOptions);

      ChromeOptions chromeOptionsTwo = new ChromeOptions();
      chromeOptionsTwo.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
      chromeOptionsTwo.setExperimentalOption("debuggerAddress", "localhost:9898");
      driverTwo = new ChromeDriver(chromeOptionsTwo);

      Assertions.assertNotEquals(driver, driverTwo);

      String url = "https://the-internet.herokuapp.com/javascript_alerts";
      driver.get(url);
      Assertions.assertEquals(url, driver.getCurrentUrl());
      Assertions.assertEquals(url, driverTwo.getCurrentUrl());

      driver.findElement(By.cssSelector("button[onclick='jsConfirm()']")).click();

      Assertions.assertThrows(UnhandledAlertException.class, driver::getTitle);
      Assertions.assertThrows(UnhandledAlertException.class, driver::getTitle);
      Assertions.assertDoesNotThrow(driverTwo::getTitle);
    } finally {
      if (driverTwo != null) {
        driverTwo.quit();
      }
    }
  }

  /**
   * List of already set:
   * https://source.chromium.org/chromium/chromium/src/+/main:chrome/test/chromedriver/chrome_launcher.cc
   * Disabling popups is sometimes nice
   */
  @Test
  public void excludeSwitches() {
    chromeOptions.setExperimentalOption(
        "excludeSwitches", ImmutableList.of("disable-popup-blocking"));
    startChrome(chromeOptions);

    driver.get("https://deliver.courseavenue.com/PopupTest.aspx");
    driver.findElement(By.id("ctl00_ContentMain_popupTest")).click();

    Assertions.assertEquals(1, driver.getWindowHandles().size());
  }

  /** Set by device name or attributes https://chromedriver.chromium.org/mobile-emulation */
  @Test
  public void mobileEmulation() {
    ImmutableMap<String, String> deviceName = ImmutableMap.of("deviceName", "Pixel 4");
    chromeOptions.setExperimentalOption("mobileEmulation", deviceName);

    startChrome(chromeOptions);
    driver.get("https://www.whatismybrowser.com/detect/what-is-my-user-agent");
    String text = driver.findElement(By.id("detected_value")).getText();

    Assertions.assertTrue(text.contains("Pixel 4"));
  }

  /**
   * https://chromedriver.chromium.org/logging/performance-log Performance Logging provides:
   * "Timeline", "Network", and "Page" domain events
   */
  @Test
  public void performanceLogging() throws IOException {
    File file = File.createTempFile("chromedriver", "log");
    System.setProperty("webdriver.chrome.logfile", file.toString());

    // Most of the good stuff is in verbose logging
    System.setProperty("webdriver.chrome.verboseLogging", "true");

    // Enable performance in Logging Preferences
    LoggingPreferences logPrefs = new LoggingPreferences();
    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
    chromeOptions.setCapability("goog:loggingPrefs", logPrefs);

    // Adjust specific performance logging preferences
    chromeOptions.setExperimentalOption(
        "perfLoggingPrefs", ImmutableMap.of("enableNetwork", false));

    startChrome(chromeOptions);
    driver.get("https://www.titusfortner.com");

    String output = Files.readAllLines(Paths.get(file.toString())).toString();

    String expectedText = "Page.enable";
    Assertions.assertTrue(output.contains(expectedText));

    // We turned this off
    String unexpectedText = "Network.enable";
    Assertions.assertFalse(output.contains(unexpectedText));
  }

  private void waitForDownload() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
