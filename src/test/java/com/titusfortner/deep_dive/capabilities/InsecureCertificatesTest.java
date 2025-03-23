package com.titusfortner.deep_dive.capabilities;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class InsecureCertificatesTest extends TestBase {
  /** Default Chrome is Not to Accept Insecure Certs */
  @Test
  public void chromeNotAcceptInsecureCerts() {
    startChrome();

    driver.get("https://expired.badssl.com");

    // This should error, but does not
    // (https://bugs.chromium.org/p/chromedriver/issues/detail?id=4407)
    Assertions.assertTrue(
        driver
            .findElement(By.id("main-message"))
            .getText()
            .contains("Your connection is not private"));
  }

  @Test
  public void chromeAcceptInsecureCerts() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setAcceptInsecureCerts(true);
    startChrome(chromeOptions);

    driver.get("https://expired.badssl.com");

    Assertions.assertTrue(driver.findElement(By.id("content")).getText().contains("expired"));
  }

  /** Default Firefox is to accept Insecure certificates */
  @Test
  public void firefoxAcceptInsecureCerts() {
    startFirefox();

    driver.get("https://expired.badssl.com");

    Assertions.assertTrue(driver.findElement(By.id("content")).getText().contains("expired"));
  }

  @Test
  public void firefoxNotAcceptInsecureCerts() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setAcceptInsecureCerts(false);

    startFirefox(firefoxOptions);

    // This should not be a generic exception (https://github.com/SeleniumHQ/selenium/issues/11817)
    Assertions.assertThrows(
        WebDriverException.class, () -> driver.get("https://expired.badssl.com"));
  }
}
