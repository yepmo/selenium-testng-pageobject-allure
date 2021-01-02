package com.template.project.web.tests.featureflow;

import com.template.project.common.ConfigFileReaderUtils;
import com.template.project.web.pages.HerokuappCheckboxPage;
import com.template.project.web.pages.HerokuappLandingPage;
import com.template.project.web.tests.common.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.model.SeverityLevel;

import javax.naming.ConfigurationException;

import static com.template.project.common.Groups.INPUT_FIELD;
import static com.template.project.web.utils.SeleniumUtils.openPage;

@Features("Herokuapp")
@Stories("Herokuapp checkbox")
public class CheckBoxTest extends BaseTest {

  private HerokuappLandingPage herokuappLandingPage;
  private HerokuappCheckboxPage herokuappCheckboxPage;

  @BeforeMethod(alwaysRun = true)
  public void setup() throws ConfigurationException {
    herokuappCheckboxPage = new HerokuappCheckboxPage();
    herokuappLandingPage = new HerokuappLandingPage();
    final String url = ConfigFileReaderUtils.getValueFromEnvironmentFile("host");
    openPage(url);
    herokuappLandingPage.verifyIfPageHeaderIsDisplayed();
    herokuappLandingPage.navigateToSubPage("Checkboxes");
    herokuappCheckboxPage.verifyCheckboxLandingPage();
  }

  @Test(groups = {INPUT_FIELD})
  @Severity(SeverityLevel.MINOR)
  @Description("This is a test for handling checkboxes")
  public void checkboxTest() {
    herokuappCheckboxPage.checkOnCheckBox1();
    herokuappCheckboxPage.unCheckCheckbox1();
    herokuappCheckboxPage.unCheckCheckbox2();
    herokuappCheckboxPage.checkOnCheckBox2();
  }
}
