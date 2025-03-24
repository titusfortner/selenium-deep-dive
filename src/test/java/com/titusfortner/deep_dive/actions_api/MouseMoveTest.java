package com.titusfortner.deep_dive.actions_api;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class MouseMoveTest extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void hovers() {
    driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");

    WebElement hoverable = driver.findElement(By.id("hover"));
    new Actions(driver).moveToElement(hoverable).perform();

    Assertions.assertEquals("hovered", driver.findElement(By.id("move-status")).getText());
  }

  @Test
  public void moveByOffsetFromElement() {
    driver.get("https://www.selenium.dev/selenium/web/mouse_interaction.html");
    driver.manage().window().fullscreen();

    WebElement tracker = driver.findElement(By.id("mouse-tracker"));
    new Actions(driver).moveToElement(tracker, 8, 0).perform();

    String[] result = driver.findElement(By.id("relative-location")).getText().split(", ");
    Assertions.assertTrue(Math.abs(Integer.parseInt(result[0]) - 100 - 8) < 2);
  }
}
