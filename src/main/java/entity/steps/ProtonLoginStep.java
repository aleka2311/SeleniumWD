package entity.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.business_objects.User;
import entity.pages.HomePage;
import entity.pages.InboxPage;
import entity.pages.LoginPage;
import entity.singleton.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ProtonLoginStep {
    private static WebDriver driver = WebDriverSingleton.getWebDriverInstance();

    @Given("^user navigates to proton home page$")
    public void navigate_to_login_page() {
        driver.navigate().to("https://protonmail.com/");
    }

    @When("^click login button$")
    public void click_login() {
        new HomePage(driver).clickOnLoginButton();
    }

    @And("^enters user credentials and submits login form$")
    public void enter_user_credentials() {
        new LoginPage(driver).login(User.PROTON_USER);
    }

    @Then("^proton inbox page is displayed$")
    public void verify_login_is_completed() {
        Assert.assertTrue(new InboxPage(driver).headerIsDisplayed());
    }


}
