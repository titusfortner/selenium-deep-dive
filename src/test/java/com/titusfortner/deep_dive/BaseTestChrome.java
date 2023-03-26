package test.java.com.titusfortner.deep_dive;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTestChrome extends BaseTest {
    protected ChromeOptions chromeOptions = new ChromeOptions();

    protected void startDriver() {
        driver = new ChromeDriver(chromeOptions);
    }
}
