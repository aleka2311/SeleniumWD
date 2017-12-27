
import entity.business_objects.User;
import entity.exceptions.MailFormedURLException;
import entity.exceptions.NoSuchMessageException;
import entity.pages.HomePage;
import entity.pages.InboxPage;
import entity.business_objects.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;


public class ProtonTest {
    private WebDriver driver;
    private InboxPage inboxPage;
    private HomePage homePage;
    private Logger logger = LogManager.getLogger(InboxPage.class);

    @BeforeTest
    public void openBrowser() throws MailFormedURLException {
       /*
        try {
            driver = new RemoteWebDriver(new URL("http://epkzkarw0218:4444/wd/hub"), DesiredCapabilities.chrome());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("https://protonmail.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void loginMail() {
        homePage = new HomePage(driver);
        inboxPage = new InboxPage(driver);
        homePage.clickOnLoginButton().login(User.USER1.getUsername(), User.USER1.getPassword());
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
    public void checkMessageInSent(Message message) {
        try {
            Assert.assertTrue(inboxPage.checkLetterInSent(message));
        } catch (NoSuchMessageException e) {
            logger.error("Сообщение отсутсвует");
        }
    }


    @AfterTest
    public void logOffAndQuit() {
        inboxPage.logoff();
        driver.quit();
    }

    @DataProvider
    public Object[][] testDataForMessage() {
        return new Object[][]{
                {new Message("arai_23.11@mail.ru", "Drafts", "You will see this message many times in future.")}
        };
    }

}