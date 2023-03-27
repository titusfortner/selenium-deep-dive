package test.java.com.titusfortner.deep_dive.decorators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomDriverListener implements WebDriverListener {
    WebDriverWait wait;

    public CustomDriverListener(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void beforeClick(WebElement element) {
        wait.until(driver -> element.isDisplayed());
    }
}
