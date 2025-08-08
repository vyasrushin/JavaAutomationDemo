package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import java.util.List;

public class WebTablePage {
    private WebDriver driver;

    public WebTablePage(WebDriver driver) {
        this.driver = driver;
    }

    private By addButton = By.id("addNewRecordButton");
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By emailField = By.id("userEmail");
    private By ageField = By.id("age");
    private By salaryField = By.id("salary");
    private By departmentField = By.id("department");
    private By submitButton = By.id("submit");

    private String editButtonXpath = "//div[text()='${EMAIL}']/ancestor::div[contains(@class,'rt-tr')]/div//span[@title='Edit']";
    private String deleteButtonXpath = "//div[text()='${EMAIL}']/ancestor::div[contains(@class,'rt-tr')]/div//span[@title='Delete']";

    public void clickAddButton() {
        driver.findElement(addButton).click();
    }

    public void fillForm(String firstName, String lastName, String email, String age, String salary, String department) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(ageField).clear();
        driver.findElement(ageField).sendKeys(age);
        driver.findElement(salaryField).clear();
        driver.findElement(salaryField).sendKeys(salary);
        driver.findElement(departmentField).clear();
        driver.findElement(departmentField).sendKeys(department);
    }

    public void submitForm() {
        driver.findElement(submitButton).click();
    }

    public boolean isRecordPresent(String email) {
        List<WebElement> elements = driver.findElements(By.xpath("//div[@class='rt-td' and text()='" + email + "']"));
        Reporter.log("Match the data as : " + elements, false);
        System.out.println("Match the data as : " + elements);
        return !elements.isEmpty();
    }

    public void clickEditButton(String email) {
        String finalXpath = editButtonXpath.replace("${EMAIL}", email);
        driver.findElement(By.xpath(finalXpath)).click();
    }

    public void clickDeleteButton(String email) {
        String finalXpath = deleteButtonXpath.replace("${EMAIL}", email);
        driver.findElement(By.xpath(finalXpath)).click();
    }
}
