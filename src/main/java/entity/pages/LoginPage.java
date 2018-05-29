package entity.pages;

import entity.business_objects.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {
    @FindBy(xpath = "//input[@id='username']")
    private WebElement usernameInput;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@id='login_btn']")
    private WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InboxPage login(User user) {
        waitForVisibilityOfElement(usernameInput);
        sendkeys(usernameInput, user.getUsername());
        sendkeys(passwordInput, user.getPassword());
        clickBtn(loginBtn);
        return new InboxPage(getDriver());
    }

}


