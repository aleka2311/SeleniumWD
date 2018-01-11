package tests;

import entity.business_objects.User;
import entity.exceptions.NoSuchElement;
import entity.exceptions.NoSuchMessageException;
import entity.pages.HomePage;
import entity.pages.InboxPage;
import entity.business_objects.Message;
import entity.singleton.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;


public class ProtonTest {
    private WebDriver driver = WebDriverSingleton.getWebDriverInstance();
    private InboxPage inboxPage;
    private HomePage homePage;
    private Logger logger = LogManager.getLogger(ProtonTest.class);

    @BeforeTest
    public void openBrowser() {
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        String url = "https://protonmail.com/";
        driver.navigate().to(url);
        logger.info("Going to URL: " + url);
    }

    @Test
    public void loginMail() throws NoSuchElement {
        homePage = new HomePage(WebDriverSingleton.getWebDriverInstance());
        inboxPage = new InboxPage(WebDriverSingleton.getWebDriverInstance());
        homePage.clickOnLoginButton().login(User.PROTON_USER);
        try {
            inboxPage.headerIsDisplayed();
        } catch (NoSuchElement e) {
            logger.error("Element is not present.");
        }
        Assert.assertTrue(inboxPage.headerIsDisplayed());
    }

    @Test(dependsOnMethods = {"loginMail"}, dataProvider = "testDataForMessage")
    public void createNewMessage(Message message) {
        inboxPage.createNewMessage(message);
    }

    @Test(dependsOnMethods = {"createNewMessage"}, dataProvider = "testDataForMessage")
    public void sendMessageFromDrafts(Message message) {
        try {
            inboxPage.sendMessageFromDrafts(message);
        } catch (NoSuchMessageException e) {
            logger.error("Сообщение отсутсвует");
        }
    }

    @Test(dependsOnMethods = {"sendMessageFromDrafts"}, dataProvider = "testDataForMessage")
    public void checkMessageInSent(Message message) throws NoSuchMessageException {
        try {
            inboxPage.checkLetterInSent(message);
        } catch (NoSuchMessageException e) {
            logger.error("Сообщение отсутсвует");
        }
        Assert.assertTrue(inboxPage.checkLetterInSent(message));
    }


    @AfterTest
    public void logOffAndQuit() {
        inboxPage.logoff();
        WebDriverSingleton.getWebDriverInstance().quit();
    }

    @DataProvider
    public Object[][] testDataForMessage() {
        return new Object[][]{
                {new Message("arai_23.11@mail.ru", "Drafts", "You will see this message many times in future.")}
        };
    }

}