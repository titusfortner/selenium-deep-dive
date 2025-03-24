package com.titusfortner.deep_dive.element;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ActionsTest extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void submitForm() {
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

    WebElement textInput = driver.findElement(By.id("my-text-id"));
    textInput.sendKeys("Attempt One");
    Assertions.assertEquals("Attempt One", textInput.getDomProperty("value"));

    textInput.clear();
    Assertions.assertEquals("", textInput.getDomProperty("value"));

    driver.findElement(By.id("my-check-1")).click();
    driver.findElement(By.tagName("form")).submit();

    WebElement message = driver.findElement(By.id("message"));
    Assertions.assertEquals("Received!", message.getText());
  }
}
