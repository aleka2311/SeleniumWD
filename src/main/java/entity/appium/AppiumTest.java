package entity.appium;

import entity.business_objects.Message;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tests.ProtonTest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AppiumTest {
    AndroidDriver<AndroidElement> driver;

    @BeforeTest
    public void androidDriverInit() throws MalformedURLException {

        File file = new File("src/main/resources/Easy Note.apk");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Aray");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, file);
        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
    }

    @Test(dataProvider = "testDataForMessage", dataProviderClass = ProtonTest.class)
    public void makeNote(Message message) {
        driver.findElement(By.className("android.widget.ImageButton")).click();
        List<AndroidElement> list = driver.findElements(By.className("android.widget.EditText"));
        for (AndroidElement androidElement : list) {
            if (androidElement.getText().equals("Add title")) androidElement.sendKeys(message.getSubject());
            if (androidElement.getText().equals("Add content")) androidElement.sendKeys(message.getTextContent());
        }
        driver.findElement(By.id("action_save")).click();
        Assert.assertEquals(driver.findElement(By.id("note_content")).getText(), message.getTextContent());
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}
