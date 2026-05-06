package com.titusfortner.deep_dive.solutions.element;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchShadowRootException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/*
 * EXERCISE Navigate through nested shadow DOM.
 *
 * Home Assistant is built from web components. The Energy menu item is not
 * available from the top-level document because it lives several shadow roots
 * deep. Use `getShadowRoot()` to move from each shadow host into its shadow DOM
 * until you can click the Energy item in the sidebar.
 *
 * TASK
 *   1. Open the demo Home Assistant Lovelace page.
 *   2. Locate each shadow host, then call `getShadowRoot()` on it.
 *   3. Click the Energy sidebar item.
 *   4. Verify the browser is on the Energy overview page.
 *
 * HINT
 *   SearchContext shadow = element.getShadowRoot();
 *   WebElement nestedHost = shadow.findElement(By.cssSelector("..."));
 *
 * SUCCESS
 *   The final assertion passes with this URL:
 *   https://demo.home-assistant.io/#/energy/overview
 */
public class ShadowDOMSolution extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void navigateToEnergyPage() {
    driver.get("https://demo.home-assistant.io/#/lovelace/home");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    wait.ignoring(NoSuchShadowRootException.class);

    SearchContext shadow1 =
        wait.until(d -> driver.findElement(By.cssSelector("ha-demo")).getShadowRoot());
    SearchContext shadow2 =
        shadow1.findElement(By.cssSelector("home-assistant-main")).getShadowRoot();
    SearchContext shadow3 = shadow2.findElement(By.cssSelector("ha-sidebar")).getShadowRoot();
    shadow3.findElement(By.cssSelector("#sidebar-panel-energy")).click();

    Assertions.assertEquals("https://demo.home-assistant.io/#/energy", driver.getCurrentUrl());
  }
}
