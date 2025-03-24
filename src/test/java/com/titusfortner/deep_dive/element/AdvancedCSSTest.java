package com.titusfortner.deep_dive.element;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class AdvancedCSSTest extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void cssHierarchy() {
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

    // descendant --> text input
    driver.findElement(By.cssSelector(("form .form-control")));

    // direct descendant --> text input
    driver.findElement(By.cssSelector(("label > .form-control")));

    // first child --> text field label
    driver.findElement(By.cssSelector((".form-label:first-child")));

    // third child --> text area label
    driver.findElement(By.cssSelector((".form-label:nth-child(3)")));

    // second child from end --> disabled input label
    driver.findElement(By.cssSelector(("input:nth-last-child(2)")));

    // last child --> only works if it is the last child regardless of locator
    Assertions.assertThrows(
        NoSuchElementException.class,
        () -> driver.findElement(By.cssSelector((".form-label:last-child"))));
    // last child --> this matches the last option in the first select
    driver.findElement(By.cssSelector(("option:last-child")));
  }

  @Test
  public void cssAttributeSubString() {
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

    // starts with --> text field
    driver.findElement(By.cssSelector(("[id^=my-t]")));

    // ends with --> password field
    driver.findElement(By.cssSelector(("[name$=word]")));

    // contains --> submit button
    driver.findElement(By.cssSelector(("[type*=ubm]")));
  }

  @Test
  public void cssSiblings() {
    driver.get("https://www.selenium.dev/selenium/web/web-form.html");

    // next sibling --> div wrapper for link
    driver.findElement(By.cssSelector(("label + div")));

    // subsequent sibling --> submit button
    driver.findElement(By.cssSelector(("label ~ [type=submit]")));
  }
}
