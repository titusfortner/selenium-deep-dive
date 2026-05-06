package com.titusfortner.deep_dive.solutions.bidi;

import com.titusfortner.deep_dive.TestBase;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NytSubscribeAssetsSolution extends TestBase {

  private static final Set<String> NYT_DOMAINS = Set.of("nytimes.com", "nyt.com", "nytimg.com");

  @BeforeEach
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.enableBiDi();
    options.setImplicitWaitTimeout(Duration.ofSeconds(10));
    startChrome(options);
  }

  @Test
  void listThirdPartyAssetsLoadedBySubscribeButton() {
    CopyOnWriteArrayList<URI> seen = new CopyOnWriteArrayList<>();
    driver.get("https://www.nytimes.com");

    ((RemoteWebDriver) driver)
        .network()
        .addRequestHandler(
            uri -> {
              seen.add(uri);
              return false;
            },
            req -> req);

    driver.findElement(By.cssSelector("[data-testid=subscribe-button]")).click();

    List<String> thirdPartyHosts =
        seen.stream()
            .map(URI::getHost)
            .filter(h -> h != null)
            .filter(h -> NYT_DOMAINS.stream().noneMatch(h::endsWith))
            .distinct()
            .sorted()
            .collect(Collectors.toList());

    System.out.println("Third-party hosts loaded:");
    thirdPartyHosts.forEach(h -> System.out.println("  " + h));

    Assertions.assertTrue(
        thirdPartyHosts.size() >= 3,
        "expected several third-party hosts, found " + thirdPartyHosts.size());
  }
}
