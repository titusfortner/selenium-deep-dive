package com.titusfortner.deep_dive.element;

import java.io.File;
import java.io.IOException;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FileUploadTest extends TestBase {
  @BeforeEach
  public void start() {
    startChrome();
  }

  @Test
  public void uploadFile() throws IOException {
    // FileDetector Required when using Grid
    // driver.setFileDetector(new LocalFileDetector());

    driver.get("http://the-internet.herokuapp.com/upload");

    WebElement inputElement = driver.findElement(By.id("file-upload"));
    File file = new File(".gitignore");
    inputElement.sendKeys(file.getCanonicalPath());
  }
}
