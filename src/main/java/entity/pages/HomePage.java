package entity.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {
    @FindBy(xpath = "//a[@title = 'Log In']")
    private WebElement loginButton;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickOnLoginButton() {
        clickBtn(loginButton);
        return new LoginPage(getDriver());
    }
}
