package test.java.com.titusfortner.deep_dive.demo.elements;

import com.sun.tools.javac.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class Element {
    private final By locator;
    private final WebDriverWait wait;
    private final SearchContext context;
    private WebElement cachedElement;
    private boolean located = false;

    public Element(WebDriver driver, By locator) {
        this.locator = locator;
        this.context = driver;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.ignoreAll(List.of(ElementNotInteractableException.class,
                StaleElementReferenceException.class));
    }

    public boolean isDisplayed() {
        locate();
        return cachedElement.isDisplayed();
    }

    public void sendKeys(String value) {
        wait.until((Function<WebDriver, Object>) d -> {
            relocateIfNecessary();
            cachedElement.sendKeys(value);
            return true;
        });
    }

    public void click() {
        wait.until((Function<WebDriver, Object>) d -> {
            relocateIfNecessary();
            cachedElement.click();
            return true;
        });
    }

    private void locate() {
        if (this.cachedElement == null) {
            this.cachedElement = context.findElement(locator);
        }
    }

    private void relocate() {
        this.cachedElement = null;
        locate();
    }

    private void relocateIfNecessary() {
        if (this.cachedElement != null && !this.located) {
            this.located = true;
        } else {
            relocate();
        }
    }
}
