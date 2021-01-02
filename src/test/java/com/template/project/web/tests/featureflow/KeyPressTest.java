package com.template.project.web.tests.featureflow;

import com.template.project.common.SampleDataProvider;
import com.template.project.web.pages.HerokuappKeyPressPage;
import com.template.project.web.pages.HerokuappLandingPage;
import com.template.project.web.tests.common.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import javax.naming.ConfigurationException;

import static com.template.project.common.ConfigFileReaderUtils.getValueFromEnvironmentFile;
import static com.template.project.common.Groups.INPUT_FIELD;
import static com.template.project.web.utils.SeleniumUtils.openPage;

@Features("Herokuapp")
@Stories("Herokuapp Key Press")
public class KeyPressTest extends BaseTest {

  private HerokuappLandingPage herokuappLandingPage;
  private HerokuappKeyPressPage herokuappKeyPressPage;

  @BeforeMethod(alwaysRun = true)
  public void setup() throws ConfigurationException {
    herokuappLandingPage = new HerokuappLandingPage();
    herokuappKeyPressPage = new HerokuappKeyPressPage();

    final String url = getValueFromEnvironmentFile("host");
    openPage(url);
    herokuappLandingPage.verifyIfPageHeaderIsDisplayed();
    herokuappLandingPage.navigateToSubPage("Key Presses");
    herokuappKeyPressPage.verifyIfPageHeaderIsDisplayed();
  }

  @Test(dataProvider = "testData", dataProviderClass = SampleDataProvider.class, groups = {INPUT_FIELD})
  public void keyPressTest(String Key, String Result){
    herokuappKeyPressPage.sendKeyAndGetResult(Key, Result);
  }

}
