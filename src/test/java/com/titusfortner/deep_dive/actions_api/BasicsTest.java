package com.titusfortner.deep_dive.actions_api;

import com.titusfortner.deep_dive.TestBase;
import java.time.Duration;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BasicsTest extends TestBase {

  @Test
  public void basicActionsChain() {
    driver.get("https://titusfortner.com/examples/mouse_interaction.html");

    WebElement clickable = driver.findElement(By.id("clickable"));

    new Actions(driver)
        .moveToElement(clickable)
        .click()
        .keyDown(Keys.SHIFT)
        .sendKeys("abc")
        .keyUp(Keys.SHIFT)
        .perform();

    Assertions.assertEquals("ABC", clickable.getAttribute("value"));
  }

  @Test
  public void pauseBetweenActions() {
    driver.get("https://titusfortner.com/examples/mouse_interaction.html");

    WebElement hoverable = driver.findElement(By.id("hover"));

    new Actions(driver).moveToElement(hoverable).pause(Duration.ofMillis(500)).perform();

    Assertions.assertEquals("hovered", driver.findElement(By.id("move-status")).getText());
  }

  @Test
  public void resetInput() {
    driver.get("https://titusfortner.com/examples/mouse_interaction.html");

    WebElement clickable = driver.findElement(By.id("clickable"));

    new Actions(driver).click(clickable).keyDown(Keys.SHIFT).sendKeys("a").perform();

    ((RemoteWebDriver) driver).resetInputState();
    new Actions(driver).click(clickable).sendKeys("a").perform();

    String value = clickable.getAttribute("value");
    Assertions.assertEquals("Aa", value);
  }

  @Test
  public void advancedSequence() {
    PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "default");

    Sequence actions =
        new Sequence(mouse, 0)
            .addAction(mouse.createPointerDown(PointerInput.MouseButton.BACK.asArg()))
            .addAction(mouse.createPointerUp(PointerInput.MouseButton.BACK.asArg()));

    ((RemoteWebDriver) driver).perform(Collections.singletonList(actions));
  }
}
