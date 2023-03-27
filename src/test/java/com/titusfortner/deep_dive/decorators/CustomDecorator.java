package test.java.com.titusfortner.deep_dive.decorators;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.decorators.Decorated;
import org.openqa.selenium.support.decorators.DefaultDecorated;
import org.openqa.selenium.support.decorators.WebDriverDecorator;

import java.lang.reflect.Method;
public class CustomDecorator extends WebDriverDecorator<WebDriver> {

    @Override
    public Decorated<WebElement> createDecorated(WebElement original) {
        return new DefaultDecorated(original, this) {
            @Override
            public Object call(Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                if ("click".equals(methodName)) {
                    JavascriptExecutor executor = (JavascriptExecutor) getDecoratedDriver().getOriginal();
                    executor.executeScript("arguments[0].click()", getOriginal());
                    return null;
                } else {
                    return super.call(method, args);
                }
            }
        };
    }

}
