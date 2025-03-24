package com.titusfortner.deep_dive.support;

import java.time.Duration;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExpectedConditionTest extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
    driver.get("http://watir.com/examples/wait.html");
    driver.findElement(By.id("show_bar")).click();
  }

  @Test
  public void waitForClickableCondition() {
    WebElement bar = driver.findElement(By.id("bar"));

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(bar));

    bar.click();
  }

  @Test
  public void waitForConditionWithFunction() {
    WebElement bar = driver.findElement(By.id("bar"));

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(driver -> bar.isDisplayed() && bar.isEnabled());

    bar.click();
  }

  @Test
  public void waitForClick() {
    WebElement bar = driver.findElement(By.id("bar"));

    WebDriverWait wait =
        (WebDriverWait)
            new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(ElementNotInteractableException.class);
    wait.until(
        driver -> {
          bar.click();
          return true;
        });
  }
}
