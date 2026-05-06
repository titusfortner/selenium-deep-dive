package com.titusfortner.deep_dive.exercises.element;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * EXERCISE 4 - Navigate through nested shadow DOM.
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
public class ShadowDOMExercise extends TestBase {
  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void navigateToEnergyPage() {
    driver.get("https://demo.home-assistant.io/#/lovelace/home");

    // TODO: Click the energy icon in the left menu.

    Assertions.assertEquals(
        "https://demo.home-assistant.io/#/energy/overview", driver.getCurrentUrl());
  }
}
