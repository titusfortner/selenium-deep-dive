package test.java.com.titusfortner.deep_dive.capabilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.util.logging.Level;

public class ProxyTest  extends BaseTest {
    @BeforeEach
    public void setLogging() {
        seleniumLogger.setLevel(Level.FINE);
    }

    public Proxy getProxy() {
        Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setHttpProxy("user:password@host:port");
        proxy.setSslProxy("user:password@host:port");
        return proxy;
    }

    /**
     * Does not accept authentication credentials with proxy
     */
    @Test
    public void chromeDoesNotCheckProxy() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setProxy(getProxy());

        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    public void firefoxDoesNotAcceptBadProxy() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProxy(getProxy());

        Assertions.assertThrows(SessionNotCreatedException.class, () ->
                driver = new FirefoxDriver(firefoxOptions)
        );
    }
}
