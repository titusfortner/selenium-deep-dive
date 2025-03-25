package com.titusfortner.deep_dive.support;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PageFactoryTest extends TestBase {

  @BeforeEach
  public void setup() {
    startChrome();
  }

  /** 10 Commands */
  @Test
  public void submitForm() {
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

    PageFactoryPage page = new PageFactoryPage(driver);
    page.submitForm("name", "password");

    WebElement message = driver.findElement(By.id("message"));
    Assertions.assertEquals("Received!", message.getText());
  }

  /** Same result; 22 Commands */
  @Test
  public void submitSynchronizedForm() {
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

    PageFactoryPage page = new PageFactoryPage(driver);
    page.synchronizedSubmitForm("name", "password");

    WebElement message = driver.findElement(By.id("message"));
    Assertions.assertEquals("Received!", message.getText());
  }
}
