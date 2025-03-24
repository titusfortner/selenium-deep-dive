package com.titusfortner.deep_dive.browser;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FramesTest extends TestBase {
  @BeforeEach
  public void start() {
    startChrome();
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
