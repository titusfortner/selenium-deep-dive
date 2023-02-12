package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;

public class TimeoutTest extends BaseTestChrome {

    @Test
    public void scriptTimeout() {
        seleniumLogger.setLevel(Level.FINE);

        chromeOptions.setScriptTimeout(Duration.ofSeconds(2));
        driver = new ChromeDriver(chromeOptions);

        Instant start = Instant.now();

        Assertions.assertThrows(ScriptTimeoutException.class, () ->
                ((JavascriptExecutor)driver).executeAsyncScript("")
        );

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        Assertions.assertTrue(timeElapsed > 2000);
        Assertions.assertTrue(timeElapsed < 2200);
    }

    @Test
    public void implicitWaitTimeout() {
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(2));
        driver = new ChromeDriver(chromeOptions);

        Instant start = Instant.now();

        Assertions.assertThrows(NoSuchElementException.class, () ->
                driver.findElement(By.id("not-there"))
        );

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        Assertions.assertTrue(timeElapsed > 2000);
        Assertions.assertTrue(timeElapsed < 2200);
    }

    @Test
    public void pageLoadTimeout() {
        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(5));
        driver = new ChromeDriver(chromeOptions);

        Instant start = Instant.now();

        Assertions.assertThrows(TimeoutException.class, () ->
                driver.get("https://titusfortner.com/examples/never_loads.html")
        );

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println(timeElapsed);
        Assertions.assertTrue(timeElapsed > 5000);
        Assertions.assertTrue(timeElapsed < 5200);
    }
}
