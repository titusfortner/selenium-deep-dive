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

    public InventoryPage login() {
        HomePage homePage = new HomePage(driver);
        return homePage.login("standard_user", "secret_sauce");
    }

    @Test
    public void addFromProductPage() {
        InventoryPage inventoryPage = login();
        ProductPage productPage = inventoryPage.viewBoltTShirtProduct();

        productPage.addItemToCart();

        Assertions.assertEquals(1,
                productPage.getNumberItemsInCart(),
                "Item not correctly added to cart");
    }

    @Test
    public void removeFromProductPage() {
        InventoryPage inventoryPage = login();
        ProductPage productPage = inventoryPage.viewBoltTShirtProduct();

        productPage.addItemToCart();
        productPage.removeItemFromCart();

        Assertions.assertEquals(0,
                productPage.getNumberItemsInCart(),
                "Item not correctly removed from cart");
    }

    @Test
    public void addFromInventoryPage() {
        InventoryPage inventoryPage = login();
        inventoryPage.addItem(Product.ONESIE);

        Assertions.assertEquals(1,
                inventoryPage.getNumberItemsInCart(),
                "Item not correctly added to cart");
    }

    @Test
    public void removeFromInventoryPage() {
        InventoryPage inventoryPage = login();
        inventoryPage.addItem(Product.BIKE_LIGHT);

        inventoryPage.removeItem(Product.BIKE_LIGHT);

        Assertions.assertEquals(0,
                inventoryPage.getNumberItemsInCart(),
                "Item not correctly removed from cart");
    }

    @Test
    public void removeFromCartPage() {
        InventoryPage inventoryPage = login();
        inventoryPage.addItem(Product.BACKPACK);
        CartPage cartPage = inventoryPage.goToCart();

        cartPage.removeItem(Product.BACKPACK);

        Assertions.assertEquals(0,
                cartPage.getNumberItemsInCart(),
                "Item not correctly removed from cart");
    }
}
