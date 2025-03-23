package com.titusfortner.deep_dive.capabilities;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SetWindowRectTest extends TestBase {

  @Test
  public void chromeDoesNotRecognizeSetWindowRect() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setCapability("setWindowRect", false);

    Exception thrown =
        Assertions.assertThrows(SessionNotCreatedException.class, () -> startChrome(chromeOptions));

    Assertions.assertTrue(thrown.getMessage().contains("unrecognized capability: setWindowRect"));
  }

  @Test
  public void firefoxDoesNotAllowDisableSetWindowRect() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setCapability("setWindowRect", false);

    Exception thrown =
        Assertions.assertThrows(
            SessionNotCreatedException.class, () -> startFirefox(firefoxOptions));

    Assertions.assertTrue(thrown.getMessage().contains("setWindowRect cannot be disabled"));
  }
}
