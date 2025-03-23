package com.titusfortner.deep_dive.capabilities;

import java.time.Duration;
import java.time.Instant;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeOptions;

public class TimeoutTest extends TestBase {

  @Test
  public void scriptTimeout() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setScriptTimeout(Duration.ofSeconds(2));
    startChrome(chromeOptions);

    Instant start = Instant.now();

    Assertions.assertThrows(
        ScriptTimeoutException.class, () -> ((JavascriptExecutor) driver).executeAsyncScript(""));

    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMillis();

    Assertions.assertTrue(timeElapsed > 2000);
    Assertions.assertTrue(timeElapsed < 2200);
  }

  @Test
  public void implicitWaitTimeout() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(2));
    startChrome(chromeOptions);

    Instant start = Instant.now();

    Assertions.assertThrows(
        NoSuchElementException.class, () -> driver.findElement(By.id("not-there")));

    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMillis();

    Assertions.assertTrue(timeElapsed > 2000, "time was: " + timeElapsed);
    Assertions.assertTrue(timeElapsed < 2200, "time was: " + timeElapsed);
  }

  @Test
  public void pageLoadTimeout() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setPageLoadTimeout(Duration.ofSeconds(5));
    startChrome(chromeOptions);

    Instant start = Instant.now();

    Assertions.assertThrows(
        TimeoutException.class,
        () -> driver.get("https://titusfortner.com/examples/never_loads.html"));

    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMillis();

    System.out.println(timeElapsed);
    Assertions.assertTrue(timeElapsed > 5000);
    Assertions.assertTrue(timeElapsed < 5200);
  }
}
