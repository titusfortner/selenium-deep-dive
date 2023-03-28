package test.java.com.titusfortner.deep_dive.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.io.File;
import java.io.IOException;

public class FileUploadTest extends BaseTestChrome {
    @BeforeEach
    public void start() {
        startDriver();
    }

    @Test
    public void uploadFile() throws IOException {
        // FileDetector Required when using Remote WebDriver
        // driver.setFileDetector(new LocalFileDetector());

        driver.get("http://the-internet.herokuapp.com/upload");

        WebElement inputElement = driver.findElement(By.id("file-upload"));
        File file = new File(".gitignore");
        inputElement.sendKeys(file.getCanonicalPath());
    }
}
