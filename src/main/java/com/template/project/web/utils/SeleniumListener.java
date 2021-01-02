package com.template.project.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestResult;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.testng.AllureTestListener;

import static com.template.project.common.Logger.*;

@Slf4j
public class SeleniumListener extends AllureTestListener {

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

  @Step("Step on Failure")
  @Override
  public void onTestFailure(ITestResult tr) {
    log.error("{} --- FAILED --- ", tr.getName());

    // saveTextLog(tr.getName());
    logInfo(String.format("%s failed and screenshot taken", tr.getName()));
    saveScreenshotPNG();

    Throwable ex = tr.getThrowable();
    if (ex != null) {
      String cause = ex.toString();
      saveTextLog(cause);
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
}
