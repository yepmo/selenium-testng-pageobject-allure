package com.template.project.web.utils;

import static com.template.project.common.ConfigFileReaderUtils.getValueFromJsonConfigFile;
import static com.template.project.web.utils.WebDriverHolder.getDriver;

import com.template.project.common.Cipher;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import javax.naming.ConfigurationException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {

  private static String HUB;
  private static String HUB_IE;
  private static String STANDALONE_CHROME;

  private static Boolean HEADLESS;
  private static Boolean INSECURE_CERTIFICATE;
  private static Boolean NO_SANDBOX;
  private static Boolean INCOGNITO;

  static {
    try {
      HUB = readFromSeleniumConfigFile("hub");
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }

    try {
      HEADLESS = Boolean.parseBoolean(readFromSeleniumConfigFile("headless"));
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    try {
      INSECURE_CERTIFICATE =
          Boolean.parseBoolean(readFromSeleniumConfigFile("insecure_certificate"));
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    try {
      NO_SANDBOX = Boolean.parseBoolean(readFromSeleniumConfigFile("no_sandbox"));
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
    try {
      INCOGNITO = Boolean.parseBoolean(readFromSeleniumConfigFile("incognito"));
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
  }

  /** This method is to setup webDriverManager for different browser */
  private static void setupWebDriverManager() {
    WebDriverManager.chromedriver().setup();
    WebDriverManager.firefoxdriver().setup();
    WebDriverManager.iedriver().setup();
    WebDriverManager.edgedriver().setup();
  }

  /**
   * Handle the known issue with WebDriverManager : HTTP response code 403 Reference:
   * https://github.com/bonigarcia/webdrivermanager#http-response-code-403
   *
   * @param tokenSecret tokenSecret to be set in settings.xml and passed as parameter
   */
  public static void handleWebDriverManagerAuthorizationIssue(String tokenSecret) {
    System.setProperty("wdm.gitHubTokenName", "test-automation");
    System.setProperty("wdm.gitHubTokenSecret", Cipher.decrypt(tokenSecret));
  }

  /**
   * Set Chrome browser capabilities.
   *
   * @param testName Used to identify the current test being run
   * @return A not null chrome driver
   * @throws MalformedURLException if the constant HUB is not a valid URL.
   */
  private static WebDriver getChromeBrowser(String testName) throws MalformedURLException {
    final ChromeOptions options = new ChromeOptions();
    options.setCapability("elementScrollBehavior", true);
    options.setCapability("name", testName);
    options.addArguments("--disable-dev-shm-usage");
    options.setHeadless(HEADLESS);
    options.setAcceptInsecureCerts(INSECURE_CERTIFICATE);
    if (NO_SANDBOX) options.addArguments("--no-sandbox");
    if (INCOGNITO) options.addArguments("--incognito");

    if (isRemote()) {
      setupWebDriverManager();
      if (isStandaloneChrome()) {
        return new RemoteWebDriver(new URL(STANDALONE_CHROME), options);
      } else {
        return new RemoteWebDriver(new URL(HUB), options);
      }
    } else {
      setupWebDriverManager();
      return new ChromeDriver(options);
    }
  }

  /**
   * Set Firefox browser capabilities.
   *
   * @param testName Used to identify the test being run
   * @return A valid instance
   * @throws MalformedURLException if the constant HUB is not a valid URL.
   */
  private static WebDriver getFirefoxBrowser(String testName) throws MalformedURLException {
    FirefoxOptions options = new FirefoxOptions();
    options.setCapability("name", testName);
    options.setCapability("moz:firefoxOptions", options);
    System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
    System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

    if (isRemote()) {
      setupWebDriverManager();
      return new RemoteWebDriver(new URL(HUB), options);
    } else {
      setupWebDriverManager();
      return new FirefoxDriver(options);
    }
  }

  /**
   * Check a system property to decide if we should use a remote web driver.
   *
   * @return True only if the property is correctly set.
   */
  private static boolean isRemote() {
    return Boolean.TRUE.equals(Boolean.valueOf(System.getProperty("remoteExecution")));
  }

  /**
   * Check a system property to decide if we should chrome from grid or node.
   *
   * @return True only if the property is correctly set.
   */
  private static boolean isStandaloneChrome() {
    return Boolean.TRUE.equals(Boolean.valueOf(System.getProperty("standaloneChrome")));
  }

  /**
   * Set IE browser capabilities.
   *
   * @param testName Used to identify the current test being run
   * @return A not null ie webdriver.
   * @throws MalformedURLException if the constant HUB_IE is not a valid URL.
   */
  private static WebDriver getInternetExplorerBrowser(String testName)
      throws MalformedURLException {
    InternetExplorerOptions options = new InternetExplorerOptions();
    options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
    options.setCapability(
        InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    options.setCapability("name", testName);
    options.setCapability("platform", Platform.WINDOWS);

    if (isRemote()) {
      setupWebDriverManager();
      ((RemoteWebDriver) getDriver()).setFileDetector(new LocalFileDetector());
      return new RemoteWebDriver(new URL(HUB_IE), options);
    } else {
      setupWebDriverManager();
      return new InternetExplorerDriver(options);
    }
  }

  /**
   * Set Edge browser capabilities.
   *
   * @param testName Used to identify the current test being run
   * @return A usable, not null edge driver.
   * @throws MalformedURLException if the constant HUB_IE is not a valid URL.
   */
  private static WebDriver getEdgeBrowser(String testName) throws MalformedURLException {
    EdgeOptions options = new EdgeOptions();
    options.setCapability("platform", Platform.ANY);
    options.setCapability("name", testName);
    if (isRemote()) {
      setupWebDriverManager();
      return new RemoteWebDriver(new URL(HUB_IE), options);
    } else {
      setupWebDriverManager();
      return new EdgeDriver();
    }
  }

  /**
   * This method returns the browser driver based on the browser name specified in the test suite
   * xml.
   *
   * @param browserName One of firefox, edge, iexplore or chrome.
   * @param testName Used to identify the current test being run
   * @return default is chrome
   * @throws MalformedURLException if the constant for the hub (HUB or HUB_IE) is not a valid URL.
   */
  static WebDriver getBrowser(String browserName, String testName) throws MalformedURLException {
    switch (browserName) {
      case "firefox":
        return getFirefoxBrowser(testName);
      case "edge":
        return getEdgeBrowser(testName);
      case "iexplore":
        return getInternetExplorerBrowser(testName);
      case "chrome":
      default:
        return getChromeBrowser(testName);
    }
  }

  private static String readFromSeleniumConfigFile(String propName) throws ConfigurationException {
    return getValueFromJsonConfigFile("selenium_config.json", propName);
  }
}
