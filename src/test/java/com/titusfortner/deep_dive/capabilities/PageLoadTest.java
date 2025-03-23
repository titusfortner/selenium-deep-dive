package com.titusfortner.deep_dive.capabilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeOptions;

public class PageLoadTest extends TestBase {
  /** Waits for Document readiness state to be "complete" This is the default setting. */
  @Test
  public void pageLoadNormal() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

    startChrome(chromeOptions);
    long timeElapsed = navigationTime("normal");

    Assertions.assertTrue(timeElapsed > 2000, "time elapsed: " + timeElapsed);
  }

  /**
   * Waits for Document readiness state to be "interactive" Useful if there is an extraneous asset
   * that isn't loading
   */
  @Test
  public void pageLoadEager() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
    startChrome(chromeOptions);

    long timeElapsed = navigationTime("eager");

    Assertions.assertTrue(timeElapsed < 1000, "time elapsed: " + timeElapsed);
    Assertions.assertTrue(timeElapsed > 300, "time elapsed: " + timeElapsed);
  }

  /** No waiting To educate people who don't think they have any race conditions in their code */
  @Test
  public void pageLoadNone() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
    startChrome(chromeOptions);

    long timeElapsed = navigationTime("none");

    Assertions.assertTrue(timeElapsed < 50);
  }

  private long navigationTime(String method) {
    Instant start = Instant.now();
    driver.get("https://nytimes.com");
    Instant finish = Instant.now();

    long timeElapsed = Duration.between(start, finish).toMillis();
    System.out.println(timeElapsed + " milliseconds");

    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    try {
      Files.createDirectories(Paths.get("src/test/screenshots"));
      Files.copy(
          screenshot.toPath(),
          Paths.get("screenshots/navigation_" + method + ".png"),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      System.err.println("Screenshot failed: " + e.getMessage());
    }

    return timeElapsed;
  }
}
