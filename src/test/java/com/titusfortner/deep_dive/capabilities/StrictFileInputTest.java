package com.titusfortner.deep_dive.capabilities;

import java.io.File;
import java.io.IOException;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class StrictFileInputTest extends TestBase {
  public void uploadFile() {
    driver.get("https://www.selenium.dev/selenium/web/upload_invisible.html");
    WebElement inputElement = driver.findElement(By.id("upload"));

    Assertions.assertEquals("file", inputElement.getAttribute("type"));

    try {
      File file = File.createTempFile("webdriver", "tmp");
      inputElement.sendKeys(file.getAbsolutePath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void chromeStrictInteractabilityFalse() {
    startChrome();

    Assertions.assertDoesNotThrow(this::uploadFile);
  }

  @Test
  public void chromeStrictInteractabilityTrue() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setStrictFileInteractability(true);
    startChrome(chromeOptions);

    Assertions.assertThrows(ElementNotInteractableException.class, this::uploadFile);
  }

  @Test
  public void firefoxStrictInteractabilityFalse() {
    startFirefox();
    Assertions.assertDoesNotThrow(this::uploadFile);
  }

  @Test
  public void firefoxStrictInteractabilityTrue() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setStrictFileInteractability(true);
    startFirefox(firefoxOptions);
    Assertions.assertThrows(ElementNotInteractableException.class, this::uploadFile);
  }
}
