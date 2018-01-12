package entity.pages;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AbstractPage {

    private WebDriver driver;
    private Logger logger = LogManager.getLogger(InboxPage.class);

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }


    public void waitForElementToBeClickable(WebElement webElement) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void waitForVisibilityOfAllElementsLocated(WebElement webElement) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void waitForListElements(List<WebElement> webElements) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(webElements));
    }

    public void takeScreenshot() {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotName = "screenshot" + System.nanoTime();
            File copy = new File(screenshotName + ".png");
            FileUtils.copyFile(screenshot, copy);
            logger.info("Saved screenshot: " + screenshotName);
        } catch (IOException e) {
            logger.error("Failed to make screenshot");
        }
    }

    public void highlightElement(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='5px solid green'", webElement);
    }

    public void unHighlightElement(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='0px'", webElement);
    }

    public void clickBtn(WebElement webElement) {
        waitForElementToBeClickable(webElement);
        highlightElement(webElement);
        logger.info("Clicking element '" + webElement.getText());
        unHighlightElement(webElement);
        webElement.click();
    }

    public void sendkeys(WebElement webElement, String s) {
        highlightElement(webElement);
        logger.info("Typing text '" + s + "' to input form '" + webElement.getAttribute("name"));
        webElement.sendKeys(s);
        unHighlightElement(webElement);
    }
}


