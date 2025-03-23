package com.titusfortner.deep_dive.driver;

import com.titusfortner.deep_dive.TestBase;
import java.io.IOException;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.http.ClientConfig;

/**
 * Set values of HTTP Client with ClientConfig class Connect/Read timeouts, credentials, proxy, etc
 */
public class HTTPClientTest extends TestBase {
  @Test
  public void chrome() throws IOException {
    ChromeOptions chromeOptions = new ChromeOptions();
    var config = ClientConfig.defaultConfig().readTimeout(Duration.ofMillis(800));

    driver = new ChromeDriver(ChromeDriverService.createDefaultService(), chromeOptions, config);

    Exception thrown =
        Assertions.assertThrows(
            TimeoutException.class,
            () -> {
              driver.get("https://www.nytimes.com");
            });
    Assertions.assertTrue(thrown.getMessage().contains("request timed out"));
  }
}
