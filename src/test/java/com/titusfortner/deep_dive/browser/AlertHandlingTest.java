package test.java.com.titusfortner.deep_dive.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class AlertHandlingTest extends BaseTestChrome {
    @BeforeEach
    public void start() {
        startDriver();
    }

    @Test
    public void alertHandling() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("alert('Hello World');");

        Alert alert = driver.switchTo().alert();

        // "Hello World"
        alert.getText();

        // Close Alert
        alert.dismiss();
    }

}

