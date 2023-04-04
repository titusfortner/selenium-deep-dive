package test.java.com.titusfortner.deep_dive.demo.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ElementList {
    private final By locator;
    private final SearchContext context;
    private List<WebElement> cachedElements;
    private final WebDriverWait wait;

    public ElementList(WebDriver driver, SearchContext context, By locator) {
        this.locator = locator;
        this.context = context;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void reset() {
        this.cachedElements = null;
    }

    public boolean isEmpty() {
        locateAll();
        return cachedElements.isEmpty();
    }

    public WebElement get(int i) {
        waitUntilPresent();
        return cachedElements.get(i);
    }

    public WebElement getFirst() {
        return get(0);
    }

    public WebElement getRandom() {
        waitUntilPresent();
        return cachedElements.get(new Random().nextInt(cachedElements.size()));
    }

    public void waitUntilPresent() {
        wait.until((Function<WebDriver, Object>) d -> {
            reset();
            return !isEmpty();
        });
    }

    private void locateAll() {
        if (this.cachedElements == null) {
            this.cachedElements = context.findElements(locator);
        }
    }
}
