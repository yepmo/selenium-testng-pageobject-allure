package com.template.project.web.tests.featureflow;

import com.template.project.web.pages.HerokuappBasicAuthPage;
import com.template.project.web.tests.common.BaseTest;
import com.template.project.web.utils.SeleniumUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.template.project.common.Groups.AUTH;

@Feature("Herokuapp")
@Story("Herokuapp Basic Authorization")
public class BasicAuthTest extends BaseTest {

  private HerokuappBasicAuthPage herokuappBasicAuthPage;

  private String url;

  @BeforeMethod(alwaysRun = true)
  public void setup() {
    herokuappBasicAuthPage = new HerokuappBasicAuthPage();
    url = herokuappBasicAuthPage.generateEncodedURL();
  }

  @Test(groups = {AUTH})
  public void navigateToEncodedURL(){
    SeleniumUtils.openPage(url);
    herokuappBasicAuthPage.verifyIfPageHeaderIsDisplayed();
    herokuappBasicAuthPage.verifyIfSuccessMessageIsDisplayed();
    herokuappBasicAuthPage.verifyIfPageFooterIsPresent();
  }

}
