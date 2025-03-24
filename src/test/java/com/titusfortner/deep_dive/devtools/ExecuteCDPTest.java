package com.titusfortner.deep_dive.devtools;

import com.titusfortner.deep_dive.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chromium.HasCdp;

import java.util.HashMap;
import java.util.Map;

/**
 * This uses a synchronous driver endpoint to send commands. No event driven features are supported.
 */
public class ExecuteCDPTest extends TestBase {
    @BeforeEach
    public void setUp() {
        startChrome();
    }

  @Test
  public void setCookie() {
    Map<String, Object> cookie = new HashMap<>();
    cookie.put("name", "cheese");
    cookie.put("value", "gouda");
    cookie.put("domain", "www.selenium.dev");
    cookie.put("secure", true);
    ((HasCdp) driver).executeCdpCommand("Network.setCookie", cookie);

    driver.get("https://www.selenium.dev");
    Cookie cheese = driver.manage().getCookieNamed("cheese");
    Assertions.assertEquals("gouda", cheese.getValue());
  }

  // Most geolocation is IP address based, so this might not do what you expect
  @Test
  public void timeZoneExecuteCDP() {
    Map<String, Object> timezoneInfo = new HashMap<>();
    timezoneInfo.put("timezoneId", "Pacific/Honolulu");

    ((HasCdp) driver).executeCdpCommand("Emulation.setTimezoneOverride", timezoneInfo);

    driver.get("https://whatismytimezone.com/");
    String articleText = driver.findElement(By.tagName("article")).getText();
    Assertions.assertTrue(articleText.contains("GMT-1000"));
  }
}
