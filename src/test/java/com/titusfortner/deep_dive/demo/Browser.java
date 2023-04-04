package test.java.com.titusfortner.deep_dive.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.WebDriverWait;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;
import test.java.com.titusfortner.deep_dive.demo.elements.ElementList;

import java.time.Duration;
import java.util.concurrent.Callable;

public class Browser {
    private final WebDriver driver;
    protected final WebDriverWait wait;

    public Browser(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public Element getElement(By locator) {
        return new Element(driver, driver, locator);
    }

    public ElementList getElements(By locator) {
        return new ElementList(driver, driver, locator);
    }

    public void quit() {
        driver.quit();
    }

    public void get(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public Object waitUntil(Callable<Object> block) {
        return wait.until(driver -> {
            try {
                return block.call();
            } catch (WebDriverException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
