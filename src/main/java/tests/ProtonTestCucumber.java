package tests;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import entity.pages.InboxPage;
import entity.singleton.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

@CucumberOptions(strict = true, tags = "@smokeTest", features = {"src/main/resources/cucumber_features/ALoginMail.feature", "src/main/resources/cucumber_features/BMessageCreate.feature"}, glue = {"entity.steps"})

public class ProtonTestCucumber extends AbstractTestNGCucumberTests {
    private static WebDriver driver = WebDriverSingleton.getWebDriverInstance();

    @BeforeClass(description = "Start browser, add implicit wait and maximize window")
    public void startBrowser() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass(description = "Stop Browser")
    public void logOffAndStopBrowser() {
        new InboxPage(driver).logoff();
        driver.quit();
    }
}
