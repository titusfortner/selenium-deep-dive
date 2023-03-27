package test.java.com.titusfortner.deep_dive.actions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.time.Duration;
import java.util.Collections;

public class DragDropTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void dragsToElement() {
        driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("droppable"));
        new Actions(driver)
                .dragAndDrop(draggable, droppable)
                .perform();

        Assertions.assertEquals("dropped", driver.findElement(By.id("drop-status")).getText());
    }

    @Test
    public void dragsByOffset() {
        driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

        WebElement draggable = driver.findElement(By.id("draggable"));
        Rectangle start = draggable.getRect();
        Rectangle finish = driver.findElement(By.id("droppable")).getRect();
        new Actions(driver)
                .dragAndDropBy(draggable,
                        finish.getX() - start.getX(),
                        finish.getY() - start.getY())
                .perform();

        Assertions.assertEquals("dropped", driver.findElement(By.id("drop-status")).getText());
    }
}
