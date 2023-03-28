package test.java.com.titusfortner.deep_dive.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;
import test.java.com.titusfortner.deep_dive.demo.pages.CartPage;
import test.java.com.titusfortner.deep_dive.demo.pages.HomePage;
import test.java.com.titusfortner.deep_dive.demo.pages.InventoryPage;
import test.java.com.titusfortner.deep_dive.demo.pages.Product;
import test.java.com.titusfortner.deep_dive.demo.pages.ProductPage;

public class CartTest extends BaseTestDemo {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    public void login() {
        HomePage homePage = HomePage.visit(browser);
        homePage.loginSuccessfully();
    }

    @Test
    public void addFromProductPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(browser);
        inventoryPage.viewBoltTShirtProduct();

        Assertions.assertDoesNotThrow(new ProductPage(browser)::addItemToCartSuccessfully);
    }

    @Test
    public void removeFromProductPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(browser);
        inventoryPage.viewBoltTShirtProduct();
        ProductPage productPage = new ProductPage(browser);
        productPage.addItemToCartSuccessfully();

        Assertions.assertDoesNotThrow(productPage::removeItemFromCartSuccessfully);
    }

    @Test
    public void addFromInventoryPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(browser);

        Assertions.assertDoesNotThrow(() -> inventoryPage.addItemSuccessfully(Product.ONESIE));
    }

    @Test
    public void removeFromInventoryPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(browser);
        inventoryPage.addItemSuccessfully(Product.BIKE_LIGHT);

        Assertions.assertDoesNotThrow(() -> inventoryPage.removeItemSuccessfully(Product.BIKE_LIGHT));
    }

    @Test
    public void removeFromCartPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(browser);
        inventoryPage.addItemSuccessfully(Product.BACKPACK);
        inventoryPage.goToCart();

        Assertions.assertDoesNotThrow(() -> new CartPage(browser).removeItemSuccessfully(Product.BACKPACK));
    }
}
