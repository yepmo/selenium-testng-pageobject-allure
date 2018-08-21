package applicationClasses.ApplicationPageClasses;


import applicationClasses.ApplicationLocators.PageLocator;

public class PageClass extends PageLocator {

    public int sampleSum(int a,int b){
        int Sum = a + b;
        logInfo("Sum of 2 numbers is "+Sum);
        return Sum;
    }

}
