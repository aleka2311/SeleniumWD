package entity.pages;

import entity.exceptions.NoSuchElement;
import entity.exceptions.NoSuchMessageException;
import entity.business_objects.Message;
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
    @FindBy(xpath = "//*[@class='headerSecuredDesktop-container']")
    private WebElement inboxHeader;
    @FindBy(css = ".compose.pm_button.sidebar-btn-compose")
    private WebElement composeBtn;
    @FindBy(xpath = "//input[@ng-model='message.Subject']")
    private WebElement subjectInput;
    @FindBy(xpath = "//input[@id='autocomplete']")
    private WebElement recipientInput;
    @FindBy(xpath = "//iframe[@class = 'squireIframe']")
    private WebElement textBoxFrame;
    @FindBy(xpath = "//*[@class='protonmail_signature_block']/preceding-sibling::div[2]")
    private WebElement textBox;
    @FindBy(xpath = "//*[@aria-label='Сохранить']")
    private WebElement saveBtn;
    @FindBy(xpath = "//*[@data-original-title = 'Закрыть']")
    private WebElement closeMessageBtn;
    @FindBy(xpath = "//span[text() = 'Черновики']")
    private WebElement draftsBtn;
    @FindBy(xpath = "//*[@draggable='true']")
    private List<WebElement> draftsList;
    @FindBy(xpath = "//*[@class = 'senders-name']")
    private WebElement sendersName;
    @FindBy(xpath = "//*[@class = 'subject-text ellipsis']")
    private WebElement subjectText;
    @FindBy(xpath = "//*[text()='Отправить']")
    private WebElement sendBtn;
    @FindBy(xpath = "//span[@ng-bind-html = '$message']")
    private WebElement messagePopUp;
    @FindBy(xpath = "//*[@href='/sent']")
    private WebElement sentMessagePageBtn;
    @FindBy(xpath = "//*[@class='navigation-link navigationUser-link pm_trigger']")
    private WebElement userTrigger;
    @FindBy(xpath = " //*[@ui-sref='login']")
    private WebElement logoffBtn;
    @FindBy(xpath = "//span[text()='Входящие']")
    private WebElement incoming;
    @FindBy(xpath = "//*[@data-pt-dropzone-item='archive']")
    private WebElement archive;
    @FindBy(xpath = "//a[text()='Droppable']")
    private WebElement droppableMenu;
    @FindBy(xpath = "//*[@class='demo-frame']")
    private WebElement droppFrame;
    @FindBy(xpath = "//*[@id='draggable']")
    private WebElement draggable;
    @FindBy(xpath = "//*[@id='droppable']")
    private WebElement droppable;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public Boolean headerIsDisplayed() throws NoSuchElement {
        waitForVisibilityOfAllElementsLocated(inboxHeader);
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
        waitForVisibilityOfAllElementsLocated(subjectInput);
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
        waitForVisibilityOfAllElementsLocated(messagePopUp);
        closeMessageBtn.click();
    }

    public void sendMessageFromDrafts(Message message) throws NoSuchMessageException {
        clickBtn(draftsBtn);
        waitForListElements(draftsList);
        List<WebElement> list = draftsList;
        for (WebElement webElement : list) {
            waitForElementToBeClickable(webElement);
            if (sendersName.getText().equals(message.getEmail()) && subjectText.getText().equals(message.getSubject())) {
                clickBtn(webElement);
                clickBtn(sendBtn);
                waitForVisibilityOfAllElementsLocated(messagePopUp);
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