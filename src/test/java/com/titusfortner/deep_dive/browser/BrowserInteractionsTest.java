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
    driver.get("http://a.testaddressbook.com");

    // "Address Book"
    driver.getTitle();

    // "http://a.testaddressbook.com/"
    driver.getCurrentUrl();

    // "<html> ... </html>"
    driver.getPageSource();
  }

  @Test
  public void browserNavigation() {
    driver.get("http://a.testaddressbook.com");

    driver.navigate().to("http://google.com");
    driver.navigate().refresh();
    driver.navigate().back();
    driver.navigate().forward();
  }
}
