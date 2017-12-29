package entity.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.business_objects.Message;
import entity.exceptions.NoSuchMessageException;
import entity.pages.InboxPage;
import entity.singleton.WebDriverSingleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ProtonMessageCreating {
    private static WebDriver driver = WebDriverSingleton.getWebDriverInstance();
    private Logger logger = LogManager.getLogger(InboxPage.class);

    @Given("^user creates new draft \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void compose_new_letter(String email, String subject, String text) {
        Message message = new Message(email, subject, text);
        new InboxPage(driver).createNewMessage(message);
    }

    @When("^user sends new draft \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void send_letter_from_draft(String email, String subject, String text) throws NoSuchMessageException {
        Message message = new Message(email, subject, text);
        new InboxPage(driver).sendMessageFromDrafts(message);
    }

    @Then("^check in sent draft \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void check_in_sent(String email, String subject, String text) throws NoSuchMessageException {
        Message message = new Message(email, subject, text);
        try {
            Assert.assertTrue(new InboxPage(driver).checkLetterInSent(message));
        } catch (NoSuchMessageException e) {
            logger.error("Сообщение отсутсвует");
        }
    }

}
