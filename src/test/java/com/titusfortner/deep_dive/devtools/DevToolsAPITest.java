package test.java.com.titusfortner.deep_dive.devtools;

import com.google.common.net.MediaType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.Route;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.net.URI;
import java.util.function.Predicate;

/**
 * This API is the future
 * No Direct references to DevTools or creating a devtools session
 * Currently implemented with DevTools, but will be replaced with BiDi when available
 */
public class DevToolsAPITest extends BaseTestChrome {
    @BeforeEach
    public void setup() {
        startDriver();
    }

    @Test
    public void basicAuthAPI() {

        Predicate<URI> uriPredicate = uri -> uri.getHost().contains("herokuapp.com");
        ((HasAuthentication) driver).register(uriPredicate, UsernameAndPassword.of("admin", "admin"));

        driver.get("https://the-internet.herokuapp.com/basic_auth");

        String text = driver.findElement(By.tagName("p")).getText();
        Assertions.assertEquals("Congratulations! You must have the proper credentials.", text);
    }

    @Test
    public void networkInterception() {
        Route route = Route.matching(req -> true)
                .to(() -> req -> new HttpResponse()
                        .setStatus(200)
                        .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                        .setContent(Contents.utf8String("Creamy, delicious cheese!")));

        try (NetworkInterceptor ignored = new NetworkInterceptor(driver, route)) {
            driver.get("https://example-sausages-site.com");
        }

        String body = driver.findElement(By.tagName("body")).getText();
        Assertions.assertEquals("Creamy, delicious cheese!", body);
    }
}
