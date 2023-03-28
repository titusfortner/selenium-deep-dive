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

public class CartTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    public void login() {
        HomePage homePage = HomePage.visit(driver);
        homePage.loginSuccessfully("standard_user", "secret_sauce");
    }

    @Test
    public void addFromProductPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.viewBoltTShirtProduct();

        Assertions.assertDoesNotThrow(new ProductPage(driver)::addItemToCartSuccessfully);
    }

    @Test
    public void removeFromProductPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.viewBoltTShirtProduct();
        ProductPage productPage = new ProductPage(driver);
        productPage.addItemToCartSuccessfully();

        Assertions.assertDoesNotThrow(productPage::removeItemFromCartSuccessfully);
    }

    @Test
    public void addFromInventoryPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(driver);

        Assertions.assertDoesNotThrow(() -> inventoryPage.addItemSuccessfully(Product.ONESIE));
    }

    @Test
    public void removeFromInventoryPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addItemSuccessfully(Product.BIKE_LIGHT);

        Assertions.assertDoesNotThrow(() -> inventoryPage.removeItemSuccessfully(Product.BIKE_LIGHT));
    }

    @Test
    public void removeFromCartPage() {
        login();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addItemSuccessfully(Product.BACKPACK);
        inventoryPage.goToCart();

        Assertions.assertDoesNotThrow(() -> new CartPage(driver).removeItemSuccessfully(Product.BACKPACK));
    }
}
