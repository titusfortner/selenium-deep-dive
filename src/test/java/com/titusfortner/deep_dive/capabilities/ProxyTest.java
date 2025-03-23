package com.titusfortner.deep_dive.capabilities;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class ProxyTest extends TestBase {

  public Proxy getProxy() {
    Proxy proxy = new Proxy();
    proxy.setProxyType(Proxy.ProxyType.MANUAL);
    proxy.setHttpProxy("user:password@host:port");
    proxy.setSslProxy("user:password@host:port");
    return proxy;
  }

  /** Does not accept authentication credentials with proxy */
  @Test
  public void chromeDoesNotCheckProxy() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.setProxy(getProxy());

    startChrome(chromeOptions);
  }

  @Test
  public void firefoxDoesNotAcceptBadProxy() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setProxy(getProxy());

    Assertions.assertThrows(SessionNotCreatedException.class, () -> startFirefox(firefoxOptions));
  }
}
