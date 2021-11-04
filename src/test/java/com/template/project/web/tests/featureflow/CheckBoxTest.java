package com.template.project.web.tests.featureflow;

import static com.template.project.common.Groups.INPUT_FIELD;
import static com.template.project.web.utils.SeleniumUtils.openPage;

import com.template.project.common.ConfigFileReaderUtils;
import com.template.project.web.pages.HerokuappCheckboxPage;
import com.template.project.web.pages.HerokuappLandingPage;
import com.template.project.web.tests.common.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Herokuapp")
@Story("Herokuapp checkbox")
public class CheckBoxTest extends BaseTest {

  private HerokuappLandingPage herokuappLandingPage;
  private HerokuappCheckboxPage herokuappCheckboxPage;

  @BeforeMethod(alwaysRun = true)
  public void setup() {
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
