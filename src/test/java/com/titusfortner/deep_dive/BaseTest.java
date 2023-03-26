package test.java.com.titusfortner.deep_dive;

import com.titusfortner.logging.SeleniumLogger;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
public class BaseTest {
    protected WebDriver driver;
    protected SeleniumLogger seleniumLogger = new SeleniumLogger();

    @AfterEach
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
