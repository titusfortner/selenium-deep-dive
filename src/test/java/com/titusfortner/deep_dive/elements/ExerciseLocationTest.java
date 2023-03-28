package test.java.com.titusfortner.deep_dive.elements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

public class ExerciseLocationTest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void locateZipCodeField() {
        driver.get("https://www.saucedemo.com/v1/checkout-step-one.html");

        String cssDescendant = "";
        String cssDirectChild = "";
        String cssLastChild = "";
        String cssChildFromEnd = "";
        String cssChildFromBeginning = "";
        String cssAttributeStartsWith = "";
        String cssAttributeEndsWith = "";
        String cssAttributeContains = "";
        String cssNextSibling = "";
        String cssSubsequentSibling = "";




        // IGNORE THE DETAILS OF THE ASSERTIONS BELOW
        // ALL YOU NEED TO KNOW IS THAT IF YOU RUN THIS TEST
        // WITH THE RIGHT VALUES ABOVE IT WILL PASS

        WebElement zipCode = driver.findElement(By.id("postal-code"));

        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssDescendant)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssDirectChild)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssLastChild)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssChildFromEnd)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssChildFromBeginning)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssAttributeStartsWith)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssAttributeEndsWith)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssAttributeContains)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssNextSibling)));
        Assertions.assertEquals(zipCode, driver.findElement(By.cssSelector(cssSubsequentSibling)));

        Assertions.assertTrue(cssDescendant.contains(" "));
        Assertions.assertTrue(cssDirectChild.contains(" > "));
        Assertions.assertTrue(cssLastChild.contains(":last"));
        Assertions.assertTrue(cssChildFromEnd.contains(":nth-l"));
        Assertions.assertTrue(cssChildFromBeginning.contains(":nth-c"));
        Assertions.assertTrue(cssAttributeStartsWith.contains("^="));
        Assertions.assertTrue(cssAttributeEndsWith.contains("$="));
        Assertions.assertTrue(cssAttributeContains.contains("*="));
        Assertions.assertTrue(cssNextSibling.contains(" + "));
        Assertions.assertTrue(cssSubsequentSibling.contains(" ~ "));
    }

}
