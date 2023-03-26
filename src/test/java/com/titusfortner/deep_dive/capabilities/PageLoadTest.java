package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.time.Duration;
import java.time.Instant;

public class PageLoadTest extends BaseTestChrome {
    public long navigationTime() {
        Instant start = Instant.now();
        driver.get("https://nytimes.com");
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(timeElapsed + " milliseconds");

        return timeElapsed;
    }

    /**
     * Waits for Document readiness state to be "complete"
     * This is the default setting.
     */
    @Test
    public void pageLoadNormal() {
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);

        long timeElapsed = navigationTime();

        Assertions.assertTrue(timeElapsed > 4000);
    }

    /**
     * Waits for Document readiness state to be "interactive"
     * Useful if there is an extraneous asset that isn't loading
     */
    @Test
    public void pageLoadEager() {
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(chromeOptions);

        long timeElapsed = navigationTime();

        Assertions.assertTrue(timeElapsed < 4000);
        Assertions.assertTrue(timeElapsed > 1000);
    }

    /**
     * No waiting
     * To educate people who don't think they have any race conditions in their code
     */
    @Test
    public void pageLoadNone() {
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver = new ChromeDriver(chromeOptions);

        long timeElapsed = navigationTime();

        Assertions.assertTrue(timeElapsed < 50);
    }
}
