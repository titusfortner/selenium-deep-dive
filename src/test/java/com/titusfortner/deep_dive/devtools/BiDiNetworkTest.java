package com.titusfortner.deep_dive.devtools;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Script;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Predicate;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BiDiNetworkTest extends TestBase {
  @BeforeEach
  public void setup() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.enableBiDi();
    firefoxOptions.setPageLoadTimeout(Duration.ofSeconds(10));
    startFirefox(firefoxOptions);
  }

  @Test
  public void addAuthenticationHandler() {
    Predicate<URI> filter = uri -> uri.getPath().contains("basic_auth");

    long id =
        ((RemoteWebDriver) driver)
            .network()
            .addAuthenticationHandler(filter, new UsernameAndPassword("admin", "admin"));

    driver.get("https://the-internet.herokuapp.com/basic_auth");

    String text = driver.findElement(By.tagName("p")).getText();
    Assertions.assertEquals("Congratulations! You must have the proper credentials.", text);
  }

  @Test
  public void removeAuthenticationHandler() {
    Predicate<URI> filter = uri -> uri.getPath().contains("basic_auth");

    long id =
        ((RemoteWebDriver) driver)
            .network()
            .addAuthenticationHandler(filter, new UsernameAndPassword("admin", "admin"));

    ((RemoteWebDriver) driver).network().removeAuthenticationHandler(id);
    driver.get("https://the-internet.herokuapp.com/basic_auth");

    Assertions.assertThrows(
        UnhandledAlertException.class, () -> driver.findElement(By.tagName("p")));
  }

  @Test
  public void clearAuthenticationHandler() {
    Predicate<URI> filter = uri -> uri.getPath().contains("basic_auth");

    ((RemoteWebDriver) driver)
        .network()
        .addAuthenticationHandler(filter, new UsernameAndPassword("admin", "admin"));

    ((RemoteWebDriver) driver).network().clearAuthenticationHandlers();
    driver.get("https://the-internet.herokuapp.com/basic_auth");

    Assertions.assertThrows(
        UnhandledAlertException.class, () -> driver.findElement(By.tagName("p")));
  }

  @Test
  void listeningForDomMutationEvents() throws InterruptedException {
    Script script = ((RemoteWebDriver) driver).script();

    // Ensure the mutation handler gets loaded in future navigation.
    script.addDomMutationHandler(dm -> {});
    driver.get("https://the-internet.herokuapp.com/dynamic_controls");

    WebElement form = driver.findElement(By.id("input-example"));
    WebElement input = form.findElement(By.tagName("input"));

    CountDownLatch latch = new CountDownLatch(1);
    script.addDomMutationHandler(
        dm -> {
          if (input.equals(dm.getElement()) && "disabled".equals(dm.getAttributeName())) {
            System.err.println("The element's disabled attribute has changed");
            latch.countDown();
          }
        });

    WebElement button = form.findElement(By.tagName("button"));
    button.click();
    latch.await(10, SECONDS);

    Assertions.assertTrue(input.isEnabled());
  }
}
