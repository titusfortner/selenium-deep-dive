package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;
import test.java.com.titusfortner.deep_dive.demo.elements.ElementList;

import java.util.function.Function;

public class CartPage extends BasePage {
    private final Element checkoutButton = new Element(driver, By.cssSelector("button[data-test='checkout']"));
    private final ElementList removeItemButtons = new ElementList(driver, By.cssSelector("button[data-test^='remove-']"));

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void checkout() {
        checkoutButton.click();
    }

    public void removeItemSuccessfully(Product product) {
        HeaderSection headerSection = new HeaderSection(driver);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before - 1;

        removeItemButtons.getRandom().click();

        try {
            wait.until((Function<WebDriver, Object>) driver -> {
                return expected.equals(headerSection.getNumberItemsInCart());
            });
        } catch (TimeoutException ex) {
            String what = "Removing item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }
}
