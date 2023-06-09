package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import test.java.com.titusfortner.deep_dive.demo.Browser;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;

public class ProductPage extends BasePage {
    private final Element addToCartButton = browser.getElement(By.cssSelector("button[data-test^='add-to-cart-']"));
    private final Element removeFromCartButton = browser.getElement(By.cssSelector("button[data-test^='remove']"));

    public ProductPage(Browser browser) {
        super(browser);
    }

    public void addItemToCartSuccessfully() {
        HeaderSection headerSection = new HeaderSection(browser);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before + 1;

        addToCartButton.click();

        try {
            browser.waitUntil(() -> expected.equals(headerSection.getNumberItemsInCart()));
        } catch (TimeoutException ex) {
            String what = "Adding item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }

    public void removeItemFromCartSuccessfully() {
        HeaderSection headerSection = new HeaderSection(browser);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before - 1;

        removeFromCartButton.click();

        try {
            browser.waitUntil(() -> expected.equals(headerSection.getNumberItemsInCart()));
        } catch (TimeoutException ex) {
            String what = "Removing item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }
}
