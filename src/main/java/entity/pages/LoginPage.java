package entity.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    @FindBy(xpath = "//*[@id='username']")
    private WebElement usernameInput;
    @FindBy(xpath = "//*[@id='password']")
    private WebElement passwordInput;
    @FindBy(xpath = "//*[@id='login_btn']")
    private WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InboxPage login(String login, String password) {
        waitForVisibilityOfAllElementsLocated(usernameInput);
        usernameInput.sendKeys(login);
        passwordInput.sendKeys(password);
        loginBtn.click();
        return new InboxPage(getDriver());
    }

}


