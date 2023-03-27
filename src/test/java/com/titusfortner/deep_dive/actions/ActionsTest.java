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

public class ActionsTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void resetInput() {
        driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

        WebElement clickable = driver.findElement(By.id("clickable"));
        Actions actions = new Actions(driver);
        actions.clickAndHold(clickable)
                .keyDown(Keys.SHIFT)
                .sendKeys("a")
                .perform();

        ((RemoteWebDriver) driver).resetInputState();

        actions.sendKeys("a").perform();
        Assertions.assertEquals("A", String.valueOf(clickable.getAttribute("value").charAt(0)));
        Assertions.assertEquals("a", String.valueOf(clickable.getAttribute("value").charAt(1)));
    }
}
