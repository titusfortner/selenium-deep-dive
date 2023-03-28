package test.java.com.titusfortner.deep_dive.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.util.Date;

public class CookiesTest extends BaseTestChrome {
    @BeforeEach
    public void start() {
        startDriver();
    }

    @Test
    public void cIsForCookie() {
        driver.get("http://a.testaddressbook.com");

        // Create
        Cookie cookie = new Cookie.Builder("name", "value")
                .domain("a.testaddressbook.com")
                .expiresOn(new Date(System.currentTimeMillis() + 500000000))
                .isHttpOnly(true)
                .isSecure(false)
                .path("/mypath")
                .build();

        // Add
        driver.manage().addCookie(cookie);

        // Read
        driver.get("http://a.testaddressbook.com/mypath");
        driver.manage().getCookieNamed("name");

        // Get All
        driver.manage().getCookies();

        // Delete
        driver.manage().deleteCookie(cookie);
    }

}

