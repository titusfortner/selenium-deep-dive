package com.titusfortner.deep_dive.exercises.actions;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;

/*
 * EXERCISE — Make this click work.
 *
 * MDN's API reference is a long page with anchors to every property. The
 * click below "succeeds" — Selenium auto-scrolls and reports no error —
 * but the actual click lands on MDN's sticky header instead of the link,
 * so the URL never changes and the assertion fails. Real-world snag.
 *
 * GOAL
 *   Make the assertion pass. The element exists, the locator is correct.
 *   Only the layout is fighting you.
 *
 * HINT
 *   Two reliable approaches:
 *     - JavascriptExecutor:
 *         executeScript("arguments[0].scrollIntoView({block:'center'});", element);
 *     - Actions API: scrollToElement(element), maybe with a follow-up
 *       scrollByAmount to get past the sticky header.
 *
 * SUCCESS
 *   driver.getCurrentUrl() ends with /Element/ariaValueMax after the click.
 */
public class ScrollToClickExercise extends TestBase {

  @BeforeEach
  public void setup() {
    startChrome();
  }

  @Test
  public void clickTreeWalkerCode() {
    driver.get("https://developer.mozilla.org/en-US/docs/Web/API/Element");

    // TODO: Click on Tree Walker item in Left Menu

    Assertions.assertEquals(
            "https://developer.mozilla.org/en-US/docs/Web/API/TreeWalker", driver.getCurrentUrl());
  }

  @Test
  void clickAriaValueMax() {
    driver.get("https://developer.mozilla.org/en-US/docs/Web/API/Element");

    // TODO: Click on Aria Value Max item in main body

    Assertions.assertEquals(
        "https://developer.mozilla.org/en-US/docs/Web/API/Element/ariaValueMax",
        driver.getCurrentUrl());
  }
}
