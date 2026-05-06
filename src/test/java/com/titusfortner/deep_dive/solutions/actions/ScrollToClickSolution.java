package com.titusfortner.deep_dive.solutions.actions;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;

import java.time.Duration;

public class ScrollToClickSolution extends TestBase {

  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void clickTreeWalkerCode() {
    driver.get("https://developer.mozilla.org/en-US/docs/Web/API/Element");
    WebElement element = driver.findElement(By.xpath("//code[normalize-space()='TreeWalker']"));

    new Actions(driver).scrollToElement(element).click(element).perform();

    Assertions.assertEquals(
        "https://developer.mozilla.org/en-US/docs/Web/API/TreeWalker", driver.getCurrentUrl());
  }

  @Test
  void clickAriaValueMax() {
    driver.get("https://developer.mozilla.org/en-US/docs/Web/API/Element");
    WebElement element = driver.findElement(By.cssSelector("#element\\.ariavaluemax a"));

    new Actions(driver)
        .scrollFromOrigin(WheelInput.ScrollOrigin.fromElement(element), 0, 20)
        .click(element)
        .perform();

    Assertions.assertEquals(
        "https://developer.mozilla.org/en-US/docs/Web/API/Element/ariaValueMax",
        driver.getCurrentUrl());
  }
}
