package test.java.com.titusfortner.deep_dive.decorators;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.SlowLoadableComponent;

import java.time.Clock;
public class CustomLoadableComponent extends SlowLoadableComponent<CustomLoadableComponent> {
    private final SearchContext context;
    private final By locator;
    private WebElement element;

    public CustomLoadableComponent(SearchContext context, By locator) {
        super(Clock.systemDefaultZone(), 20);
        this.context = context;
        this.locator = locator;
    }

    @Override protected void load() {
        // Nothing
    }

    @Override protected void isLoaded() throws Error {
        try {
            element = context.findElement(locator);
        } catch(NoSuchElementException e) {
            throw new Error("Element not found", e);
        }

        if (!element.isDisplayed()) {
            throw new Error("Element is not displayed");
        }
    }

    public WebElement getElement() {
        return element;
    }
}
