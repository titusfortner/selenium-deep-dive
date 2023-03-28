package test.java.com.titusfortner.deep_dive.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.java.com.titusfortner.deep_dive.demo.data.User;
import test.java.com.titusfortner.deep_dive.demo.pages.HeaderSection;
import test.java.com.titusfortner.deep_dive.demo.pages.HomePage;

public class AuthenticationTest extends BaseTestDemo {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void signInUnsuccessful() {
        HomePage homePage = HomePage.visit(browser);
        User lockedOutUser = User.lockedOut();

        Assertions.assertDoesNotThrow(() ->
                homePage.loginUnsuccessfully(lockedOutUser)
        );
    }

    @Test
    public void signInSuccessful() {
        HomePage homePage = HomePage.visit(browser);
        User validUser = User.valid();

        Assertions.assertDoesNotThrow(() ->
                homePage.loginSuccessfully(validUser)
        );
    }

    @Test
    public void logout() {
        HomePage homePage = HomePage.visit(browser);
        homePage.loginSuccessfully();

        Assertions.assertDoesNotThrow(new HeaderSection(browser)::logOutSuccessfully);
    }
}
