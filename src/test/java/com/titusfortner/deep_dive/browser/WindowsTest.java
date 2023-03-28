package test.java.com.titusfortner.deep_dive.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.util.Set;

public class WindowsTest extends BaseTestChrome {
    @BeforeEach
    public void start() {
        startDriver();
    }

    @Test
    public void windowSwitching() {
        driver.get("https://google.com");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('https://www.example.com');");

        String origWindow = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        handles.remove(origWindow);

        String nextWindow = String.valueOf(handles.iterator().next());

        // "Example Domain"
        driver.switchTo().window(nextWindow);

        driver.close();

        // "Google"
        driver.switchTo().window(origWindow);
    }
}

