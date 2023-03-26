package test.java.com.titusfortner.deep_dive.capabilities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;

public class ChromeTest extends BaseTestChrome {
    /**
     * Extensive list of common arguments: https://github.com/GoogleChrome/chrome-launcher/blob/main/docs/chrome-flags-for-tools.md
     * This example shows changing window size and position
     */
    @Test
    public void addArgs() {
        chromeOptions.addArguments("--window-position=100,100", "--window-size=800,600");
        driver = new ChromeDriver(chromeOptions);

        Assertions.assertEquals(new Point(100, 100), driver.manage().window().getPosition());
        Assertions.assertEquals(new Dimension(800, 600), driver.manage().window().getSize());
    }

    /**
     * Set the Location of the browser you want to use
     */
    @Test
    public void setBinary() throws IOException {
        File tempFile = Files.createTempDirectory("webdriver").toFile();
        chromeOptions.setBinary(tempFile);

        Exception thrown = Assertions.assertThrows(SessionNotCreatedException.class, () ->
                new ChromeDriver(chromeOptions)
        );

        Assertions.assertTrue(thrown.getMessage().contains("Failed to create Chrome process"));
    }

    /**
     * Add an extension that injects an element on every page
     */
    @Test
    public void addExtension() {
        chromeOptions.addExtensions(new File("src/test/resources/example.crx"));
        driver = new ChromeDriver(chromeOptions);

        driver.get("https://www.selenium.dev/selenium/web/blank.html");

        WebElement injected = driver.findElement(By.id("webextensions-selenium-example"));
        Assertions.assertEquals("Content injected by webextensions-selenium-example", injected.getText());
    }

    /**
     * I couldn't find anything actually useful here, but maybe something exists
     */
    @Test
    public void changeLocalState() {
        Map<String, Boolean> biometrics = ImmutableMap.of("had_biometrics_available", false);
        Map<String, Object> passwordManager = ImmutableMap.of("password_manager", biometrics);
        chromeOptions.setExperimentalOption("localState", passwordManager);
        driver = new ChromeDriver(chromeOptions);

        driver.get("chrome://local-state/");

        String content = driver.findElement(By.id("content")).getText();
        Assertions.assertTrue(content.contains("\"had_biometrics_available\": false"));
    }

    /**
     * https://source.chromium.org/chromium/chromium/src/+/main:chrome/common/pref_names.cc
     * The most common is for managing download directory
     */
    @Test
    public void changePrefs() throws IOException {
        String downloadDirectory = Files.createTempDirectory("webdriver").toString();

        Map<String, Object> prefs = ImmutableMap.of(
                "download.prompt_for_download", false,
                "download.default_directory", downloadDirectory
        );
        chromeOptions.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(chromeOptions);
        driver.get("https://filesamples.com/formats/csv/");
        driver.findElements(By.cssSelector("[download]")).get(1).click();

        File downloadedFile = new File(downloadDirectory + "/" + "sample3.csv");

        waitForDownload();
        Assertions.assertTrue(downloadedFile.exists());
    }

    /**
     * Tells the driver not to close the browser when the driver exits
     * In Java the driver only exits if `quit()` is called, so this parameter is not useful
     * https://github.com/SeleniumHQ/selenium/issues/11303
     */
    @Test
    @Disabled
    public void detach()  {
        chromeOptions.setExperimentalOption("detach", true);
        new ChromeDriver(chromeOptions);
    }

    /**
     * Two different drivers with different capabilities acting on the same browser
     * You have to know the debugging port of the browser to be able to do this
     */
    @Test
    public void debuggerAddress() {
        WebDriver driverOne = null;
        WebDriver driverTwo = null;

        try {
            ChromeOptions chromeOptionsOne = new ChromeOptions();
            chromeOptionsOne.addArguments("--remote-debugging-port=9898");
            chromeOptionsOne.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
            driverOne = new ChromeDriver(chromeOptionsOne);

            ChromeOptions chromeOptionsTwo = new ChromeOptions();
            chromeOptionsTwo.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
            chromeOptionsTwo.setExperimentalOption("debuggerAddress", "localhost:9898");
            driverTwo = new ChromeDriver(chromeOptionsTwo);

            Assertions.assertNotEquals(driverOne, driverTwo);

            String url = "https://the-internet.herokuapp.com/javascript_alerts";
            driverOne.get(url);
            Assertions.assertEquals(url, driverOne.getCurrentUrl());
            Assertions.assertEquals(url, driverTwo.getCurrentUrl());

            driverOne.findElement(By.cssSelector("button[onclick='jsConfirm()']")).click();

            Assertions.assertThrows(UnhandledAlertException.class, driverOne::getTitle);
            Assertions.assertThrows(UnhandledAlertException.class, driverOne::getTitle);
            Assertions.assertDoesNotThrow(driverTwo::getTitle);
        } finally {
            if (driverOne != null) {
                driverOne.quit();
            }
            if (driverTwo != null) {
                driverTwo.quit();
            }
        }
    }

    /**
     * List of already set: https://source.chromium.org/chromium/chromium/src/+/main:chrome/test/chromedriver/chrome_launcher.cc
     * Disabling popups is sometimes nice
     */
    @Test
    public void excludeSwitches() {
        chromeOptions.setExperimentalOption("excludeSwitches", ImmutableList.of("disable-popup-blocking"));
        driver = new ChromeDriver(chromeOptions);

        driver.get("https://deliver.courseavenue.com/PopupTest.aspx");
        driver.findElement(By.id("ctl00_ContentMain_popupTest")).click();

        Assertions.assertEquals(1, driver.getWindowHandles().size());
    }

    /**
     * Set by device name or attributes
     * https://chromedriver.chromium.org/mobile-emulation
     */
    @Test
    public void mobileEmulation() {
        chromeOptions.setExperimentalOption("mobileEmulation", ImmutableMap.of("deviceName", "Pixel 4"));

        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.whatismybrowser.com/detect/what-is-my-user-agent");
        String text = driver.findElement(By.id("detected_value")).getText();

        Assertions.assertTrue(text.contains("Pixel 4"));
    }

    /**
     * https://chromedriver.chromium.org/logging/performance-log
     * Performance Logging provides:  "Timeline", "Network", and "Page" domain events
     *
     */
    @Test
    public void performanceLogging() throws IOException {
        // Most of the good stuff is in verbose logging
        System.setProperty("webdriver.chrome.verboseLogging", "true");

        File file = File.createTempFile("chromedriver", "log");
        System.setProperty("webdriver.chrome.logfile", file.toString());

        // Enable performance in Logging Preferences
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        chromeOptions.setCapability("goog:loggingPrefs", logPrefs);

        // Adjust specific performance logging preferences
        chromeOptions.setExperimentalOption("perfLoggingPrefs",
                ImmutableMap.of("enableNetwork", false));

        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.titusfortner.com");

        String output = Files.readAllLines(Paths.get(file.toString())).toString();

        String expectedText = "Page.enable";
        Assertions.assertTrue(output.contains(expectedText));

        // We turned this off
        String unexpectedText = "Network.enable";
        Assertions.assertFalse(output.contains(unexpectedText));
    }

    private void waitForDownload() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
