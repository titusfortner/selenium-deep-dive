package com.titusfortner.deep_dive.bidi;

import com.titusfortner.deep_dive.TestBase;
import java.net.URI;
import java.time.Duration;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;

public class NetworkRequestTest extends TestBase {

  @BeforeEach
  public void setup() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.enableBiDi();
    firefoxOptions.setPageLoadTimeout(Duration.ofSeconds(10));
    startFirefox(firefoxOptions);
  }

  @Test
  public void canAddRequestHandlerToModifyHeaders() {
    Predicate<URI> filter = uri -> uri.toString().contains("headers");

    ((RemoteWebDriver) driver)
        .network()
        .addRequestHandler(
            filter,
            httpRequest -> {
              httpRequest.addHeader("X-Test", "network-intercept");
              return httpRequest;
            });

    driver.get("https://httpbin.org/headers");

    Assertions.assertEquals(
        "X-Test \"network-intercept\"", driver.findElement(By.id("/headers/X-Test")).getText());
  }

  @Test
  public void removeHeader() {
    Predicate<URI> filter = uri -> uri.getHost().equals("httpbin.org");

    ((RemoteWebDriver) driver)
        .network()
        .addRequestHandler(filter, req -> req.removeHeader("upgrade-insecure-requests"));

    driver.get("https://httpbin.org/headers");

    Assertions.assertTrue(
        driver.findElements(By.id("/headers/Upgrade-Insecure-Requests")).isEmpty());
  }

  @Test
  public void modifyHttpMethod() {
    Predicate<URI> filter = uri -> uri.getHost().equals("selenium.dev");

    ((RemoteWebDriver) driver)
        .network()
        .addRequestHandler(filter, req -> new HttpRequest(HttpMethod.HEAD, req.getUri()));

    driver.get("https://selenium.dev");

    Assertions.assertEquals("", driver.getTitle());
  }

  @Test
  public void modifyPostBody() {
    Predicate<URI> filter = uri -> uri.toString().equals("https://httpbin.org/post");
    String contentString = "custname=&custtel=&custemail=fake@example.com&delivery=&comments=";

    ((RemoteWebDriver) driver)
        .network()
        .addRequestHandler(
            filter,
            req ->
                req.setContent(Contents.utf8String(contentString))
                    .setHeader("Content-Length", String.valueOf(contentString.length())));

    driver.get("https://httpbin.org/forms/post");
    driver.findElement(By.name("custemail")).sendKeys("real@example.com");
    driver.findElement(By.tagName("button")).click();

    WebElement custemail = driver.findElement(By.id("/form/custemail"));
    Assertions.assertEquals("custemail \"fake@example.com\"", custemail.getText());
  }

  @Test
  public void redirectUrl() {
    Predicate<URI> filter = uri -> uri.toString().equals("https://selenium.dev/");
    String oldUrl = "https://627c1ee6f585500008d4768d--selenium-dev.netlify.app/";

    ((RemoteWebDriver) driver)
        .network()
        .addRequestHandler(filter, req -> new HttpRequest(req.getMethod(), oldUrl));
    driver.get("https://selenium.dev");

    String message = "© 2022 Software Freedom Conservancy All Rights Reserved";
    Assertions.assertEquals(
        message, driver.findElement(By.cssSelector("small.text-white")).getText());
  }

  @Test
  public void addRemoveClear() {
    Predicate<URI> filter = uri -> uri.getHost().equals("selenium.dev");

    long id =
        ((RemoteWebDriver) driver)
            .network()
            .addRequestHandler(filter, req -> new HttpRequest(HttpMethod.HEAD, req.getUri()));

    // Remove one
    ((RemoteWebDriver) driver).network().removeRequestHandler(id);

    // Remove all
    // ((RemoteWebDriver) driver).network().clearRequestHandlers();

    driver.get("https://selenium.dev");

    Assertions.assertEquals("Selenium", driver.getTitle());
  }
}
