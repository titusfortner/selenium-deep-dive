package test.java.com.titusfortner.deep_dive.devtools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.bidi.LogInspector;
import org.openqa.selenium.bidi.log.ConsoleLogEntry;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

/**
 * This is what is actually implemented with new BiDi Protocol
 * Everything is in active development
 */
public class BiDiTest extends BaseTest {
    @BeforeEach
    public void setup() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("webSocketUrl", true);
        seleniumLogger.setLevel(Level.OFF);
        seleniumLogger.geckodriver().disable();
        driver = new FirefoxDriver(options);
    }

    /**
     * Get Console Logs!
     */
    @Test
    public void consoleLogs() throws ExecutionException, InterruptedException, TimeoutException {
        ConsoleLogEntry logEntry;
        CompletableFuture<ConsoleLogEntry> future = new CompletableFuture<>();
        driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

        try (LogInspector logInspector = new LogInspector(driver)) {
            logInspector.onConsoleEntry(future::complete);

            driver.findElement(By.id("consoleLog")).click();
            logEntry = future.get(5, TimeUnit.SECONDS);
        }

        Assertions.assertEquals("Hello, world!", logEntry.getText());
        Assertions.assertEquals("console", logEntry.getType());
        Assertions.assertEquals("log", logEntry.getMethod());
    }

    /**
     * Get JS Exceptions!
     */
    @Test
    public void jsExceptions() throws ExecutionException, InterruptedException, TimeoutException {
        JavascriptLogEntry logEntry;
        CompletableFuture<JavascriptLogEntry> future = new CompletableFuture<>();
        driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

        try (LogInspector logInspector = new LogInspector(driver)) {
            logInspector.onJavaScriptException(future::complete);
            driver.findElement(By.id("jsException")).click();

            logEntry = future.get(5, TimeUnit.SECONDS);
        }

        Assertions.assertEquals("Error: Not working", logEntry.getText());
        Assertions.assertEquals("javascript", logEntry.getType());
    }
}
