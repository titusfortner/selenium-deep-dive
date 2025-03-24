package com.titusfortner.deep_dive.element;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

public class InformationTest extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void textInput() {
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

    WebElement element = driver.findElement(By.id("my-text-id"));
    element.sendKeys("Hi Mom");

    Assertions.assertTrue(element.isDisplayed());
    Assertions.assertTrue(element.isEnabled());
    Assertions.assertFalse(element.isSelected());
    Assertions.assertEquals("", element.getText());
    Assertions.assertEquals("input", element.getTagName());
    Assertions.assertEquals("myvalue", element.getAttribute("myprop"));
    Assertions.assertEquals("myvalue", element.getDomAttribute("myprop"));
    Assertions.assertEquals("Hi Mom", element.getDomProperty("value"));
    Assertions.assertEquals("400", element.getCssValue("font-weight"));
    Assertions.assertEquals(new Dimension(356, 38), element.getSize());
    Assertions.assertEquals(new Point(42, 88), element.getLocation());
  }
}
