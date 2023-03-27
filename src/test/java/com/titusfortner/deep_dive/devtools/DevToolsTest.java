package test.java.com.titusfortner.deep_dive.devtools;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chromium.HasCdp;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v111.emulation.Emulation;
import org.openqa.selenium.devtools.v111.log.Log;
import org.openqa.selenium.devtools.v111.network.Network;
import org.openqa.selenium.devtools.v111.network.model.Headers;
import test.java.com.titusfortner.deep_dive.BaseTestChrome;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Essentially wrapping executeCdpCommand in a nicer interface
 * Still relies on ChromeDevTools
 * Still going away when BiDi spec arrives
 */
public class DevToolsTest extends BaseTestChrome {
    DevTools devTools;

    @BeforeEach
    public void setup() {
        startDriver();

        devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
    }

    @Test
    public void timeZoneExecuteCDP() {
        Map<String, Object> timezoneInfo = new HashMap<>();
        timezoneInfo.put("timezoneId", "Pacific/Honolulu");

        ((HasCdp) driver).executeCdpCommand("Emulation.setTimezoneOverride", timezoneInfo);

        driver.get("https://whatismytimezone.com/");
        String articleText = driver.findElement(By.tagName("article")).getText();
        Assertions.assertTrue(articleText.contains("GMT-1000"));
    }


    /**
     * Override the timezone in network emulation
     */
    @Test
    public void timeZone() {
        devTools.send(Emulation.setTimezoneOverride("Pacific/Honolulu"));

        driver.get("https://whatismytimezone.com/");
        String articleText = driver.findElement(By.tagName("article")).getText();
        Assertions.assertTrue(articleText.contains("GMT-1000"));
    }

    /**
     * Use Basic Authentication
     * Important because no longer an option in URL
     */
    @Test
    public void basicAuth() {
        devTools.send(new Command<>("Network.enable", new HashMap<>()));

        String encodeToString = Base64.getEncoder().encodeToString("admin:admin".getBytes());

        Map<String, Object> authorization = ImmutableMap.of("Authorization", "Basic " + encodeToString);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(authorization)));

        driver.get("https://the-internet.herokuapp.com/basic_auth");
        String text = driver.findElement(By.tagName("p")).getText();

        Assertions.assertEquals("Congratulations! You must have the proper credentials.", text);
    }

    @Test
    public void chromeConsoleLogs() {
        devTools.send(Log.enable());

        devTools.addListener(Log.entryAdded(),
                logEntry -> {
                    Assertions.assertTrue(logEntry.getText().contains("Failed to load resource"));
                    Assertions.assertEquals("error", logEntry.getLevel().toString());
                });
        driver.get("http://the-internet.herokuapp.com/broken_images");
    }

    @Test
    public void geoLocation() throws InterruptedException {
        Number latitude = 52.5043;
        Number longitude = 13.4501;

        devTools.send(Emulation.setGeolocationOverride(Optional.of(latitude),
                Optional.of(longitude),
                Optional.of(1)));

        driver.get("https://my-location.org/");
        Thread.sleep(5000);

        Assertions.assertEquals(latitude.toString(), driver.findElement(By.id("latitude")).getText());
        Assertions.assertEquals(longitude.toString(), driver.findElement(By.id("longitude")).getText());
    }
}
