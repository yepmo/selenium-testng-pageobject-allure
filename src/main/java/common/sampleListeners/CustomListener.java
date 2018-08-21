package common.sampleListeners;

import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.testng.ITestContext;
import org.testng.ITestResult;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.testng.AllureTestListener;

import static common.utility.Helpers.logInfo;
import static common.utility.WebDriverHolder.getDriver;

public class CustomListener extends AllureTestListener {


  private static final Logger log = LogManager.getLogger(CustomListener.class);

  @Override
  public void onStart(ITestContext arg0) {
    log.info("Test suite: {}", arg0.getName());
  }

  @Override
  public void onTestStart(ITestResult arg0) {
    log.info("Starting test {}", arg0.getMethod());
  }

  @Override
  public void onTestSuccess(ITestResult tr) {
    log.info("{} --- SUCCESS ---\n", tr.getName());
  }

  @Step("Step onFailure")
  @Override
  public void onTestFailure(ITestResult tr) {
    log.error("{} --- FAILED --- ", tr.getName());

    //saveTextLog(tr.getName());

    Throwable ex = tr.getThrowable();
    logInfo(tr.getName()+" failed and screenshot taken");
    saveScreenshotPNG();
    if (ex != null) {
      String cause = ex.toString();
//      saveTextLog(cause);
      log.error(cause + "\n");
    }
  }

  @Override
  public void onTestSkipped(ITestResult tr) {
    log.info("{} --- SKIPPED ---\n", tr.getName());
    Throwable ex = tr.getThrowable();
    if (ex != null) {
      String cause = ex.toString();
      log.error("{}\n", cause);
    }
  }

  @Attachment(value = "page screenshot", type = "image/png")
  public byte[] saveScreenshotPNG(){
    return((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
  }

  @Attachment
  public static String saveTextLog(String message) {
    return message;
  }

  @Attachment(value = "System Environment", type = "text/plain")
  public byte[] attachSystemProperties() {
    Properties props = System.getProperties();
    StringBuilder result = new StringBuilder();
    for (String prop : props.stringPropertyNames()) {
      result.append(prop)
          .append(" = ")
          .append(System.getProperty(prop))
          .append("\n");
    }
    return result.toString().getBytes();
  }

}

