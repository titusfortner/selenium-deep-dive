package com.titusfortner.deep_dive.capabilities;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserVersionTest extends TestBase {
  @Test
  public void chromeVersion() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setBrowserVersion("116");

    Assertions.assertDoesNotThrow(
        () -> {
          startChrome(chromeOptions);
        });
  }

  @Test
  public void firefoxVersion() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setBrowserVersion("130");

    Assertions.assertDoesNotThrow(
        () -> {
          startFirefox(firefoxOptions);
        });
  }
}
