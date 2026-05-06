package com.titusfortner.deep_dive.exercises.support;

import com.titusfortner.deep_dive.TestBase;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

/*
 * EXERCISE — A listener that flags slow findElement calls.
 *
 * Real debugging tool: when a suite is slow, you usually don't know where
 * the time is going. A listener that flags any findElement taking longer
 * than a threshold is a 30-line tool you'll reuse on every project.
 *
 * GOAL
 *   1. Implement a WebDriverListener that times beforeFindElement →
 *      afterFindElement, and adds a record to `slowFinds` when the call
 *      takes longer than 500 ms.
 *   2. Wrap `driver` with EventFiringDecorator using your listener.
 *   3. Use the wrapped driver to drive the test. The find for #finish
 *      will block ~5 s waiting for the element to appear (implicit wait).
 *   4. Assert the listener captured at least one slow find.
 *
 * HINT
 *   WebDriverListener has typed hooks like beforeFindElement, but the
 *   generic catch-all is more reliable across Selenium versions:
 *     public void beforeAnyCall(Object target, Method method, Object[] args) { ... }
 *     public void afterAnyCall(Object target, Method method, Object[] args, Object result) { ... }
 *   Filter on method.getName().equals("findElement").
 *   Wrap with: new EventFiringDecorator<>(listener).decorate(driver)
 *
 * SUCCESS
 *   `slowFinds` is non-empty after the slow find of #finish.
 */
public class SlowFinderExercise extends TestBase {

  @BeforeEach
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.setImplicitWaitTimeout(Duration.ofSeconds(8));
    startChrome(options);
  }

  @Test
  void detectSlowFindElement() {
    List<String> slowFinds = new CopyOnWriteArrayList<>();

    // TODO wrap driver with a WebDriverListener that records into `slowFinds` when a
    //   findElement call takes more than 500 ms.
    // Extra Credit: Implement with a WebDriverDecorator

    driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
    driver.findElement(By.cssSelector("#start button")).click();
    driver.findElement(By.id("finish"));

    Assertions.assertFalse(slowFinds.isEmpty());
  }
}
