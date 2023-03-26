package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class FirefoxTest extends BaseTest {
    FirefoxOptions firefoxOptions = new FirefoxOptions();

    @BeforeEach
    public void enableLogging() {
        seleniumLogger.setLevel(Level.FINE);
        seleniumLogger.geckodriver().disable();
    }

    /**
     * https://wiki.mozilla.org/Firefox/CommandLineOptions
     * Open browser to specific page
     */
    @Test
    public void addArgument() {
        String url = "https://titusfortner.com/";
        firefoxOptions.addArguments("-new-window=" + url);
        driver = new FirefoxDriver(firefoxOptions);

        Assertions.assertEquals(url, driver.getCurrentUrl());
    }

    /**
     * The class needs to be deprecated - https://github.com/SeleniumHQ/selenium/issues/11587
     * The parameter is intended to accept the location of an existing profile
     */
    @Test
    public void profile() {
        FirefoxProfile profile = new FirefoxProfile();
        String url = "https://titusfortner.com/";
        profile.setPreference("browser.startup.homepage", url);

        firefoxOptions.setProfile(profile);

        driver = new FirefoxDriver(firefoxOptions);

        Assertions.assertEquals(url, driver.getCurrentUrl());
    }

    /**
     * Driver logging levels should move to service class - https://github.com/SeleniumHQ/selenium/issues/11410
     * Set the log level for how much output to see
     */
    @Test
    public void optionsLogging() {
        seleniumLogger.setLevel(Level.OFF);
        seleniumLogger.geckodriver().enable();

        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.DEBUG);
        driver = new FirefoxDriver(firefoxOptions);

        driver.get("https://www.titusfortner.com/");
    }

    /**
     * Change the download directory
     */
    @Test
    public void changePrefs() throws IOException {
        String downloadDirectory = Files.createTempDirectory("webdriver").toString();

        firefoxOptions.addPreference("browser.download.dir", downloadDirectory);
        firefoxOptions.addPreference("browser.download.folderList", 2);
        driver = new FirefoxDriver(firefoxOptions);

        driver.get("https://filesamples.com/formats/csv/");
        driver.findElements(By.cssSelector("[download]")).get(1).click();

        File downloadedFile = new File(downloadDirectory + "/" + "sample3.csv");

        waitForDownload();
        Assertions.assertTrue(downloadedFile.exists());

    }

    private void waitForDownload() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
