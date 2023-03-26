package test.java.com.titusfortner.deep_dive.drivers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.http.ClientConfig;
import test.java.com.titusfortner.deep_dive.BaseTest;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.logging.Level;

/**
 * Set values of HTTP Client with ClientConfig class
 * Connect/Read timeouts, credentials, proxy, etc
 */
public class HTTPClientTest extends BaseTestChrome {
    @BeforeEach
    public void setLogging() {
        seleniumLogger.setLevel(Level.FINE);
    }

    /**
     * New in Selenium 4.8.2
     */
    @Test
    public void chrome() throws IOException {
        ClientConfig config = ClientConfig.defaultConfig().readTimeout(Duration.ofSeconds(1));

        Assertions.assertThrows(SessionNotCreatedException.class, () -> {
            driver = new ChromeDriver(ChromeDriverService.createDefaultService(), chromeOptions, config);
        });
    }
}
