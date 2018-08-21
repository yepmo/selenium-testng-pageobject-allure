package common.utility;

import static common.utility.WebDriverHolder.getDriver;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.By.xpath;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import ru.yandex.qatools.allure.annotations.*;

public class Waiters {

  protected static Wait<WebDriver> getFluentWait(){
    Integer fluentWaitInterval = 60;
    Wait<WebDriver> fluentWait = new FluentWait<>(getDriver())
        .withTimeout(Duration.ofSeconds(100))
        .pollingEvery(Duration.ofMillis(600))
        .ignoring(NoSuchElementException.class);
    return fluentWait;
  }

  protected static Wait<WebDriver> getCustomFluentWait(Integer waitingTime) {
    Wait<WebDriver> fluentWait = new FluentWait<>(getDriver())
        .withTimeout(Duration.ofSeconds(waitingTime))
        .pollingEvery(Duration.ofMillis(600))
        .ignoring(NoSuchElementException.class);
    return fluentWait;
  }

  @Step
  public static void waitUntilElementVisible(String locator) {
    getFluentWait().until(ExpectedConditions.visibilityOfElementLocated(xpath(locator)));
  }

  @Step
  public static void waitUntilElementInvisible(String locator) {
    getFluentWait().until(ExpectedConditions.invisibilityOfElementLocated(xpath(locator)));
  }

  @Step
  public static void waitUntilTextWillBePresent(String locator, String text) {
    getFluentWait().until(ExpectedConditions.textToBePresentInElementLocated(xpath(locator), text));
  }

  @Step
  public static void waitUntilTextWillBeInvisible(String locator, String text) {
    getFluentWait().until(ExpectedConditions.invisibilityOfElementWithText(xpath(locator), text));
  }

  @Step
  static void waitUntilElementClickable(String locator) {
    getFluentWait().until(ExpectedConditions.elementToBeClickable(xpath(locator)));
  }

  @Step
  static void waitUntilValueWillBePresentInElement(String locator, String text) {
    getFluentWait().until(ExpectedConditions.textToBePresentInElementValue(xpath(locator), text));
  }

}
