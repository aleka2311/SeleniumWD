package entity.pages;

import entity.decorator.CustomWebElementDecorator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {
    private Logger logger = LogManager.getLogger(HomePage.class);
    @FindBy(xpath = "//a[text() = 'LOG IN']")
    private WebElement loginButton;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickOnLoginButton() {
        waitForElementToBeClickable(loginButton);
        highlightElement(loginButton);
        logger.info("Clicking element '" + loginButton.getText() + "' (Located: " + loginButton.getTagName() + ")");
        unHighlightElement(loginButton);
        new CustomWebElementDecorator(loginButton).click();
        return new LoginPage(getDriver());
    }
}
