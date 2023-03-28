package test.java.com.titusfortner.deep_dive.demo.pages;

import com.sun.tools.javac.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.ignoreAll(List.of(ElementNotInteractableException.class,
                StaleElementReferenceException.class));
    }

    public void sendKeys(By locator, String value) {
        wait.until((Function<WebDriver, Object>) d -> {
            d.findElement(locator).sendKeys(value);
            return true;
        });
    }

    public void click(By locator) {
        wait.until((Function<WebDriver, Object>) d -> {
            d.findElement(locator).click();
            return true;
        });
    }
}
