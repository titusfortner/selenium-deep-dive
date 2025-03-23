package com.titusfortner.deep_dive.capabilities;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;

public class AlertsTest extends TestBase {
  @Test
  public void dismiss() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
    startChrome(chromeOptions);

    openConfirmAlert();

    String result = driver.findElement(By.id("result")).getText();
    Assertions.assertEquals("You clicked: Cancel", result);
  }

  @Test
  public void accept() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
    startChrome(chromeOptions);

    openConfirmAlert();

    String result = driver.findElement(By.id("result")).getText();
    Assertions.assertEquals("You clicked: Ok", result);
  }

  @Test
  public void dismissAndNotify() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
    startChrome(chromeOptions);

    openConfirmAlert();

    Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());

    String result = driver.findElement(By.id("result")).getText();
    Assertions.assertEquals("You clicked: Cancel", result);
  }

  @Test
  public void acceptAndNotify() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY);
    startChrome(chromeOptions);

    openConfirmAlert();

    Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());

    String result = driver.findElement(By.id("result")).getText();
    Assertions.assertEquals("You clicked: Ok", result);
  }

  @Test
  public void ignore() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
    startChrome(chromeOptions);

    openConfirmAlert();

    Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());
    Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());
    Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());
  }

  private void openConfirmAlert() {
    driver.get("https://the-internet.herokuapp.com/javascript_alerts");
    WebElement confirmButton = driver.findElements(By.tagName("button")).get(1);
    confirmButton.click();
  }
}
