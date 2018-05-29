package entity.pages;

import entity.business_objects.Message;
import entity.exceptions.NoSuchElement;
import entity.exceptions.NoSuchMessageException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InboxPage extends AbstractPage {
    private Logger logger = LogManager.getLogger(InboxPage.class);
    @FindBy(xpath = "//div[@class='headerSecuredDesktop-container']")
    private WebElement inboxHeader;
    @FindBy(xpath = "//button[text() = 'Compose']")
    private WebElement composeBtn;
    @FindBy(xpath = "//input[@title = 'Subject']")
    private WebElement subjectInput;
    @FindBy(xpath = "//div[@data-label = 'To']//input[@class = 'autocompleteEmails-input']")
    private WebElement recipientInput;
    @FindBy(xpath = "//iframe[@class = 'squireIframe']")
    private WebElement textBoxFrame;
    @FindBy(xpath = "//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")
    private WebElement textBox;
    @FindBy(xpath = "//button[@data-original-title = 'Save']")
    private WebElement saveBtn;
    @FindBy(xpath = "//button[@data-original-title = 'Close']")
    private WebElement closeMessageBtn;
    @FindBy(xpath = "//a[@title = 'Drafts']")
    private WebElement draftsBtn;
    @FindBy(xpath = "//div[contains(@class , 'conversation read')]")
    private List<WebElement> draftsList;
    @FindBy(xpath = "//span[@class = 'senders-name']")
    private WebElement sendersName;
    @FindBy(xpath = "//span[@class = 'subject-text ellipsis']")
    private WebElement subjectText;
    @FindBy(xpath = "//button[contains(text(), 'Send')]")
    private WebElement sendBtn;
    @FindBy(xpath = "//span[@ng-bind-html = '$message']")
    private WebElement messagePopUp;
    @FindBy(xpath = "//a[@title= 'Sent']")
    private WebElement sentMessagePageBtn;
    @FindBy(xpath = "//a[@class='navigation-link navigationUser-link pm_trigger']")
    private WebElement userTrigger;
    @FindBy(xpath = " //a[@ui-sref='login']")
    private WebElement logoffBtn;
    @FindBy(xpath = "//div[@ng-if='message.ID']")
    private WebElement messageWindow;


    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public Boolean headerIsDisplayed() throws NoSuchElement {
        waitForVisibilityOfElement(inboxHeader);
        boolean succeed = inboxHeader.isDisplayed();
        if (succeed) {
            highlightElement(inboxHeader);
            logger.info("Successful authentication ");
            unHighlightElement(inboxHeader);
        } else {
            takeScreenshot();
            throw new NoSuchElement();
        }
        return succeed;
    }

    public void createNewMessage(Message message) {
        clickBtn(composeBtn);
        waitForVisibilityOfElement(subjectInput);
        sendkeys(recipientInput, message.getEmail());
        sendkeys(subjectInput, message.getSubject());
        getDriver().switchTo().frame(textBoxFrame);
        waitForElementToBeClickable(textBox);
        textBox.click();
        Actions make = new Actions(getDriver());
        logger.info("Typing text '" + message.getTextContent() + "' to input form '" + textBox.getAttribute("name") + "' (Located: " + textBox.getTagName() + ")");
        Action kbEvents = make.sendKeys(message.getTextContent()).build();
        kbEvents.perform();
        getDriver().switchTo().defaultContent();
        saveBtn.click();
        waitForVisibilityOfElement(messagePopUp);
        closeMessageBtn.click();
    }

    public void sendMessageFromDrafts(Message message) throws NoSuchMessageException {
        clickBtn(draftsBtn);
        waitForListElements(draftsList);
        for (WebElement draft : draftsList) {
            waitForElementToBeClickable(draft);
            if (sendersName.getText().equals(message.getEmail()) && subjectText.getText().equals(message.getSubject())) {
                waitForElementToBeClickable(draft);
                clickOnElementViaActions(draft);
                clickBtn(sendBtn);
                waitForVisibilityOfElement(messagePopUp);
                break;
            } else {
                takeScreenshot();
                throw new NoSuchMessageException();
            }
        }
    }

    public boolean checkLetterInSent(Message message) throws NoSuchMessageException {
        clickBtn(sentMessagePageBtn);
        boolean check = true;
        waitForListElements(draftsList);
        List<WebElement> list = draftsList;
        for (WebElement draft : list) {
            if (sendersName.getText().equals(message.getEmail()) && subjectText.getText().equals(message.getSubject())) {
                check = true;
            } else {
                takeScreenshot();
                throw new NoSuchMessageException();
            }
        }
        return check;
    }

    public void logoff() {
        userTrigger.click();
        highlightElement(logoffBtn);
        logger.info("Clicking element '" + logoffBtn.getText() + "' (Located: " + logoffBtn.getTagName() + ")");
        unHighlightElement(logoffBtn);
        logoffBtn.click();
    }

}