package test.java.com.titusfortner.deep_dive.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;
import test.java.com.titusfortner.deep_dive.demo.pages.HeaderSection;
import test.java.com.titusfortner.deep_dive.demo.pages.HomePage;
import test.java.com.titusfortner.deep_dive.demo.pages.InventoryPage;

public class AuthenticationTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void signInUnsuccessful() {
        HomePage homePage = HomePage.visit(driver);

        homePage.login("locked_out_user", "secret_sauce");

        Assertions.assertTrue(homePage.isLockedOut(), "Error Not Found");
    }

    @Test
    public void signInSuccessful() {
        HomePage homePage = HomePage.visit(driver);

        homePage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assertions.assertTrue(inventoryPage.isOnPage(), "Login Not Successful");
    }

    @Test
    public void logout() {
        HomePage homePage = HomePage.visit(driver);
        homePage.login("standard_user", "secret_sauce");

        new HeaderSection(driver).logOut();

        Assertions.assertTrue(homePage.isOnPage(), "Logout Not Successful");
    }
}
