package com.titusfortner.deep_dive.driver;

import com.titusfortner.deep_dive.TestBase;
import com.titusfortner.logging.SeleniumLogger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.chromium.HasNetworkConditions;
import org.openqa.selenium.chromium.HasPermissions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxCommandContext;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.HasContext;
import org.openqa.selenium.firefox.HasFullPageScreenshot;
import org.openqa.selenium.firefox.HasExtensions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AugmenterTest extends TestBase {
  @BeforeAll
  public static void setup() {
    SeleniumLogger.enable();
    startGrid();
  }

  @Test
  public void augmentNetworkConditions() {
    driver = new RemoteWebDriver(new ChromeOptions());
    driver = new Augmenter().augment(driver);

    ChromiumNetworkConditions networkConditions = new ChromiumNetworkConditions();
    networkConditions.setOffline(true);
    ((HasNetworkConditions) driver).setNetworkConditions(networkConditions);

    try {
      driver.get("https://www.selenium.dev");
      Assertions.fail("If Network is set to be offline, any navigation should throw an exception");
    } catch (WebDriverException ex) {
      ((HasNetworkConditions) driver).setNetworkConditions(new ChromiumNetworkConditions());
    }

    Assertions.assertDoesNotThrow(() -> driver.get("https://www.selenium.dev"));
  }

  @Test
  public void canSetPermission() {
    driver = new RemoteWebDriver(new ChromeOptions());
    driver = new Augmenter().augment(driver);

    driver.get("https://www.selenium.dev/selenium/web/clicks.html");

    Assertions.assertEquals("prompt", getPermission("clipboard-read"));
    Assertions.assertEquals("granted", getPermission("clipboard-write"));

    ((HasPermissions) driver).setPermission("clipboard-read", "denied");
    ((HasPermissions) driver).setPermission("clipboard-write", "denied");

    Assertions.assertEquals("denied", getPermission("clipboard-read"));
    Assertions.assertEquals("denied", getPermission("clipboard-write"));
  }

  public String getPermission(String key) {
    Map<String, Object> result =
        (Map<String, Object>)
            ((JavascriptExecutor) driver)
                .executeAsyncScript(
                    "callback = arguments[arguments.length - 1];"
                        + "callback(navigator.permissions.query({"
                        + "name: arguments[0]"
                        + "}));",
                    key);
    return result.get("state").toString();
  }

  @Test
  @Disabled
  public void takeFullPageScreenshot() throws IOException {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    driver = new RemoteWebDriver(firefoxOptions);
    WebDriver augmentedDriver = new Augmenter().augment(driver);

    File file = ((HasFullPageScreenshot) augmentedDriver).getFullPageScreenshotAs(OutputType.FILE);

    Path fullPageScreenshot = Paths.get("src/test/screenshots/TakeFullPageScreenshotFirefox.png");
    Files.move(file.toPath(), fullPageScreenshot);
  }

  @Test
  @Disabled
  public void installAndUninstallAddon() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    driver = new RemoteWebDriver(firefoxOptions);
    WebDriver augmentedDriver = new Augmenter().augment(driver);

    Path xpiPath = Paths.get("src/test/resources/example.xpi");
    String id = ((HasExtensions) augmentedDriver).installExtension(xpiPath);

    driver.get("https://www.selenium.dev/selenium/web/blank.html");
    WebElement injected = driver.findElement(By.id("webextensions-selenium-example"));
    Assertions.assertEquals(
        "Content injected by webextensions-selenium-example", injected.getText());

    ((HasExtensions) augmentedDriver).uninstallExtension(id);

    driver.navigate().refresh();
    Assertions.assertEquals(driver.findElements(By.id("webextensions-selenium-example")).size(), 0);
  }

  @Test
  @Disabled
  public void changePrefs() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.addPreference("intl.accept_languages", "de-DE");
    driver = new RemoteWebDriver(firefoxOptions);
    WebDriver augmentedDriver = new Augmenter().augment(driver);

    driver.get("https://www.google.com");

    String languageID = "gws-output-pages-elements-homepage_additional_languages__als";
    String lang1 = driver.findElement(By.id(languageID)).getText();
    Assertions.assertTrue(lang1.contains("angeboten auf"));

    ((HasContext) augmentedDriver).setContext(FirefoxCommandContext.CHROME);

    String script = "Services.prefs.setStringPref('intl.accept_languages', 'es-ES')";
    ((JavascriptExecutor) driver).executeScript(script);

    ((HasContext) augmentedDriver).setContext(FirefoxCommandContext.CONTENT);
    driver.navigate().refresh();

    String lang2 = driver.findElement(By.id(languageID)).getText();
    Assertions.assertTrue(lang2.contains("Ofrecido por"));
  }
}
