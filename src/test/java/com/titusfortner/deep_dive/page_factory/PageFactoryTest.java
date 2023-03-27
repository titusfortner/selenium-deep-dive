package test.java.com.titusfortner.deep_dive.page_factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.util.logging.Level;

public class PageFactoryTest extends BaseTestChrome {

    @BeforeEach
    public void setup() {
        startDriver();
        seleniumLogger.setLevel(Level.FINE);
    }

    /**
     * 10 Commands
     */
    @Test
    public void submitForm() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        PageFactoryPage page = new PageFactoryPage(driver);
        page.submitForm("name", "password");

        WebElement message = driver.findElement(By.id("message"));
        Assertions.assertEquals("Received!", message.getText());
    }

    /**
     * Same result; 22 Commands
     */
    @Test
    public void submitSynchronizedForm() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        PageFactoryPage page = new PageFactoryPage(driver);
        page.synchronizedSubmitForm("name", "password");

        WebElement message = driver.findElement(By.id("message"));
        Assertions.assertEquals("Received!", message.getText());
    }
}
