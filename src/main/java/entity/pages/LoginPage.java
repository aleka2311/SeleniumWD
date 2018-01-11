package entity.pages;

import entity.business_objects.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(LoginPage.class);
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
        highlightElement(usernameInput);
        logger.info("Typing text '" + user.getUsername() + "' to input form '" + usernameInput.getAttribute("name") + "' (Located: " + usernameInput.getTagName() + ")");
        usernameInput.sendKeys(user.getUsername());
        unHighlightElement(usernameInput);
        highlightElement(passwordInput);
        logger.info("Typing text '" + user.getPassword() + "' to input form '" + passwordInput.getAttribute("name") + "' (Located: " + passwordInput.getTagName() + ")");
        passwordInput.sendKeys(user.getPassword());
        unHighlightElement(passwordInput);
        highlightElement(loginBtn);
        logger.info("Clicking element '" + loginBtn.getText() + "' (Located: " + loginBtn.getTagName() + ")");
        unHighlightElement(loginBtn);
        loginBtn.click();
        return new InboxPage(getDriver());
    }

}


