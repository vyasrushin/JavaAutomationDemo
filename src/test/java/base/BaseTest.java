package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String browser = "firefox"; // Change to "firefox" if needed

/*    
    @Parameters("browser")
    @BeforeClass
    public void setup() {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/webtables");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
*/
    
    @Parameters({"browser", "appUrl"})
    @BeforeClass
    public void setup(@Optional("chrome") String browser, @Optional("https://demoqa.com/webtables") String appUrl) {
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.get(appUrl);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public WebDriver getDriver() {
        return driver;
    }
    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}