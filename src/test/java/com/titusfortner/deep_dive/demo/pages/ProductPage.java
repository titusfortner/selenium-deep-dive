package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;

import java.util.function.Function;

public class ProductPage extends BasePage {
    private final Element addToCartButton = new Element(driver, By.cssSelector("button[data-test^='add-to-cart-']"));
    private final Element removeFromCartButton = new Element(driver, By.cssSelector("button[data-test^='remove']"));

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void addItemToCartSuccessfully() {
        HeaderSection headerSection = new HeaderSection(driver);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before + 1;

        addToCartButton.click();

        try {
            wait.until((Function<WebDriver, Object>) driver -> expected.equals(headerSection.getNumberItemsInCart()));
        } catch (TimeoutException ex) {
            String what = "Adding item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }

    public void removeItemFromCartSuccessfully() {
        HeaderSection headerSection = new HeaderSection(driver);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before - 1;

        removeFromCartButton.click();

        try {
            wait.until((Function<WebDriver, Object>) driver -> expected.equals(headerSection.getNumberItemsInCart()));
        } catch (TimeoutException ex) {
            String what = "Removing item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }
}
