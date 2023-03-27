package test.java.com.titusfortner.deep_dive.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class MouseClickTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void clickAndHold() {
        driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

        WebElement clickable = driver.findElement(By.id("clickable"));
        new Actions(driver)
                .clickAndHold(clickable)
                .perform();

        Assertions.assertEquals("focused", driver.findElement(By.id("click-status")).getText());
    }

    @Test
    public void clickAndRelease() {
        driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

        WebElement clickable = driver.findElement(By.id("click"));
        new Actions(driver)
                .click(clickable)
                .perform();

        Assertions.assertTrue(driver.getCurrentUrl().contains("resultPage.html"));
    }

    @Test
    public void rightClick() {
        driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

        WebElement clickable = driver.findElement(By.id("clickable"));
        new Actions(driver)
                .contextClick(clickable)
                .perform();

        Assertions.assertEquals("context-clicked", driver.findElement(By.id("click-status")).getText());
    }

    @Test
    public void doubleClick() {
        driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

        WebElement clickable = driver.findElement(By.id("clickable"));
        new Actions(driver)
                .doubleClick(clickable)
                .perform();

        Assertions.assertEquals("double-clicked", driver.findElement(By.id("click-status")).getText());
    }
}
