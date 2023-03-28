package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FinishPage extends BasePage {
    public static final String URL = "https://www.saucedemo.com/checkout-complete.html";

    private final By completeText = By.className("complete-text");

    public FinishPage(WebDriver driver) {
        super(driver);
    }

    public boolean isComplete() {
        return driver.findElement(completeText).isDisplayed();
    }
}
