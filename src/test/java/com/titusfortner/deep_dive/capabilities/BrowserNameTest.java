package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.util.logging.Level;

public class BrowserNameTest extends BaseTest {
    @BeforeEach
    public void setLogging() {
        seleniumLogger.setLevel(Level.FINE);
    }

    @Test
    public void chrome() {
        driver = new ChromeDriver();
    }

    @Test
    public void edge() {
        driver = new EdgeDriver();
    }

    @Test
    public void firefox() {
        driver = new FirefoxDriver();
    }

    @Test
    @Disabled
    public void ie() {
        driver = new InternetExplorerDriver();
    }

    @Test
    @Disabled
    public void safari() {
        driver = new SafariDriver();
    }
}
