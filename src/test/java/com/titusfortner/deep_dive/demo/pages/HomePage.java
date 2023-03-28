package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import test.java.com.titusfortner.deep_dive.demo.data.User;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;
import test.java.com.titusfortner.deep_dive.demo.elements.ElementList;

import java.util.function.Function;

public class HomePage extends BasePage {
    public static final String URL = "https://www.saucedemo.com/";

    private final Element usernameTextfield = new Element(driver, By.cssSelector("input[data-test='username']"));
    private final Element passwordTextfield = new Element(driver, By.cssSelector("input[data-test='password']"));
    private final Element loginButton = new Element(driver, By.cssSelector("input[data-test='login-button']"));
    private final ElementList errorElements = new ElementList(driver, By.cssSelector("[data-test=error]"));

    public static HomePage visit(WebDriver driver) {
        HomePage homePage = new HomePage(driver);
        driver.get(URL);
        return homePage;
    }

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void loginUnsuccessfully(User user) {
        login(user);

        try {
            errorElements.waitUntilPresent();
        } catch (TimeoutException ex) {
            String url = driver.getCurrentUrl();
            throw new PageValidationException("Expected login errors, but none were found; current URL: " + url);
        }
    }

    public void loginSuccessfully() {
        loginSuccessfully(User.valid());
    }

    public void loginSuccessfully(User user) {
        login(user);

        try {
            wait.until((Function<WebDriver, Object>) driver -> !URL.equals(driver.getCurrentUrl()));
        } catch (TimeoutException ex) {
            String additional = errorElements.isEmpty() ? "" : " found error: " + errorElements.getFirst().getText();
            throw new PageValidationException("User is not logged in;" + additional);
        }
    }

    private void login(User user) {
        usernameTextfield.sendKeys(user.getUsername());
        passwordTextfield.sendKeys(user.getPassword());
        loginButton.click();
    }
}
