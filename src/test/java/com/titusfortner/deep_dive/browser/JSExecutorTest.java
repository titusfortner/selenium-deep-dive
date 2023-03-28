package test.java.com.titusfortner.deep_dive.browser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class JSExecutorTest extends BaseTestChrome {
    @BeforeEach
    public void start() {
        startDriver();
    }

    @Test
    public void jsExecute() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement element = driver.findElement(By.cssSelector("button[type=submit]"));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);

        js.executeScript("window.scrollBy(0,50)");
    }

}

