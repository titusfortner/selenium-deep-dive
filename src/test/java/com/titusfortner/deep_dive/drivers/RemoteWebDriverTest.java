package test.java.com.titusfortner.deep_dive.drivers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.chromium.HasNetworkConditions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.util.HashMap;
import java.util.Map;

public class RemoteWebDriverTest extends BaseTest {

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
}
