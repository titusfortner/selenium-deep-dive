package com.titusfortner.deep_dive.solutions.support;

import com.titusfortner.deep_dive.TestBase;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

public class SlowFinderListenerSolution extends TestBase {

  public static class SlowFinderListener implements WebDriverListener {
    private final List<String> slowFinds;
    private Instant start;

    public SlowFinderListener(List<String> slowFinds) {
      this.slowFinds = slowFinds;
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
      start = Instant.now();
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
      long ms = Duration.between(start, Instant.now()).toMillis();
      if (ms > 500) {
        slowFinds.add(result + " took " + ms + "ms");
      }
    }
  }

  @BeforeEach
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.setImplicitWaitTimeout(Duration.ofSeconds(8));
    startChrome(options);
  }

  @Test
  void detectSlowFindElement() {
    List<String> slowFinds = new CopyOnWriteArrayList<>();

    WebDriverListener slowFinder = new SlowFinderListener(slowFinds);

    driver = new EventFiringDecorator<>(slowFinder).decorate(driver);

    driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
    driver.findElement(By.cssSelector("#start button")).click();
    driver.findElement(By.id("finish"));

    Assertions.assertFalse(slowFinds.isEmpty());
  }
}
