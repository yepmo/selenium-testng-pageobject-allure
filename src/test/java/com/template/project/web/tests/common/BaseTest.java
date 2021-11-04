package com.template.project.web.tests.common;

import static com.template.project.common.Logger.logInfo;
import static com.template.project.web.utils.WebDriverHolder.setDriver;
import static com.template.project.web.utils.WebDriverHolder.tearDownBrowser;

import com.template.project.common.RetryListener;
import com.template.project.web.utils.SeleniumListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Listeners({SeleniumListener.class})
public class BaseTest extends RetryListener {

  @Parameters("browserName")
  @BeforeMethod(alwaysRun = true)
  protected static void setUpBrowser(@Optional("chrome") String browserName, ITestContext result)
      throws UnknownHostException {
    logInfo(String.format("Tests running on host: %s", InetAddress.getLocalHost().getHostName()));
    setDriver(browserName, result.getName());
  }

  @AfterMethod(alwaysRun = true)
  protected static void tearDown(ITestResult testResult) {
    tearDownBrowser();
  }
}
