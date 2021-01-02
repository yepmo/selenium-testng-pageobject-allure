package com.template.project.web.tests.featureflow;

import com.template.project.common.ConfigFileReaderUtils;
import com.template.project.web.pages.HerokuappLandingPage;
import com.template.project.web.pages.HerokuappUploadPage;
import com.template.project.web.tests.common.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.model.SeverityLevel;

import javax.naming.ConfigurationException;

import static com.template.project.web.utils.SeleniumUtils.openPage;

@Features("Herokuapp")
@Stories("Herokuapp upload")
public class UploadTest extends BaseTest {

  private HerokuappLandingPage herokuappLandingPage;
  private HerokuappUploadPage herokuappUploadPage;

  @BeforeMethod(alwaysRun = true)
  public void setup() throws ConfigurationException {
    herokuappLandingPage = new HerokuappLandingPage();
    herokuappUploadPage = new HerokuappUploadPage();

    openPage(ConfigFileReaderUtils.getValueFromEnvironmentFile("host"));
    herokuappLandingPage.verifyIfPageHeaderIsDisplayed();
    herokuappLandingPage.navigateToSubPage("File Upload");
    herokuappUploadPage.verifyIfPageHeaderIsDisplayed();
  }

  @Test
  @Description("This is a sample Test 4")
  @Severity(SeverityLevel.BLOCKER)
  public void uploadTest() {
    herokuappUploadPage.uploadImageToField("//input[@id='file-upload']", "sampleJPEG.jpeg");
    herokuappUploadPage.clickUploadButton();
  }
}
