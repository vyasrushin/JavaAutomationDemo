package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import utils.ScreenshotUtil;
import base.BaseTest;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        if (testClass instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testClass).getDriver();
            String testName = result.getName();
            ScreenshotUtil.takeScreenshot(driver, testName + "_FAILED");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Uncomment this if you want screenshots on success too
        
        Object testClass = result.getInstance();
        if (testClass instanceof BaseTest) {
            WebDriver driver = ((BaseTest) testClass).getDriver();
            String testName = result.getName();
            ScreenshotUtil.takeScreenshot(driver, testName + "_PASSED");
        }
        
    }

    // Other methods can be left empty if not needed
    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
