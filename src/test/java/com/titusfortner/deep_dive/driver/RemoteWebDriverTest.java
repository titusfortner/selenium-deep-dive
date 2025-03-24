package com.titusfortner.deep_dive.driver;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;

public class RemoteWebDriverTest extends TestBase {
  public RemoteWebDriverTest() {}

  @BeforeAll
  public static void setup() {
    startGrid();
  }

  @Test
  public void localHostByDefault() {
    driver = new RemoteWebDriver(new ChromeOptions());
  }

  @Test
  public void useUrl() {
    driver = new RemoteWebDriver(gridUrl, new ChromeOptions());
  }

  @Test
  public void useCommandExecutor() {
    HttpCommandExecutor executor = new HttpCommandExecutor(gridUrl);
    driver = new RemoteWebDriver(executor, new ChromeOptions());
  }

  @Test
  public void useClientConfig() {
    ClientConfig config = ClientConfig.defaultConfig().baseUrl(gridUrl);
    HttpCommandExecutor executor = new HttpCommandExecutor(config);
    driver = new RemoteWebDriver(executor, new ChromeOptions());
  }
}
