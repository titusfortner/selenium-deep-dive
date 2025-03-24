package com.titusfortner.deep_dive.element;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class ShadowDOMTest extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void shadowRoot() {
    driver.get("http://watir.com/examples/shadow_dom.html");

    WebElement shadowHost = driver.findElement(By.cssSelector("#shadow_host"));
    SearchContext shadowRoot = shadowHost.getShadowRoot();
    WebElement shadowContent = shadowRoot.findElement(By.cssSelector("#shadow_content"));

    Assertions.assertEquals("some text", shadowContent.getText());
  }
}
