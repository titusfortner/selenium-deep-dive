package test.java.com.titusfortner.deep_dive.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class AdvancedCSSTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void cssHierarchy() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        // descendant --> text input
        driver.findElement(By.cssSelector(("form .form-control")));

        // direct descendant --> text input
        driver.findElement(By.cssSelector(("label > .form-control")));

        // first child --> text field label
        driver.findElement(By.cssSelector((".form-label:first-child")));

        // last child --> read only input label
        driver.findElement(By.cssSelector((".form-label:last-child")));

        // third child --> text area label
        driver.findElement(By.cssSelector((".form-label:nth-child(3)")));

        // second child from end --> disabled input label
        driver.findElement(By.cssSelector(("input:nth-last-child(2)")));
    }

    @Test
    public void cssAttributeSubString() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        // starts with --> text field
        driver.findElement(By.cssSelector(("[id^=my-t]")));

        // ends with --> password field
        driver.findElement(By.cssSelector(("[id$=word]")));

        // contains --> submit button
        driver.findElement(By.cssSelector(("[type*=ubm]")));
    }

    @Test
    public void cssSiblings() {
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        // next sibling --> div wrapper for link
        driver.findElement(By.cssSelector(("label + div")));

        // subsequent sibling --> submit button
        driver.findElement(By.cssSelector(("label ~ [type=submit]")));
    }
}
