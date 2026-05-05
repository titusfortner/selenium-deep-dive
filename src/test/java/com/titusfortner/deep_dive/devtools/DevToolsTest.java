package com.titusfortner.deep_dive.devtools;

import com.google.common.collect.ImmutableMap;
import com.titusfortner.deep_dive.TestBase;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v147.emulation.Emulation;
import org.openqa.selenium.devtools.v147.network.Network;
import org.openqa.selenium.devtools.v147.network.model.Headers;

/**
 * Essentially wrapping executeCdpCommand in a nicer interface Still relies on ChromeDevTools Still
 * going away when BiDi spec arrives
 */
public class DevToolsTest extends TestBase {
  DevTools devTools;

  @BeforeEach
  public void setup() {
    startChrome();
    devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
  }

  /** Override the timezone in network emulation */
  @Test
  public void timeZone() {
    // This uses the idealized domains
    devTools.send(Emulation.setTimezoneOverride("Pacific/Honolulu"));

    driver.get("https://whatismytimezone.com/");
    String articleText = driver.findElement(By.tagName("article")).getText();
    Assertions.assertTrue(articleText.contains("GMT-1000"));
  }

  /** Use Basic Authentication Important because no longer an option in URL */
  @Test
  public void basicAuth() {
    // devTools.send(Network.enable(Optional.of(100000), Optional.of(100000),
    // Optional.of(100000)));
    devTools.send(new Command<>("Network.enable", new HashMap<>()));

    String encodedAuth = Base64.getEncoder().encodeToString("admin:admin".getBytes());
    Map<String, Object> headers = ImmutableMap.of("Authorization", "Basic " + encodedAuth);
    devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

    driver.get("https://the-internet.herokuapp.com/basic_auth");

    Assertions.assertEquals(
        "Congratulations! You must have the proper credentials.",
        driver.findElement(By.tagName("p")).getText());
  }
}
