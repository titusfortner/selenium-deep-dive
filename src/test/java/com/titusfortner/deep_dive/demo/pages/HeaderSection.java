package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;
import test.java.com.titusfortner.deep_dive.demo.elements.ElementList;

import java.util.function.Function;

public class HeaderSection extends BasePage {
    private final Element menuButton = new Element(driver, By.id("react-burger-menu-btn"));
    private final Element logoutLink = new Element(driver, By.id("logout_sidebar_link"));
    private final ElementList shoppingCartItems = new ElementList(driver, By.className("shopping_cart_badge"));

    public HeaderSection(WebDriver driver) {
        super(driver);
    }

    public Integer getNumberItemsInCart() {
        shoppingCartItems.reset();
        if (shoppingCartItems.isEmpty()) {
            return 0;
        } else {
            return Integer.valueOf(shoppingCartItems.getFirst().getText());
        }
    }

    public boolean isLoggedIn() {
        return InventoryPage.URL.equals(driver.getCurrentUrl());
    }

    public void logOutSuccessfully() {
        logOut();

        try {
            wait.until((Function<WebDriver, Object>) driver -> !isLoggedIn());
        } catch (TimeoutException ex) {
            throw new PageValidationException("User is still logged in;");
        }
    }

    public void logOut() {
        menuButton.click();
        logoutLink.click();
    }
}
