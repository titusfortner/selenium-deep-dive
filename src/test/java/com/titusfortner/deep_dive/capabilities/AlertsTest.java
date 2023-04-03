package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class AlertsTest extends BaseTestChrome {
    public void openConfirmAlert() {
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        WebElement confirmButton = driver.findElements(By.tagName("button")).get(1);
        confirmButton.click();
    }

    @Test
    public void dismiss() {
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
        driver = new ChromeDriver(chromeOptions);

        openConfirmAlert();

        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals("You clicked: Cancel", result);
    }

    @Test
    public void accept() {
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        driver = new ChromeDriver(chromeOptions);

        openConfirmAlert();

        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals("You clicked: Ok", result);
    }

    @Test
    public void dismissAndNotify() {
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
        driver = new ChromeDriver(chromeOptions);

        openConfirmAlert();

        Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());

        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals("You clicked: Cancel", result);
    }

    @Test
    public void acceptAndNotify() {
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY);
        driver = new ChromeDriver(chromeOptions);

        openConfirmAlert();

        Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());

        String result = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals("You clicked: Ok", result);
    }

    @Test
    public void ignore() {
        chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        driver = new ChromeDriver(chromeOptions);

        openConfirmAlert();

        Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());
        Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());
        Assertions.assertThrows(UnhandledAlertException.class, () -> driver.getTitle());
    }
}
