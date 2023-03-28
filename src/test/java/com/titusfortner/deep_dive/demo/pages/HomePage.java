package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    public static final String URL = "https://www.saucedemo.com/";
    private final WebDriver driver;

    private final By usernameTextfield = By.cssSelector("input[data-test='username']");
    private final By passwordTextfield = By.cssSelector("input[data-test='password']");
    private final By loginButton = By.cssSelector("input[data-test='login-button']");
    private final By errorElement = By.cssSelector("[data-test=error]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        if (!isOnPage()) {
            driver.get(URL);
        }
    }

    public InventoryPage login(String username, String password) {
        driver.findElement(usernameTextfield).sendKeys(username);
        driver.findElement(passwordTextfield).sendKeys(password);
        driver.findElement(loginButton).click();

        return new InventoryPage(driver);
    }

    public boolean isLockedOut() {
        return driver.findElement(errorElement).getText().contains("Sorry, this user has been locked out");
    }

    public boolean isOnPage() {
        return URL.equals(driver.getCurrentUrl());
    }
}
