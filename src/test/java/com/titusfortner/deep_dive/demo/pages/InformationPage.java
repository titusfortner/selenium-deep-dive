package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import test.java.com.titusfortner.deep_dive.demo.Browser;
import test.java.com.titusfortner.deep_dive.demo.data.Person;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;
import test.java.com.titusfortner.deep_dive.demo.elements.ElementList;

import java.util.function.Function;

public class InformationPage extends BasePage {
    public static final String URL = "https://www.saucedemo.com/checkout-step-one.html";

    private final Element firstNameElement = browser.getElement(By.cssSelector("input[data-test='firstName']"));
    private final Element lastNameElement = browser.getElement(By.cssSelector("input[data-test='lastName']"));
    private final Element postalCodeElement = browser.getElement(By.cssSelector("input[data-test='postalCode']"));
    private final Element continueButton = browser.getElement(By.cssSelector("input[data-test='continue']"));
    private final ElementList errorElements = browser.getElements(By.cssSelector("[data-test=error]"));

    public InformationPage(Browser browser) {
        super(browser);
    }

    public void addInformationSuccessfully() {
        addInformationSuccessfully(new Person());
    }

    public void addInformationSuccessfully(Person person) {
        firstNameElement.sendKeys(person.getFirstName());
        lastNameElement.sendKeys(person.getLastName());
        postalCodeElement.sendKeys(person.getPostalCode());
        continueButton.click();

        try {
            browser.waitUntil(() -> !URL.equals(browser.getCurrentUrl()));
        } catch (TimeoutException ex) {
            String additional = errorElements.isEmpty() ? "" : " found error: " + errorElements.getFirst().getText();
            throw new PageValidationException("Information not submitted;" + additional);
        }
    }
}
