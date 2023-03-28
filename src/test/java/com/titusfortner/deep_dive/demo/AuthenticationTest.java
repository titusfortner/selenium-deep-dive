package test.java.com.titusfortner.deep_dive.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;
import test.java.com.titusfortner.deep_dive.demo.pages.HeaderSection;
import test.java.com.titusfortner.deep_dive.demo.pages.HomePage;

public class AuthenticationTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void signInUnsuccessful() {
        HomePage homePage = HomePage.visit(driver);

        Assertions.assertDoesNotThrow(() ->
                homePage.loginUnsuccessfully("locked_out_user", "secret_sauce")
        );
    }

    @Test
    public void signInSuccessful() {
        HomePage homePage = HomePage.visit(driver);

        Assertions.assertDoesNotThrow(() ->
                homePage.loginSuccessfully("standard_user", "secret_sauce")
        );
    }

    @Test
    public void logout() {
        HomePage homePage = HomePage.visit(driver);
        homePage.loginSuccessfully("standard_user", "secret_sauce");

        Assertions.assertDoesNotThrow(new HeaderSection(driver)::logOutSuccessfully);
    }
}
