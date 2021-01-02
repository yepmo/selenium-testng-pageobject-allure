package com.template.project.common;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Properties;

import static com.template.project.web.utils.WebDriverHolder.getDriver;
import static org.testng.Reporter.log;

/** Adds content to allure reporting. */
public class Logger {

  static final org.apache.logging.log4j.Logger log = LogManager.getLogger(Logger.class);

  /**
   * This method generates log message in the console & the allure report.
   *
   * @param message Content of message to be added to allure report
   */
  @Step("INFO: {0}")
  public static void logInfo(String message) {
    if (null == message) {
      return;
    }
    log("INFO: " + message);
    log.info(message);
  }

  /**
   * This method generates warning log message in the console & the allure report.
   *
   * @param message Content of message to be added to allure report
   */
  @Step("WARN: {0}")
  public static void logWarn(String message) {
    if (null == message) {
      return;
    }
    log("WARN: " + message);
    log.warn(message);
  }

  /**
   * This method generates error log message in the console & the allure report.
   *
   * @param message Content of message to be added to allure report
   */
  @Step("ERROR: {0}")
  public static void logError(String message) {
    if (null == message) {
      return;
    }
    log("ERROR: " + message);
    log.error(message);
  }

  /**
   * This method generates a stacktrace log message in the console & the allure report.
   *
   * @param trace Content of message to be added to allure report
   */
  @Step("TRACE: {0}")
  public static void logTrace(StackTraceElement trace) {
    if (null == trace) {
      return;
    }
    log("TRACE:\n" + trace);
    log.trace(trace);
  }

  /**
   * Log payload of an Http Request in the allure report.
   *
   * @param message Content of message to be added to allure report
   */
  @Attachment(value = "request body :")
  public static String logRequest(String message) {
    if (null == message) {
      return "null request";
    }
    return message;
  }

  /**
   * Take and save a screen shot as an attachment to the allure report.
   *
   * @return the screenshot, which will be handled by the annotation
   */
  @Attachment(value = "page screenshot", type = "image/png")
  public static byte[] saveScreenshotPNG() {
    log.debug("Taking screenshot");
    return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
  }

  /**
   * Print the console message as a attachment to the allure report.
   *
   * @param message Text which is to be added to the allure report.
   * @return The {@code message}, unchanged.
   */
  @Attachment
  public static String saveTextLog(final String message) {
    log.debug("Saving text log to allure");
    return message;
  }

  /**
   * Attach the configured system properties to the allure log.
   *
   * @return Should generally not be used by calling code, this is consumed by allure.
   */
  @Attachment(value = "System Environment", type = "text/plain")
  public byte[] attachSystemProperties() {
    log.debug("Attaching system properties to allure report");
    final Properties props = System.getProperties();
    final StringBuilder result = new StringBuilder();
    for (final String prop : props.stringPropertyNames()) {
      if (prop.toLowerCase().contains("password")) {
        // Dont log things that look like passwords
        result.append(prop).append(" = ").append("********").append("\n");
      } else {
        result.append(prop).append(" = ").append(System.getProperty(prop)).append("\n");
      }
    }
    final String propertyBlock = result.toString();
    log.debug(propertyBlock);
    return propertyBlock.getBytes();
  }
}
