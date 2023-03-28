package test.java.com.titusfortner.deep_dive.demo.pages;

import org.openqa.selenium.By;
import test.java.com.titusfortner.deep_dive.demo.Browser;
import test.java.com.titusfortner.deep_dive.demo.elements.Element;

public class FinishPage extends BasePage {
    public static final String URL = "https://www.saucedemo.com/checkout-complete.html";

    private final Element completeText = browser.getElement(By.className("complete-text"));

    public FinishPage(Browser browser) {
        super(browser);
    }

    public boolean isComplete() {
        return completeText.isDisplayed();
    }
}
