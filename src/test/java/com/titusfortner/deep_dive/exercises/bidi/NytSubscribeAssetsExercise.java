package com.titusfortner.deep_dive.exercises.bidi;

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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * EXERCISE — What does NYT load from third parties when you click "Subscribe"?
 *
 * Real privacy/audit pattern: navigate to a major site, do one user action,
 * find out which OTHER companies got pinged as a side effect.
 *
 * GOAL
 *   1. Navigate to https://www.nytimes.com.
 *   2. Click the element with data-testid="subscribe-button".
 *   3. Capture every HTTP request the browser makes during the whole flow.
 *   4. Print the unique non-NYT hosts (sorted) so you can see who's loaded.
 *
 * HINT
 *   ((RemoteWebDriver) driver).network().addRequestHandler(...) BEFORE
 *   navigating. The handler must return the request unchanged. Capture
 *   URIs into a thread-safe collection (CopyOnWriteArrayList).
 *
 * SUCCESS
 *   At least 3 unique third-party hosts captured. Print them — Google,
 *   Doubleclick, Datadog, LiveRamp, etc. The list is depressing.
 *
 * NOTE
 *   If nytimes.com has changed and data-testid="subscribe-button" is gone,
 *   inspect the page and pick any header link instead — the exercise is
 *   about the audit pattern, not that exact button.
 */
public class NytSubscribeAssetsExercise extends TestBase {

  private static final Set<String> NYT_DOMAINS = Set.of("nytimes.com", "nyt.com", "nytimg.com");

  @BeforeEach
  public void setup() {
    ChromeOptions options = new ChromeOptions();
    options.enableBiDi();
    options.setPageLoadTimeout(Duration.ofSeconds(30));
    startChrome(options);
  }

  @Test
  void listThirdPartyAssetsLoadedBySubscribeButton() {
    CopyOnWriteArrayList<URI> seen = new CopyOnWriteArrayList<>();

    // TODO register a request handler that adds every requested URI to
    //   `seen` and returns the request unchanged.

    driver.get("https://www.nytimes.com");
    new WebDriverWait(driver, Duration.ofSeconds(15))
        .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid=subscribe-button]")))
        .click();

    List<String> thirdPartyHosts = seen.stream()
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
