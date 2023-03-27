package test.java.com.titusfortner.deep_dive.page_factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class PageFactoryPage {
    private final WebDriver driver;
    WebDriverWait wait;

    public PageFactoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="my-text-id")
    WebElement textTextField;

    @FindBy(name="my-password")
    WebElement passwordTextField;

    @FindBy(tagName = "button")
    WebElement submitButton;

    public void submitForm(String text, String password) {
        textTextField.sendKeys(text);
        passwordTextField.sendKeys(password);
        submitButton.click();
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void sendKeys(WebElement element, String keys) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(keys);
    }

    public void synchronizedSubmitForm(String text, String password) {
        sendKeys(textTextField, text);
        sendKeys(passwordTextField, password);
        click(submitButton);
    }
}
