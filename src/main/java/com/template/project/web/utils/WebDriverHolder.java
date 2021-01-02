package com.template.project.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static com.template.project.common.Logger.logInfo;
import static com.template.project.web.utils.BrowserFactory.getBrowser;

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
    getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
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
