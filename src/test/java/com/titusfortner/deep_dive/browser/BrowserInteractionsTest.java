package test.java.com.titusfortner.deep_dive.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class BrowserInteractionsTest extends BaseTestChrome {
    @BeforeEach
    public void start() {
        startDriver();
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
