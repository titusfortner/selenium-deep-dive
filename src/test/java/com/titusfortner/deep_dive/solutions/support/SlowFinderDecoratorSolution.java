package com.titusfortner.deep_dive.solutions.support;

import com.titusfortner.deep_dive.TestBase;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.decorators.Decorated;
import org.openqa.selenium.support.decorators.DefaultDecorated;
import org.openqa.selenium.support.decorators.WebDriverDecorator;

public class SlowFinderDecoratorSolution extends TestBase {

  public static class SlowFinderDecorator extends WebDriverDecorator<WebDriver> {
    private final List<String> slowFinds;

    public SlowFinderDecorator(List<String> slowFinds) {
      this.slowFinds = slowFinds;
    }

    @Override
    public Decorated<WebDriver> createDecorated(WebDriver original) {
      return new DefaultDecorated<>(original, this) {
        @Override
        public Object call(Method method, Object[] args) throws Throwable {
          if (!"findElement".equals(method.getName()) || args.length != 1 || !(args[0] instanceof By)) {
            return super.call(method, args);
          }

          Instant start = Instant.now();
          try {
            return super.call(method, args);
          } finally {
            long ms = Duration.between(start, Instant.now()).toMillis();
            if (ms > 500) {
              slowFinds.add(args[0] + " took " + ms + "ms");
            }
          }
        }
      };
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
    driver = new SlowFinderDecorator(slowFinds).decorate(driver);

    driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
    driver.findElement(By.cssSelector("#start button")).click();
    driver.findElement(By.id("finish"));

    Assertions.assertFalse(slowFinds.isEmpty());
  }
}
