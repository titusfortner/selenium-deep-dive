package test.java.com.titusfortner.deep_dive.browser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WindowType;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.util.Collections;

public class WindowsTest extends BaseTestChrome {
    @BeforeEach
    public void start() {
        startDriver();
    }

    @Test
    public void windowSwitching() {
        driver.get("https://google.com");
        String originalWindow = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.WINDOW);

        driver.close();
        Assertions.assertEquals(driver.getWindowHandles(), Collections.singleton(originalWindow));

        driver.switchTo().window(originalWindow);
        Assertions.assertEquals(driver.getWindowHandle(), originalWindow);
    }
}

