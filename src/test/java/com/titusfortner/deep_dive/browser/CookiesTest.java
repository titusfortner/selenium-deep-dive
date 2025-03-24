package com.titusfortner.deep_dive.browser;

import java.util.Date;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

public class CookiesTest extends TestBase {
  @BeforeEach
  public void start() {
    startChrome();
  }

  @Test
  public void cIsForCookie() {
    driver.get("https://selenium.dev");

    // Create
    Cookie cookie =
        new Cookie.Builder("name", "value")
            .domain("selenium.dev")
            .expiresOn(new Date(System.currentTimeMillis() + 500000000))
            .isHttpOnly(true)
            .isSecure(false)
            .path("/documentation")
            .build();

    // Add
    driver.manage().addCookie(cookie);

    // Read
    driver.get("https://selenium.dev/documentation");
    driver.manage().getCookieNamed("name");

    // Get All
    driver.manage().getCookies();

    // Delete
    driver.manage().deleteCookie(cookie);
  }
}
