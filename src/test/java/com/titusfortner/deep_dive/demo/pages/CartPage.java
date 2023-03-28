package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import test.java.com.titusfortner.deep_dive.demo.Browser;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;
import test.java.com.titusfortner.deep_dive.demo.elements.ElementList;

public class CartPage extends BasePage {
    private final Element checkoutButton = browser.getElement(By.cssSelector("button[data-test='checkout']"));
    private final ElementList removeItemButtons = browser.getElements(By.cssSelector("button[data-test^='remove-']"));

    public CartPage(Browser browser) {
        super(browser);
    }

    public void checkout() {
        checkoutButton.click();
    }

    public void removeItemSuccessfully(Product product) {
        HeaderSection headerSection = new HeaderSection(browser);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before - 1;

        removeItemButtons.getRandom().click();

        try {
            browser.waitUntil(() -> expected.equals(headerSection.getNumberItemsInCart()));
        } catch (TimeoutException ex) {
            String what = "Removing item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }
}
