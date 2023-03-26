package test.java.com.titusfortner.deep_dive.drivers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

public class DriverServiceTest extends BaseTest {
    @BeforeEach
    public void setLogging() {
        seleniumLogger.setLevel(Level.FINE);
    }

    @Test
    public void chrome() throws IOException {
        File logFile = File.createTempFile("chromedriver", "log");

        ChromeDriverService service = new ChromeDriverService.Builder()
                .withVerbose(true)
                .withLogFile(logFile)
                .withReadableTimestamp(true)
                .build();
        driver = new ChromeDriver(service);

        String output = Files.readAllLines(Paths.get(logFile.toString())).toString();

        String expectedText = "[DEBUG]";
        Assertions.assertTrue(output.contains(expectedText));
    }

    @Test
    public void firefox() throws IOException {
        System.clearProperty("webdriver.firefox.logfile");
        File logFile = File.createTempFile("geckodriver", "log");

        FirefoxDriverService service = new GeckoDriverService.Builder()
                .withLogFile(logFile)
                .build();
        driver = new FirefoxDriver(service);

        String output = Files.readAllLines(Paths.get(logFile.toString())).toString();
        String expectedText = "Marionette\tINFO";
        Assertions.assertTrue(output.contains(expectedText));
    }
}
