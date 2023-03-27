package test.java.com.titusfortner.deep_dive.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class KeyboardTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void keyUpAndDown() {
        driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");

        new Actions(driver)
                .keyDown(Keys.SHIFT)
                .keyDown("a")
                .keyUp("a")
                .keyUp(Keys.SHIFT)
                .keyDown("b")
                .keyUp("b")
                .keyDown("c")
                .keyUp("c")
                .perform();

        WebElement textField = driver.findElement(By.id("textInput"));
        Assertions.assertEquals("Abc", textField.getAttribute("value"));
    }

    @Test
    public void sendKeys() {
        driver.get("https://www.selenium.dev/selenium/web/single_text_input.html");

        new Actions(driver)
                .keyDown(Keys.SHIFT)
                .sendKeys("abc")
                .keyUp(Keys.SHIFT)
                .perform();

        WebElement textField = driver.findElement(By.id("textInput"));
        Assertions.assertEquals("ABC", textField.getAttribute("value"));
    }
}
