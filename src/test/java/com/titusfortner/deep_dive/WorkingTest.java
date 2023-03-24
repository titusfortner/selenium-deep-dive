package test.java.com.titusfortner.deep_dive;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WorkingTest {
    /**
     * Opens and closes a browser
     */
    @Test
    public void validate() {
        WebDriver driver = new ChromeDriver();

        driver.quit();
    }
}
