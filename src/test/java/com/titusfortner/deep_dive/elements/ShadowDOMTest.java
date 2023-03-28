package test.java.com.titusfortner.deep_dive.elements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class ShadowDOMTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
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
