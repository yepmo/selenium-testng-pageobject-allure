package applicationName;

import applicationClasses.ApplicationPageClasses.PageClass;
import common.utility.Retry;
import io.qameta.allure.*;
import java.io.IOException;
import org.testng.annotations.*;
import org.testng.annotations.Test;


import static common.utility.Helpers.getFromPropertyFile;
import static common.utility.Helpers.*;


@Epic("MavenSample Epic")
@Feature("MavenSampleSample Feature")
public class sampleTest extends BaseTest{
    PageClass pageClass;

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        pageClass = new PageClass();
        logInfo("test case execution started");
    }

    @Test
    @Description("This is a test for a sample car")
    @Severity(SeverityLevel.BLOCKER)
    public void sampleCar(){ logInfo("This is a Car");}

    @Test(retryAnalyzer = Retry.class)
    @Severity(SeverityLevel.BLOCKER)
    @Description("This is a test for a sample bike")
    public void sampleBike() throws IOException {
      openPage("http://www.google.com");

    String url = getFromPropertyFile("HOST");

      openPage(url);

    assert false; }

    @Test(groups = {"Light","Very Light"})
    @Description("This is a test for a sample Motor Cycle")
    public void sampleMotorCycle(){ logInfo("This is a Motor Cycle");}

    @Test(groups = {"Heavy"})
    @Description("This is a test for a sample car")
    public void sampleBus(){ logInfo("This is a Bus");}

    @Test//(groups = {"Heavy"})
    @Description("This is a test for a sample Truck")
    public void sampleTruck(){ logInfo("This is a Truck");}

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
      logInfo("Execution Completed");
    }
}
