package com.titusfortner.deep_dive.capabilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxTest extends TestBase {
  FirefoxOptions firefoxOptions = new FirefoxOptions();

  /** https://wiki.mozilla.org/Firefox/CommandLineOptions Open browser to specific page */
  @Test
  public void addArgument() {
    String url = "https://titusfortner.com/";
    firefoxOptions.addArguments("-new-window=" + url);
    startFirefox(firefoxOptions);

    Assertions.assertEquals(url, driver.getCurrentUrl());
  }

  /**
   * profile parameter is intended to be for Base64-encoded ZIP of a profile directory primarily for
   * use when running remotely When running locally, use: `{"args": ["-profile",
   * "/path/to/your/profile"]}` Currently FirefoxProfile class has a lot more things it tries to do,
   * but that is being mostly deprecated https://github.com/SeleniumHQ/selenium/issues/11587
   */
  @Test
  public void profile() throws IOException {
    File pretendProfileLocation = Files.createTempDirectory("se-deep-dive").toFile();
    FirefoxProfile profile = new FirefoxProfile(pretendProfileLocation);

    String url = "https://titusfortner.com/";
    profile.setPreference("browser.startup.homepage", url);
    firefoxOptions.setProfile(profile);

    startFirefox(firefoxOptions);

    Assertions.assertEquals(url, driver.getCurrentUrl());
  }

  /**
   * Driver logging levels should move to service class -
   * https://github.com/SeleniumHQ/selenium/issues/11410 Set the log level for how much output to
   * see
   */
  @Test
  public void optionsLogging() {
    firefoxOptions.setLogLevel(FirefoxDriverLogLevel.ERROR);
    startFirefox(firefoxOptions);

    driver.get("https://www.titusfortner.com/");
  }

  /** Change the download directory */
  @Test
  public void changePrefs() throws IOException {
    String downloadDirectory = Files.createTempDirectory("webdriver").toString();

    firefoxOptions.addPreference("browser.download.dir", downloadDirectory);
    firefoxOptions.addPreference("browser.download.folderList", 2);
    startPatientFirefox(firefoxOptions);

    driver.get("https://filesamples.com/formats/csv/");
    driver.findElement(By.id("ez-accept-all")).click();

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
