package entity.pages;

import entity.business_objects.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

    public InboxPage login(User user) {
        waitForVisibilityOfAllElementsLocated(usernameInput);
        sendkeys(usernameInput, user.getUsername());
        sendkeys(passwordInput, user.getPassword());
        clickBtn(loginBtn);
        return new InboxPage(getDriver());
    }

}


