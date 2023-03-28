package test.java.com.titusfortner.deep_dive;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class BaseTestChrome extends BaseTest {
    protected ChromeOptions chromeOptions = new ChromeOptions();

    protected ChromeOptions getOptions() {
        chromeOptions.addExtensions(new File("src/test/resources/SelectorsHub.crx"));
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);

        return chromeOptions;
    }

    protected void startDriver() {
        driver = new ChromeDriver(getOptions());
    }
}
