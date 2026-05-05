package com.titusfortner.deep_dive.driver;

import com.titusfortner.deep_dive.TestBase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasDownloads;
import org.openqa.selenium.HasDownloads.DownloadedFile;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GridDownloadsTest extends TestBase {
  @BeforeAll
  public static void setup() {
    startGrid();
  }

  @Test
  public void listDownloadedFiles() {
    ChromeOptions options = new ChromeOptions();
    options.setEnableDownloads(true);

    driver = new Augmenter().augment(new RemoteWebDriver(gridUrl, options));

    driver.get("https://www.selenium.dev/selenium/web/downloads/download.html");
    driver.findElement(By.id("file-1")).click();
    driver.findElement(By.id("file-2")).click();
    waitForDownloadedFiles(2);

    List<DownloadedFile> files = ((HasDownloads) driver).getDownloadedFiles();
    List<String> names = files.stream().map(DownloadedFile::getName).collect(Collectors.toList());

    Assertions.assertEquals(List.of("file_1.txt", "file_2.jpg"), names);
  }

  @Test
  public void downloadFile() throws IOException {
    ChromeOptions options = new ChromeOptions();
    options.setEnableDownloads(true);

    driver = new Augmenter().augment(new RemoteWebDriver(gridUrl, options));

    driver.get("https://www.selenium.dev/selenium/web/downloads/download.html");
    driver.findElement(By.id("file-1")).click();
    waitForDownloadedFiles(1);

    DownloadedFile file = ((HasDownloads) driver).getDownloadedFiles().get(0);
    Assertions.assertEquals("file_1.txt", file.getName());

    Path targetDir = Files.createTempDirectory("selenium-grid-downloads");
    ((HasDownloads) driver).downloadFile(file.getName(), targetDir);

    Assertions.assertTrue(Files.exists(targetDir.resolve("file_1.txt")));
  }

  @Test
  public void deleteDownloadedFiles() {
    ChromeOptions options = new ChromeOptions();
    options.setEnableDownloads(true);

    driver = new Augmenter().augment(new RemoteWebDriver(gridUrl, options));

    driver.get("https://www.selenium.dev/selenium/web/downloads/download.html");
    driver.findElement(By.id("file-1")).click();
    waitForDownloadedFiles(1);

    ((HasDownloads) driver).deleteDownloadableFiles();

    Assertions.assertTrue(((HasDownloads) driver).getDownloadedFiles().isEmpty());
  }

  private void waitForDownloadedFiles(int expectedCount) {
    new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(
            d ->
                ((HasDownloads) d)
                        .getDownloadedFiles().stream()
                        .filter(f -> f.hasExtension(".txt") || f.hasExtension(".jpg"))
                        .count()
                    == expectedCount);
  }
}
