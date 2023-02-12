package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class StrictFileInputTest extends BaseTest {
    @BeforeEach
    public void setLogging() {
        seleniumLogger.setLevel(Level.FINE);
    }

    public void uploadFile() {
        driver.get("https://www.selenium.dev/selenium/web/upload_invisible.html");
        WebElement inputElement = driver.findElement(By.id("upload"));

        Assertions.assertEquals("file", inputElement.getAttribute("type"));

        try {
            File file = File.createTempFile("webdriver", "tmp");
            inputElement.sendKeys(file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void chromeStrictInteractabilityFalse() {
        driver = new ChromeDriver();

        Assertions.assertDoesNotThrow(this::uploadFile);
    }

    @Test
    public void chromeStrictInteractabilityTrue() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setStrictFileInteractability(true);
        driver = new ChromeDriver(chromeOptions);

        Assertions.assertThrows(ElementNotInteractableException.class, this::uploadFile);
    }

    @Test
    public void firefoxStrictInteractabilityFalse() throws IOException {
        driver = new FirefoxDriver();

        Assertions.assertDoesNotThrow(this::uploadFile);
    }

    @Test
    public void firefoxStrictInteractabilityTrue() throws IOException {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setStrictFileInteractability(true);
        driver = new FirefoxDriver(firefoxOptions);

        Assertions.assertThrows(ElementNotInteractableException.class, this::uploadFile);
    }
}
