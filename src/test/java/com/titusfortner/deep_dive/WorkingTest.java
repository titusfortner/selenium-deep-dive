package com.titusfortner.deep_dive;

import org.junit.jupiter.api.Test;

public class WorkingTest extends TestBase {
  /** Verify that Selenium can drive each of these browsers */
  @Test
  public void validateChrome() {
    startChrome();
  }

  @Test
  public void validateFirefox() {
    startFirefox();
  }

  @Test
  public void validateEdge() {
    startEdge();
  }
}
