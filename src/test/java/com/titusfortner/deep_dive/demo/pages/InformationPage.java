package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InformationPage {
    private final WebDriver driver;

    private final By firstNameElement = By.cssSelector("input[data-test='firstName']");
    private final By lastNameElement = By.cssSelector("input[data-test='lastName']");
    private final By postalCodeElement = By.cssSelector("input[data-test='postalCode']");
    private final By continueButton = By.cssSelector("input[data-test='continue']");

    public InformationPage(WebDriver driver) {
        this.driver = driver;
    }

    public CheckoutPage addInformation(String firstName, String lastName, String postalCode) {
        driver.findElement(firstNameElement).sendKeys(firstName);
        driver.findElement(lastNameElement).sendKeys(lastName);
        driver.findElement(postalCodeElement).sendKeys(postalCode);
        driver.findElement(continueButton).click();

        return new CheckoutPage(driver);
    }
}
