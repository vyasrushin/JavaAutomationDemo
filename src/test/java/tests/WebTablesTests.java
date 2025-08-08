package tests;

import base.BaseTest;
import tsestpages.WebTablePagefsfd;
import pages.WebTablePage;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.TestDataUtil;
import utils.TestDataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(listeners.TestListener.class)
public class WebTablesTests extends BaseTest {
/*
    @Test(priority = 1)
    public void testAddAndValidateRecord() {
        WebTablePagefsfd page = new WebTablePagefsfd(driver);
        String email = "john.doe@test.com";

        page.clickAddButton();
        page.fillForm("John", "Doe", email, "30", "5000", "QA");
        page.submitForm();

        Assert.assertTrue(page.isRecordPresent(email));
    }

    @Test(priority = 2)
    public void testEditRecord() {
        WebTablePagefsfd page = new WebTablePagefsfd(driver);
        String email = "john.doe@test.com";
        String newDepartment = "Development";

        page.clickEditButton(email);
        page.fillForm("John", "Doe", email, "30", "5000", newDepartment);
        page.submitForm();

        Assert.assertTrue(page.isRecordPresent(email)); // Just validation here
    }

    @Test(priority = 3)
    public void testDeleteRecord() {
        WebTablePagefsfd page = new WebTablePagefsfd(driver);
        String email = "john.doe@test.com";

        page.clickDeleteButton(email);

        Assert.assertFalse(page.isRecordPresent(email));
    }

    @Test(priority = 4, dataProvider = "recordData")
    public void testDataDrivenValidation(String firstName, String lastName, String email, String age, String salary, String department) {
        WebTablePagefsfd page = new WebTablePagefsfd(driver);
        page.clickAddButton();
        page.fillForm(firstName, lastName, email, age, salary, department);
        page.submitForm();

        Assert.assertTrue(page.isRecordPresent(email));
    }

    @DataProvider(name = "recordData")
    public Object[][] getData() {
        return TestDataUtil.getTestData();
    }
*/
    // 🔹 Global variables for all tests
    private String dataType;
    private String fileName;
    private String testCaseName;
    
    @BeforeSuite
    @Parameters({"dataType", "fileName", "testCaseName"})
    public void loadGlobalTestData(@Optional("json") String dataType,
						            @Optional("TestData.json") String fileName,
						            @Optional("") String testCaseName) 
    {
        // Store in globals
        this.dataType = dataType;
        this.fileName = fileName;
        this.testCaseName = testCaseName;

        // Initial load
        loadTestData();
    }
    
 // ✅ Central loader using global variables
    public void loadTestData()
    {
    	// If testCaseName is not provided or blank → call 2-argument loadData()
        if (testCaseName == null || testCaseName.trim().isEmpty()) {
            TestDataProvider.loadData(dataType, fileName);
        } else {
            TestDataProvider.loadData(dataType, fileName, testCaseName);
        }
    }

    @Test(priority = 1)
    public void testAddAndValidateRecord() throws InterruptedException {
    	testCaseName = "TC2";
        loadTestData();
    	
    	WebTablePage page = new WebTablePage(driver);
        
        // Fetch single value from loaded test data
        String firstName = TestDataProvider.getTestData("firstName");
        String lastName = TestDataProvider.getTestData("lastName");
        String email = TestDataProvider.getTestData("email");
        String age = TestDataProvider.getTestData("age");
        String salary = TestDataProvider.getTestData("salary");
        String department = TestDataProvider.getTestData("department");

        page.clickAddButton();
        page.fillForm(firstName, lastName, email, age, salary, department);
        Thread.sleep(3000);
        page.submitForm();
        Thread.sleep(3000);
        Assert.assertTrue(page.isRecordPresent(email));
    }

    @Test(priority = 2)
    public void testEditRecord() throws InterruptedException {
    	testCaseName = "TC2";
        loadTestData();
        
        WebTablePage page = new WebTablePage(driver);

        String email = TestDataProvider.getTestData("email");
        //String newDepartment = TestDataProvider.getTestData("newDepartment");

        page.clickEditButton(email);
        page.fillForm(
                TestDataProvider.getTestData("firstName"),
                TestDataProvider.getTestData("lastName"),
                email,
                TestDataProvider.getTestData("age"),
                TestDataProvider.getTestData("salary"),
                TestDataProvider.getTestData("department")
        );
        Thread.sleep(3000);
        page.submitForm();
        Thread.sleep(3000);
        Assert.assertTrue(page.isRecordPresent(email));
    }

    @Test(priority = 3)
    public void testDeleteRecord() throws InterruptedException {
    	testCaseName = "TC2";
        loadTestData();
        WebTablePage page = new WebTablePage(driver);
        String email = TestDataProvider.getTestData("email");

        page.clickDeleteButton(email);
        Thread.sleep(3000);
        Assert.assertFalse(page.isRecordPresent(email));
        
    }

    @Test(priority = 4, dataProvider = "recordData")
    public void testDataDrivenValidation(String firstName, String lastName, String email, String age, String salary, String department) throws InterruptedException {
        WebTablePage page = new WebTablePage(driver);
        page.clickAddButton();
        page.fillForm(firstName, lastName, email, age, salary, department);
        Thread.sleep(3000);
        page.submitForm();
        Thread.sleep(3000);
        Assert.assertTrue(page.isRecordPresent(email));
    }

    @DataProvider(name = "recordData")
    public Object[][] getData() {
        // Keeps your JSON-based DataProvider intact
        return TestDataUtil.getTestData();
    }
	
}