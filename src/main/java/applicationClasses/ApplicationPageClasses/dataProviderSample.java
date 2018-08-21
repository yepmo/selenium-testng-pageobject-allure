package applicationClasses.ApplicationPageClasses;

import org.testng.annotations.DataProvider;

public class dataProviderSample {


    @DataProvider(name = "sampleData")
    public Object[][] sampleData(){
        return new Object[][]{
                {"abc","def"},
                {"ghi","jkl"},
                {"mno","pqr"}
        };
    }

}
