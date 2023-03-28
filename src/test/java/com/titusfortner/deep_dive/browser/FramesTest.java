package test.java.com.titusfortner.deep_dive.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class FramesTest extends BaseTestChrome {
    @BeforeEach
    public void start() {
        startDriver();
    }

    @Test
    public void frameSwitching() {
        driver.get("https://the-internet.herokuapp.com/nested_frames");

        // "BOTTOM"
        driver.switchTo().frame(1);

        // Move up one context
        driver.switchTo().parentFrame();

        // ""
        driver.switchTo().frame("frame-top");

        // "LEFT"
        driver.switchTo().frame("frame-left");

        // Move back to top context
        driver.switchTo().defaultContent();
    }

}

