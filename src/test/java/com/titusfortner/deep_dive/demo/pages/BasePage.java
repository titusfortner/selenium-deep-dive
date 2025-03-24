package com.titusfortner.deep_dive.demo.pages;

import com.titusfortner.deep_dive.demo.Browser;

public abstract class BasePage {
  protected Browser browser;

  public BasePage(Browser browser) {
    this.browser = browser;
  }
}
