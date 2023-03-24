package test.java.com.titusfortner.deep_dive;

import com.titusfortner.logging.SeleniumLogger;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.logging.Level;

public class WorkingTest {
    /**
     * Opens and closes a browser
     */
    @Test
    public void validate() {
        new SeleniumLogger().setLevel(Level.FINE);

        WebDriver driver = new ChromeDriver();

        driver.quit();
    }
}
