package test.java.com.titusfortner.deep_dive.demo;

import com.titusfortner.logging.SeleniumLogger;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.io.File;

public class BaseTestDemo {
    protected Browser browser;
    protected SeleniumLogger seleniumLogger = new SeleniumLogger();

    protected ChromeOptions chromeOptions = new ChromeOptions();

    protected ChromeOptions getOptions() {
        chromeOptions.addExtensions(new File("src/test/resources/SelectorsHub.crx"));
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);

        return chromeOptions;
    }

    protected void startDriver() {
        WebDriver driver = new ChromeDriver(getOptions());
        browser = new Browser(driver);
    }

    @AfterEach
    public void quitDriver() {
        browser.quit();
    }

}
