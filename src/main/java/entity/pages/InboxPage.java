package entity.pages;

import entity.exceptions.NoSuchMessageException;
import entity.business_objects.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InboxPage extends AbstractPage {
    Logger logger = LogManager.getLogger(InboxPage.class);
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
    //  @FindBy(xpath = "//*[@ng-repeat = 'conversation in conversations track by conversation.ID']")
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

    public Boolean headerIsDisplayed() {
        waitForVisibilityOfAllElementsLocated(inboxHeader);
        return inboxHeader.isDisplayed();
    }

    public void createNewMessage(Message message) {
        composeBtn.click();
        waitForVisibilityOfAllElementsLocated(subjectInput);
        subjectInput.sendKeys(message.getSubject());
        recipientInput.sendKeys(message.getEmail());
        getDriver().switchTo().frame(textBoxFrame);
        textBox.click();
        Actions make = new Actions(getDriver());
        Action kbEvents = make.sendKeys(message.getTextContent()).build();
        kbEvents.perform();
        getDriver().switchTo().defaultContent();
        waitForElementToBeClickable(saveBtn);
        saveBtn.click();
        waitForVisibilityOfAllElementsLocated(messagePopUp);
        waitForElementToBeClickable(closeMessageBtn);
        closeMessageBtn.click();
    }

    public void sendMessageFromDrafts(Message message) throws NoSuchMessageException {
        waitForElementToBeClickable(draftsBtn);
        draftsBtn.click();
        waitForListElements(draftsList);
        List<WebElement> list = draftsList;
        for (WebElement webElement : list) {
            waitForElementToBeClickable(webElement);
            if (sendersName.getText().equals(message.getEmail()) && subjectText.getText().equals(message.getSubject())) {
                webElement.click();
                waitForElementToBeClickable(sendBtn);
                sendBtn.click();
                waitForVisibilityOfAllElementsLocated(messagePopUp);
                break;
            } else {
                throw new NoSuchMessageException();
            }
        }
    }

    public boolean checkLetterInSent(Message message) throws NoSuchMessageException {
        waitForElementToBeClickable(sentMessagePageBtn);
        sentMessagePageBtn.click();
        boolean check = true;
        waitForListElements(draftsList);
        List<WebElement> list = draftsList;
        for (WebElement draft : list) {
            if (sendersName.getText().equals(message.getEmail()) && subjectText.getText().equals(message.getSubject())) {
                check = true;
            } else {
                throw new NoSuchMessageException();
            }
        }
        return check;
    }
/*
    public void dragAndDrop() {
        waitForElementToBeClickable(droppableMenu);
        droppableMenu.click();
        waitForVisibilityOfAllElementsLocated(droppFrame);
        getDriver().switchTo().frame(droppFrame);
        Actions make = new Actions(getDriver());
        Action kbEvents = make.dragAndDrop(draggable, droppable).build();
        kbEvents.perform();
    }*/

    public void logoff() {
        userTrigger.click();
        logoffBtn.click();
    }


}