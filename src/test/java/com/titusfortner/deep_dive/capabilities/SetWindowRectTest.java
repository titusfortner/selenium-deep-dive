package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.util.logging.Level;

public class SetWindowRectTest extends BaseTest {
    @BeforeEach
    public void setLogging() {
        seleniumLogger.setLevel(Level.FINE);
    }

    @Test
    public void chromeDoesNotRecognizeSetWindowRect() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("setWindowRect", false);

        Exception thrown = Assertions.assertThrows(SessionNotCreatedException.class, () ->
                driver = new ChromeDriver(chromeOptions)
        );

        Assertions.assertTrue(thrown.getMessage().contains("unrecognized capability: setWindowRect"));
    }

    @Test
    public void firefoxDoesNotAllowDisableSetWindowRect() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setCapability("setWindowRect", false);

        Exception thrown = Assertions.assertThrows(SessionNotCreatedException.class, () ->
                driver = new FirefoxDriver(firefoxOptions)
        );

        Assertions.assertTrue(thrown.getMessage().contains("InvalidArgumentError: setWindowRect cannot be disabled"));
    }
}
