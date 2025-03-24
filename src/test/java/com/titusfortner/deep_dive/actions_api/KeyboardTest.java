package com.titusfortner.deep_dive.actions_api;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class KeyboardTest extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
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

    new Actions(driver).keyDown(Keys.SHIFT).sendKeys("abc").keyUp(Keys.SHIFT).perform();

    WebElement textField = driver.findElement(By.id("textInput"));
    Assertions.assertEquals("ABC", textField.getAttribute("value"));
  }
}
