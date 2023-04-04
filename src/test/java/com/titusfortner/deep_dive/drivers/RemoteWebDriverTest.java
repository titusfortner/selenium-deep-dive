package test.java.com.titusfortner.deep_dive.drivers;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import test.java.com.titusfortner.deep_dive.BaseTest;

import java.net.MalformedURLException;
import java.net.URL;

public class RemoteWebDriverTest extends BaseTest {
    URL serverUrl = new URL("http://localhost:4444/");

    public RemoteWebDriverTest() throws MalformedURLException {
    }

    @Test
    public void localHostByDefault() {
        driver = new RemoteWebDriver(new ChromeOptions());
    }

    @Test
    public void useUrl() {
        driver = new RemoteWebDriver(serverUrl, new ChromeOptions());
    }

    @Test
    public void useCommandExecutor() {
        HttpCommandExecutor executor = new HttpCommandExecutor(serverUrl);
        driver = new RemoteWebDriver(executor, new ChromeOptions());
    }

    @Test
    public void useClientConfig() {
        ClientConfig config = ClientConfig.defaultConfig().baseUrl(serverUrl);
        HttpCommandExecutor executor = new HttpCommandExecutor(config);
        driver = new RemoteWebDriver(executor, new ChromeOptions());
    }
}
