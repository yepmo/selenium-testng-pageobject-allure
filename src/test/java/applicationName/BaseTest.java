package applicationName;

import common.utility.WebDriverHolder;
import common.sampleListeners.CustomListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


@Listeners({CustomListener.class})
public class BaseTest extends WebDriverHolder {

    @Parameters("browserName")
    @BeforeMethod(alwaysRun = true)
    static void setUpBrowser(@Optional("chrome") String browserName) { setDriver(browserName); }

    @AfterMethod(alwaysRun = true)
    void tearDown(ITestResult testResult){
        tearDownBrowser();
    }
}
