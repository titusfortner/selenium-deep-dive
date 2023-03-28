package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    public static final String URL = "https://www.saucedemo.com/checkout-step-two.html";
    private final WebDriver driver;

    private final By finishButton = By.cssSelector("button[data-test='finish']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnPage() {
        return URL.equals(driver.getCurrentUrl());
    }

    public void finish() {
        driver.findElement(finishButton).click();
    }
}
