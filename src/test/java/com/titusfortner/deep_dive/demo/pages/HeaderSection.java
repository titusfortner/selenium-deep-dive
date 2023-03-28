package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import test.java.com.titusfortner.deep_dive.demo.Browser;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;
import test.java.com.titusfortner.deep_dive.demo.elements.ElementList;

import java.util.function.Function;

public class HeaderSection extends BasePage {
    private final Element menuButton = browser.getElement(By.id("react-burger-menu-btn"));
    private final Element logoutLink = browser.getElement(By.id("logout_sidebar_link"));
    private final ElementList shoppingCartItems = browser.getElements(By.className("shopping_cart_badge"));

    public HeaderSection(Browser browser) {
        super(browser);
    }

    public Integer getNumberItemsInCart() {
        shoppingCartItems.reset();
        if (shoppingCartItems.isEmpty()) {
            return 0;
        } else {
            return Integer.valueOf(shoppingCartItems.getFirst().getText());
        }
    }

    public void logOutSuccessfully() {
        logOut();

        try {
            browser.waitUntil(() -> !InventoryPage.URL.equals(browser.getCurrentUrl()));
        } catch (TimeoutException ex) {
            throw new PageValidationException("User is still logged in;");
        }
    }

    public void logOut() {
        menuButton.click();
        logoutLink.click();
    }
}
