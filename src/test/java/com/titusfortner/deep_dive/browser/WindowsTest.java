package com.titusfortner.deep_dive.browser;


import com.titusfortner.deep_dive.TestBase;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WindowType;

public class WindowsTest extends TestBase {
  @BeforeEach
  public void start() {
    startChrome();
  }

  @Test
  public void windowSwitching() {
    driver.get("https://selenium.dev");
    String originalWindow = driver.getWindowHandle();

    driver.switchTo().newWindow(WindowType.WINDOW);

    driver.close();
    Assertions.assertEquals(driver.getWindowHandles(), Collections.singleton(originalWindow));

    driver.switchTo().window(originalWindow);
    Assertions.assertEquals(driver.getWindowHandle(), originalWindow);
  }
}
