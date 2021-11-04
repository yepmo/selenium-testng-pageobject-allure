package com.template.project.web.utils;

import static com.template.project.common.Logger.logInfo;
import static com.template.project.web.utils.BrowserFactory.getBrowser;

import java.net.MalformedURLException;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class WebDriverHolder {

  private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

  public static void setDriver(String browserName, String testName) {
    try {
      driver.set(getBrowser(browserName, testName));
      logInfo(String.format("Starting browser : %s", browserName));
    } catch (MalformedURLException e) {
      log.error("Unable to open {}", browserName, e);
    }
    getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
  }

  public static WebDriver getDriver() {
    return driver.get();
  }

  public static void tearDownBrowser() {
    logInfo("Closing Browser");
    if (getDriver() != null) {
      getDriver().quit();
      driver.remove();
    }
  }
}
