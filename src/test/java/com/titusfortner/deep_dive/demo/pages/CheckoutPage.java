package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;

import java.util.function.Function;

public class CheckoutPage extends BasePage {
    public static final String URL = "https://www.saucedemo.com/checkout-step-two.html";
    private final Element finishButton = new Element(driver, By.cssSelector("button[data-test='finish']"));

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void finishSuccessfully() {
        finishButton.click();
        try {
            wait.until((Function<WebDriver, Object>) driver -> !URL.equals(driver.getCurrentUrl()));
        } catch (TimeoutException ex) {
            FinishPage finishPage = new FinishPage(driver);
            if (!finishPage.isComplete()) {
                throw new PageValidationException("Checkout unsuccessful;");
            }
        }
    }
}
