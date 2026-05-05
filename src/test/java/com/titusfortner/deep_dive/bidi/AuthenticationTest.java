package com.titusfortner.deep_dive.bidi;

import com.titusfortner.deep_dive.TestBase;
import java.net.URI;
import java.time.Duration;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthenticationTest extends TestBase {

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
}
