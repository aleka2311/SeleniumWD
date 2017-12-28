package entity.factory_method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.IOException;

public class ChromeDriverCreator extends WebDriverCreator {
    @Override
    public WebDriver FactoryMethod() {
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(
                new File("src/main/resources/chromedriver.exe")).build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = new ChromeDriver(service);
        return driver;
    }
}
