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

    public void login() {
        HomePage homePage = HomePage.visit(driver);
        homePage.loginSuccessfully("standard_user", "secret_sauce");
    }

    public void goToCheckoutWithItem() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addItemSuccessfully(Product.ONESIE);
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.checkout();
    }

    @Test
    public void goodInfo() {
        login();
        goToCheckoutWithItem();
        InformationPage informationPage = new InformationPage(driver);

        Assertions.assertDoesNotThrow(() -> {
            informationPage.addInformationSuccessfully("Luke", "Perry", "90210");
        });
    }

    @Test
    public void completeCheckout() {
        login();
        goToCheckoutWithItem();
        InformationPage informationPage = new InformationPage(driver);
        informationPage.addInformationSuccessfully("Luke", "Perry", "90210");

        Assertions.assertDoesNotThrow(new CheckoutPage(driver)::finishSuccessfully);
    }
}
