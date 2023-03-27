package test.java.com.titusfortner.deep_dive.decorators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class DecoratorTest extends BaseTestChrome {

    @BeforeEach
    public void setup() {
        startDriver();
        driver.get("http://watir.com/examples/wait.html");
        driver.findElement(By.id("show_bar")).click();
    }

    @Test
    public void defaultErrors() {
        WebElement bar = driver.findElement(By.id("bar"));

        Assertions.assertThrows(ElementNotInteractableException.class, bar::click);
    }

    @Test
    public void customLoadableWait() {
        WebElement bar = new CustomLoadableComponent(driver, By.id("bar")).get().getElement();
        Assertions.assertDoesNotThrow(bar::click);

        Assertions.assertEquals("changed", bar.getText());
    }

    @Test
    public void automaticallyWaits() {
        WebDriverListener listener = new CustomDriverListener(driver);
        WebDriver decorated = new EventFiringDecorator<>(listener).decorate(driver);

        WebElement bar = decorated.findElement(By.id("bar"));
        Assertions.assertDoesNotThrow(bar::click);

        Assertions.assertEquals("changed", bar.getText());
    }

    @Test
    public void clicksAnyway() throws InterruptedException {
        WebDriver decorated = new CustomDecorator().decorate(driver);
        WebElement bar = decorated.findElement(By.id("bar"));
        Assertions.assertDoesNotThrow(bar::click);

        Assertions.assertEquals("", bar.getText());
        Thread.sleep(5000);
        Assertions.assertEquals("changed", bar.getText());
    }
}
