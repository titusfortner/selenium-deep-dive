package com.titusfortner.deep_dive.browser;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BrowserInteractionsTest extends TestBase {
  @BeforeEach
  public void start() {
    startChrome();
  }

  @Test
  public void browserInformation() {
    driver.get("https://selenium.dev");

    // "Selenium"
    driver.getTitle();

    // "https://selenium.dev/"
    driver.getCurrentUrl();

    // "<html> ... </html>"
    driver.getPageSource();
  }

  @Test
  public void browserNavigation() {
    driver.get("https://selenium.dev");

    driver.navigate().to("https://selenium.dev/documentation");
    driver.navigate().refresh();
    driver.navigate().back();
    driver.navigate().forward();
  }
}
