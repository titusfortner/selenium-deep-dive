package test.java.com.titusfortner.deep_dive.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;
import test.java.com.titusfortner.deep_dive.demo.pages.CartPage;
import test.java.com.titusfortner.deep_dive.demo.pages.CheckoutPage;
import test.java.com.titusfortner.deep_dive.demo.pages.FinishPage;
import test.java.com.titusfortner.deep_dive.demo.pages.HomePage;
import test.java.com.titusfortner.deep_dive.demo.pages.InformationPage;
import test.java.com.titusfortner.deep_dive.demo.pages.InventoryPage;
import test.java.com.titusfortner.deep_dive.demo.pages.Product;

public class CheckoutTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    public InventoryPage login() {
        HomePage homePage = new HomePage(driver);
        return homePage.login("standard_user", "secret_sauce");
    }

    public InformationPage goToCheckoutWithItem() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addItem(Product.ONESIE);
        CartPage cartPage = inventoryPage.goToCart();
        return cartPage.checkout();
    }

    @Test
    public void goodInfo() {
        login();
        InformationPage informationPage = goToCheckoutWithItem();

        CheckoutPage checkoutPage = informationPage.addInformation("Luke", "Perry", "90210");

        Assertions.assertTrue(checkoutPage.isOnPage(),"Information Submission Unsuccessful");
    }

    @Test
    public void completeCheckout() {
        login();
        InformationPage informationPage = goToCheckoutWithItem();
        CheckoutPage checkoutPage = informationPage.addInformation("Luke", "Perry", "90210");

        FinishPage finish = checkoutPage.finish();

        Assertions.assertTrue(finish.isOnPage());
        Assertions.assertTrue(finish.isComplete());
    }
}
