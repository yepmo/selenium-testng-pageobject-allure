package com.template.project.web.tests.featureflow;

import com.template.project.web.pages.HerokuappBasicAuthPage;
import com.template.project.web.tests.common.BaseTest;
import com.template.project.web.utils.SeleniumUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import javax.naming.ConfigurationException;
import java.security.GeneralSecurityException;

import static com.template.project.common.Groups.AUTH;

@Features("Herokuapp")
@Stories("Herokuapp Basic Authorization")
public class BasicAuthTest extends BaseTest {

  private HerokuappBasicAuthPage herokuappBasicAuthPage;

  private String url;

  @BeforeMethod(alwaysRun = true)
  public void setup() throws GeneralSecurityException, ConfigurationException {
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
