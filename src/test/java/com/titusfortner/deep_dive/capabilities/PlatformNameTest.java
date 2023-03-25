package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.util.logging.Level;

public class PlatformNameTest extends BaseTest {
    @BeforeEach
    public void setLogging() {
        seleniumLogger.setLevel(Level.FINE);
    }

    @Test
    public void chromePlatformName() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPlatformName("Twitter");

        Assertions.assertThrows(SessionNotCreatedException.class, () -> {
            driver = new ChromeDriver(chromeOptions);
        });
    }

    @Test
    public void edgePlatformName() {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setPlatformName("Twitter");

        Assertions.assertThrows(SessionNotCreatedException.class, () -> {
            driver = new EdgeDriver(edgeOptions);
        });
    }

    @Test
    public void firefoxPlatformName() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setPlatformName("Twitter");

        Assertions.assertThrows(SessionNotCreatedException.class, () -> {
            driver = new FirefoxDriver(firefoxOptions);
        });
    }
}
