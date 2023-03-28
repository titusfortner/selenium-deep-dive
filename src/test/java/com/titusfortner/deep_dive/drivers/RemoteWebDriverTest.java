package test.java.com.titusfortner.deep_dive.drivers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.chromium.HasNetworkConditions;
import org.openqa.selenium.chromium.HasPermissions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxCommandContext;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.HasContext;
import org.openqa.selenium.firefox.HasExtensions;
import org.openqa.selenium.firefox.HasFullPageScreenshot;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RemoteWebDriverTest extends BaseTest {
    URL serverUrl = new URL("http://localhost:4444/");

    public RemoteWebDriverTest() throws MalformedURLException {
    }

    @Test
    public void localHostByDefault() {
        driver = new RemoteWebDriver(new ChromeOptions());
    }

    @Test
    public void useUrl() {
        driver = new RemoteWebDriver(serverUrl, new ChromeOptions());
    }

    @Test
    public void useCommandExecutor() {
        HttpCommandExecutor executor = new HttpCommandExecutor(serverUrl);
        driver = new RemoteWebDriver(executor, new ChromeOptions());
    }

    @Test
    public void useClientConfig() {
        ClientConfig config = ClientConfig.defaultConfig().baseUrl(serverUrl);
        HttpCommandExecutor executor = new HttpCommandExecutor(config);
        driver = new RemoteWebDriver(executor, new ChromeOptions());
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
    @Disabled("Example Only")
    public void buildRemoteWebDriver() {
        Map<String, Object> sauceOptions = new HashMap<>();

        driver = RemoteWebDriver.builder()
                .address("https://ondemand.us-west-1.saucelabs.com/wd/hub")
                .oneOf(new ChromeOptions(), new EdgeOptions())
                .setCapability("sauce:options", sauceOptions)
                .config(ClientConfig.defaultConfig())
                .authenticateAs(new UsernameAndPassword("Username", "Password"))
                // .withDriverService(ChromeDriverService.createDefaultService()) --> not used with address
                .build();
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
        Map<String, Object> result = (Map<String, Object>) ((JavascriptExecutor) driver).executeAsyncScript(
                "callback = arguments[arguments.length - 1];"
                        + "callback(navigator.permissions.query({"
                        + "name: arguments[0]"
                        + "}));", key);
        return result.get("state").toString();
    }

    @Test
    public void takeFullPageScreenshot() throws IOException {
        driver = new RemoteWebDriver(new FirefoxOptions());
        driver = new Augmenter().augment(driver);

        File file = ((HasFullPageScreenshot) driver).getFullPageScreenshotAs(OutputType.FILE);

        Path fullPageScreenshot = Paths.get("src/test/screenshots/TakeFullPageScreenshotFirefox.png");
        Files.move(file.toPath(), fullPageScreenshot);
    }

    @Test
    public void installAndUninstallAddon() {
        driver = new RemoteWebDriver(new FirefoxOptions());
        driver = new Augmenter().augment(driver);

        Path xpiPath = Paths.get("src/test/resources/example.xpi");
        String id = ((HasExtensions) driver).installExtension(xpiPath);

        driver.get("https://www.selenium.dev/selenium/web/blank.html");
        WebElement injected = driver.findElement(By.id("webextensions-selenium-example"));
        Assertions.assertEquals("Content injected by webextensions-selenium-example", injected.getText());

        ((HasExtensions) driver).uninstallExtension(id);

        driver.navigate().refresh();
        Assertions.assertEquals(driver.findElements(By.id("webextensions-selenium-example")).size(), 0);
    }

    @Test
    public void changePrefs() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addPreference("intl.accept_languages", "de-DE");
        driver = new RemoteWebDriver(firefoxOptions);
        driver = new Augmenter().augment(driver);

        driver.get("https://www.google.com");

        String languageID = "gws-output-pages-elements-homepage_additional_languages__als";
        String lang1 = driver.findElement(By.id(languageID)).getText();
        Assertions.assertTrue(lang1.contains("angeboten auf"));

        ((HasContext) driver).setContext(FirefoxCommandContext.CHROME);

        String script = "Services.prefs.setStringPref('intl.accept_languages', 'es-ES')";
        ((JavascriptExecutor) driver).executeScript(script);

        ((HasContext) driver).setContext(FirefoxCommandContext.CONTENT);
        driver.navigate().refresh();

        String lang2 = driver.findElement(By.id(languageID)).getText();
        Assertions.assertTrue(lang2.contains("Ofrecido por"));
    }
}
