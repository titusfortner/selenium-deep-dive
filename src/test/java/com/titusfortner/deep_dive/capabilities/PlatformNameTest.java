package com.titusfortner.deep_dive.capabilities;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class PlatformNameTest extends TestBase {
  @Test
  public void chromePlatformName() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setPlatformName("Twitter");

    Assertions.assertThrows(
        SessionNotCreatedException.class,
        () -> {
          startChrome(chromeOptions);
        });
  }

  @Test
  public void edgePlatformName() {
    EdgeOptions edgeOptions = new EdgeOptions();
    edgeOptions.setPlatformName("Twitter");

    Assertions.assertThrows(
        SessionNotCreatedException.class,
        () -> {
          startEdge(edgeOptions);
        });
  }

  @Test
  public void firefoxPlatformName() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setPlatformName("Twitter");

    Assertions.assertThrows(
        SessionNotCreatedException.class,
        () -> {
          startFirefox(firefoxOptions);
        });
  }
}
