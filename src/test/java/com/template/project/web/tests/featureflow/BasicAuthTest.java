package com.template.project.web.tests.featureflow;

import static com.template.project.common.Cipher.decrypt;
import static com.template.project.common.Groups.AUTH;
import static com.template.project.common.Groups.NOT_FOR_REMOTE_EXECUTION;
import static com.template.project.web.utils.SeleniumUtils.handleBasicAuthViaBiDiApi;

import com.template.project.common.ConfigFileReaderUtils;
import com.template.project.common.PropertyFileReaderUtils;
import com.template.project.web.pages.HerokuappBasicAuthPage;
import com.template.project.web.tests.common.BaseTest;
import com.template.project.web.utils.SeleniumUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Herokuapp")
@Story("Herokuapp Basic Authorization")
public class BasicAuthTest extends BaseTest {

  private HerokuappBasicAuthPage herokuappBasicAuthPage;

  @BeforeMethod(alwaysRun = true)
  public void setup() {
    herokuappBasicAuthPage = new HerokuappBasicAuthPage();
  }

  @Test(groups = {AUTH})
  @Description(
      "This is to validate the authorization via encoded URL without chrome dev tool protocol")
  public void navigateToEncodedURL() {
    final String url = herokuappBasicAuthPage.generateEncodedURL();
    SeleniumUtils.openPage(url);
    herokuappBasicAuthPage.verifyIfPageHeaderIsDisplayed();
    herokuappBasicAuthPage.verifyIfSuccessMessageIsDisplayed();
    herokuappBasicAuthPage.verifyIfPageFooterIsPresent();
  }

  @Test(groups = {NOT_FOR_REMOTE_EXECUTION})
  @Description(
      "This is to validate the authorization via encoded URL using chrome dev tool protocol")
  public void navigateToEncodedUrlViaDevToolsProtocol() {
    SeleniumUtils.handleBasicAuthViaChromeDevTools(
        PropertyFileReaderUtils.getValueFromTestDataFile("USERNAME"),
        decrypt(PropertyFileReaderUtils.getValueFromTestDataFile("PASSWORD")));
    SeleniumUtils.openPage(herokuappBasicAuthPage.generateURL());
    herokuappBasicAuthPage.verifyIfPageHeaderIsDisplayed();
    herokuappBasicAuthPage.verifyIfSuccessMessageIsDisplayed();
    herokuappBasicAuthPage.verifyIfPageFooterIsPresent();
  }

  @Test(groups = {AUTH})
  @Description("This is to validate the authorization via Bi-Directional protocol")
  public void basicAuthUsingBiDiApi() {
    handleBasicAuthViaBiDiApi(
        ConfigFileReaderUtils.getValueFromEnvironmentFile("host_url"),
        PropertyFileReaderUtils.getValueFromTestDataFile("USERNAME"),
        decrypt(PropertyFileReaderUtils.getValueFromTestDataFile("PASSWORD")));
    SeleniumUtils.openPage(herokuappBasicAuthPage.generateURL());
    herokuappBasicAuthPage.verifyIfPageHeaderIsDisplayed();
    herokuappBasicAuthPage.verifyIfSuccessMessageIsDisplayed();
    herokuappBasicAuthPage.verifyIfPageFooterIsPresent();
  }
}
