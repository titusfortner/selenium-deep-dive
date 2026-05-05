package com.titusfortner.deep_dive.bidi;

import com.titusfortner.deep_dive.TestBase;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.log.ConsoleLogEntry;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DomMutation;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ScriptTest extends TestBase {
  @BeforeEach
  public void setup() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.enableBiDi();
    startChrome(chromeOptions);
  }

  @Test
  void canAddConsoleMessageHandler() {
    CopyOnWriteArrayList<ConsoleLogEntry> logs = new CopyOnWriteArrayList<>();

    ((RemoteWebDriver) driver).script().addConsoleMessageHandler(logs::add);

    driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
    driver.findElement(By.id("consoleLog")).click();

    new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> !logs.isEmpty());

    Assertions.assertEquals("Hello, world!", logs.get(0).getText());
  }

  @Test
  void canRemoveConsoleMessageHandler() {
    CopyOnWriteArrayList<ConsoleLogEntry> logs = new CopyOnWriteArrayList<>();

    driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

    long id = ((RemoteWebDriver) driver).script().addConsoleMessageHandler(logs::add);
    ((RemoteWebDriver) driver).script().removeConsoleMessageHandler(id);

    driver.findElement(By.id("consoleLog")).click();

    Assertions.assertEquals(0, logs.size());
  }

  @Test
  void canAddJsErrorHandler() {
    CopyOnWriteArrayList<JavascriptLogEntry> logs = new CopyOnWriteArrayList<>();

    ((RemoteWebDriver) driver).script().addJavaScriptErrorHandler(logs::add);

    driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
    driver.findElement(By.id("jsException")).click();

    new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> !logs.isEmpty());

    Assertions.assertEquals("Error: Not working", logs.get(0).getText());
  }

  @Test
  void canRemoveJsErrorHandler() {
    CopyOnWriteArrayList<JavascriptLogEntry> logs = new CopyOnWriteArrayList<>();

    long id = ((RemoteWebDriver) driver).script().addJavaScriptErrorHandler(logs::add);
    ((RemoteWebDriver) driver).script().removeJavaScriptErrorHandler(id);

    driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
    driver.findElement(By.id("jsException")).click();

    Assertions.assertEquals(0, logs.size());
  }

  @Test
  void listeningForDomMutationEvents()
      throws InterruptedException, ExecutionException, TimeoutException {

    // Required to ensure the mutation handler gets loaded in future navigation.
    ((RemoteWebDriver) driver).script().addDomMutationHandler(dm -> {});
    driver.get("https://the-internet.herokuapp.com/dynamic_controls");

    WebElement form = driver.findElement(By.id("input-example"));
    WebElement input = form.findElement(By.tagName("input"));

    CompletableFuture<DomMutation> mutated = new CompletableFuture<>();
    ((RemoteWebDriver) driver)
        .script()
        .addDomMutationHandler(
            dm -> {
              if (input.equals(dm.getElement()) && "disabled".equals(dm.getAttributeName())) {
                mutated.complete(dm);
              }
            });

    WebElement button = form.findElement(By.tagName("button"));
    button.click();

    WebElement target = mutated.get(10, SECONDS).getElement();
    Assertions.assertTrue(target.isEnabled());
  }

  @Test
  void pinnedScriptRunsBeforePageLoad() throws Exception {
    CopyOnWriteArrayList<ConsoleLogEntry> logs = new CopyOnWriteArrayList<>();
    long id = ((RemoteWebDriver) driver).script().addConsoleMessageHandler(logs::add);

    String script = "() => { console.log('pinned script ran'); }";
    String pinnedScript = ((RemoteWebDriver) driver).script().pin(script);

    try {
      driver.get("https://www.selenium.dev/selenium/web/blank.html");

      new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> !logs.isEmpty());
      Assertions.assertEquals("pinned script ran", logs.get(0).getText());

    } finally {
      ((RemoteWebDriver) driver).script().removeConsoleMessageHandler(id);
      ((RemoteWebDriver) driver).script().unpin(pinnedScript);
    }
  }
}
