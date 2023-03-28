package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import test.java.com.titusfortner.deep_dive.demo.Browser;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;

public class InventoryPage extends BasePage {
    public static final String URL = "https://www.saucedemo.com/inventory.html";

    private final Element item1Link = browser.getElement(By.id("item_1_title_link"));
    private final Element shoppingCartLink = browser.getElement(By.className("shopping_cart_link"));

    public InventoryPage(Browser browser) {
        super(browser);
    }

    public void viewBoltTShirtProduct() {
        item1Link.click();
    }

    public void goToCart() {
        shoppingCartLink.click();
    }

    public void addItemSuccessfully(Product product) {
        HeaderSection headerSection = new HeaderSection(browser);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before + 1;

        addItem(product);

        try {
            browser.waitUntil(() -> expected.equals(headerSection.getNumberItemsInCart()));
        } catch (TimeoutException ex) {
            String what = "Adding item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }

    public void removeItemSuccessfully(Product product) {
        HeaderSection headerSection = new HeaderSection(browser);
        Integer before = headerSection.getNumberItemsInCart();
        Integer expected = before - 1;

        removeItem(product);

        try {
            browser.waitUntil(() -> expected.equals(headerSection.getNumberItemsInCart()));
        } catch (TimeoutException ex) {
            String what = "Removing item unsuccessful; ";
            String after = headerSection.getNumberItemsInCart().toString();
            throw new PageValidationException(what + "Expected: " + expected + ", but found: " + after);
        }
    }

    private void addItem(Product product) {
        String cssSelector = "button[data-test='add-to-cart-" + product.getId() + "']";
        browser.getElement(By.cssSelector(cssSelector)).click();
    }

    private void removeItem(Product product) {
        String cssSelector = "button[data-test='remove-" + product.getId() + "']";
        browser.getElement(By.cssSelector(cssSelector)).click();
    }
}
