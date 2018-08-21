package common.utility;

/**
 * Created by Parthiban.
 **/

import java.io.*;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import ru.yandex.qatools.allure.annotations.*;
import static common.utility.WebDriverHolder.getDriver;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.By.*;
import static org.testng.AssertJUnit.fail;
import static org.testng.Reporter.log;

@Slf4j
public class Helpers extends Waiters {

  private static Properties prop;
  private static String propValue;

  @Step("{0}")
  public static void logInfo(String message) {
    log(message);
    log.info(message);
  }

  @Step
  public static void switchToFrame(String locator, String locatorWithinFrame){
    WebElement mainFrame = getDriver().findElement(By.xpath(locator));
    getDriver().switchTo().frame(mainFrame);
    WebElement elementOnFrame = getDriver().findElement(By.xpath(locatorWithinFrame));
    assert elementOnFrame.isDisplayed();
    logInfo("Switched to expected frame");
  }

  @Step
  public static void switchBackFromFrame(String locatorOutsideFrame){
    getDriver().switchTo().defaultContent();
    WebElement elementOnFrame = getDriver().findElement(By.xpath(locatorOutsideFrame));
    assert elementOnFrame.isDisplayed();
    logInfo("Switched back from frame");
  }

  @Step
  public static String getFromPropertyFile(String propName) throws IOException {
    prop = new Properties();
    FileInputStream fis = new FileInputStream(
        "src/main/resources/sampleData.properties");
    prop.load(fis);
    propValue = prop.getProperty(propName);
    logInfo("Param - " + propName + " is : " + propValue);
    return propValue;
  }

  @Step
  static WebElement tryFindElement(String locator) {
    return getFluentWait().until(ExpectedConditions.presenceOfElementLocated(xpath(locator)));
  }

  @Step
  static List<WebElement> tryFindElements(String locator) {
    return getFluentWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(xpath(locator)));
  }

  @Step
  public void scrollUp() {
    JavascriptExecutor jse = (JavascriptExecutor) getDriver();
    jse.executeScript("window.scrollBy(0, -1000);");
    try {
      sleep(1500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Step
  public void scrollDown() {
    JavascriptExecutor jse = (JavascriptExecutor) getDriver();
    jse.executeScript("window.scrollBy(0,800)");
    try {
      sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Step
  public void scrollToElement(String locator) {
    Actions actions = new Actions(getDriver());
    actions.moveToElement(tryFindElement(locator)).perform();
  }

  @Step
  public void reload() {
    getDriver().navigate().refresh();
  }

  @Step
  public void clickElement(String locator) {
    waitUntilElementClickable(locator);
    tryFindElement(locator).click();
  }

  @Step
  public void clearField(String locator) {
    tryFindElement(locator).clear();
    waitUntilValueWillBePresentInElement(locator, "");
  }

  @Step
  public void clearAndFillInFieldWith(String locator, String text) {
    tryFindElement(locator).clear();
    waitUntilValueWillBePresentInElement(locator, "");
    tryFindElement(locator).sendKeys(text);
    waitUntilValueWillBePresentInElement(locator, text);
  }

  @Step
  public void fillInFieldWith(String locator, String text) {
    tryFindElement(locator).sendKeys(text);
    waitUntilValueWillBePresentInElement(locator, text);
  }

  @Step
  public static Boolean isElementPresent(String locator) {
    try {
      getFluentWait().until(ExpectedConditions.visibilityOfElementLocated(xpath(locator)));
      return true;
    } catch (TimeoutException ignored) {
      return false;
    }
  }

  @Step
  public void iShouldSee(String locator) {
    try {
      getFluentWait().until(ExpectedConditions.presenceOfElementLocated(xpath(locator)));
    } catch (Exception ex) {
      fail("Locator $locator is not found");
    }
  }

  @Step
  public void iShouldNotSee(String locator) {
    boolean elementExists = isElementPresent(locator);
    assert !elementExists;
  }

  @Step
  public void iShouldSeeText(String locator,String text) {
    assert tryFindElement(locator).getText().contains(text);
  }

  @Step
  public void iShouldNotSeeText(String locator,String text) {
    assert !tryFindElement(locator).getText().contains(text);
  }

  @Step
  public static Boolean isCheckBoxSelected(String locator) {
    try {
      getFluentWait().until(ExpectedConditions.elementToBeSelected(xpath(locator)));
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  @Step
  static long getTimeStamp() {
    long timeStamp = Calendar.getInstance().getTime().getTime();
    return timeStamp;
  }

  @Step
  public void selectFromListByIndex(String list, int option) {
    getFluentWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(xpath(list), 1));
    new Select(tryFindElement(list)).selectByIndex(option);
  }

  @Step
  public void selectFromListByValue(String list, String value) {
    log.info("Selecting from list {} by value {}", list, value);
    getFluentWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(xpath(list), 1));
    new Select(tryFindElement(list)).selectByValue(value);
    getFluentWait().until(ExpectedConditions.attributeToBe(xpath(list), "value", value));
  }

  @Step
  public void selectFromListByVisibleText(String list, String value) {
    new Select(tryFindElement(list)).selectByVisibleText(value);
  }

  @Step
  public void hoverOverElement(String locator) {
    Actions builder = new Actions(getDriver());
    builder.moveToElement(tryFindElement(locator)).perform();
  }

  @Step
  public static int getRandomNumberBetween(int min, int max) {
    Random random = new Random();
    int randomNum = random.nextInt((max - min) + 1) + min;
    return randomNum;
  }

  @Step
  public void sendKeyTo(String locator, String key) {
    switch (key.toLowerCase()) {
      case "enter":
        tryFindElement(locator).sendKeys(Keys.ENTER);
        break;
      case "down":
        tryFindElement(locator).sendKeys(Keys.ARROW_DOWN);
        break;
      case "up":
        tryFindElement(locator).sendKeys(Keys.ARROW_UP);
        break;
      case "space":
        tryFindElement(locator).sendKeys(Keys.SPACE);
        break;
      case "right":
        tryFindElement(locator).sendKeys(Keys.ARROW_RIGHT);
        break;
      case "left":
        tryFindElement(locator).sendKeys(Keys.ARROW_LEFT);
        break;
      case "escape":
        tryFindElement(locator).sendKeys(Keys.ESCAPE);
        break;
      case "backspace":
        tryFindElement(locator).sendKeys(Keys.BACK_SPACE);
        break;
      default:
        log.info("key not recognized");
        break;
    }
  }

  @Step
  public static String getTextFromElement(String locator) {
    return tryFindElement((locator)).getText();
  }

  @Step
  public static String getValueFromElement(String locator) {
    return tryFindElement((locator)).getAttribute("value");
  }

  @Step
  public void openPage(String url) {
    log.info("Opening web page {}", url);
    getDriver().get(url);
  }

  @Step
  public void deleteAllCookies() {
    getDriver().manage().deleteAllCookies();
  }

  @Step
  public void reloadPageIfElementIsNotPresent(String locator) {
    try {
      iShouldSee(locator);
    } catch (AssertionError ex) {
      log.info("Page url is {}", WebDriverHolder.getDriver().getCurrentUrl());
      reload();
      reload();
      iShouldSee(locator);
    }
  }

  @Step
  public static String getUrl() {
    return getDriver().getCurrentUrl();
  }

  public void refreshPageIfSomethingWentWrong() throws InterruptedException {
    sleep(1000);
    if (getDriver().getPageSource().contains("something went wrong")) {
      reload();
      sleep(2000);
    }
  }

  @Step
  public void switchToTab(int tabNumber) {
    ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
    getDriver().switchTo().window(tabs.get(tabNumber));
  }

  @Step
  static Integer getNumberFromText(String text) {
    return Integer.parseInt(text.replaceAll("[^0-9]", ""));
  }

}
