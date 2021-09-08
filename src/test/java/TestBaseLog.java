import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestBaseLog {

    public EventFiringWebDriver drv;

    public WebDriverWait wait;

    public String getBASE_URL() {
        return System.getenv("LITECART_BASE_URL");
    }

    public String getADMIN_USER() {
        return System.getenv("LITECART_ADMIN_USER");
    }

    public String getADMIN_PWD() {
        return System.getenv("LITECART_ADMIN_PWD");
    }

    @BeforeEach
    public void settingUp() {

        ChromeOptions opt = new ChromeOptions();
        opt.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "normal");
        WebDriverManager.chromedriver().setup();
        drv = new EventFiringWebDriver(new ChromeDriver());
        drv.register(new Listener());
        drv.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(drv, 5);
    }

    //Method that is looking for the presence of the element
    public boolean isElementPresent(By element) {
        return drv.findElements(element).size() > 0;
    }

    //Method for accepting cookies if they exist
    public void acceptCookies() {
        By accept_cookies = By.cssSelector("button[name=accept_cookies]");
        if (isElementPresent(accept_cookies)) {
            drv.findElement(accept_cookies).click();
        }
    }

    //Method for logging in to the Admin page
    public void loginToAdmin() {
        drv.get(getBASE_URL() + "admin");

        By username = By.name("username");
        By password = By.name("password");
        By loginBtn = By.cssSelector("button[name=login]");

        //Verifying the Admin page is loaded by presence of the Username field
        Assertions.assertTrue(isElementPresent(username), "Admin page is not loaded or Username does not exist");
        drv.findElement(username).sendKeys(getADMIN_USER());
        drv.findElement(password).sendKeys(getADMIN_PWD());
        drv.findElement(loginBtn).click();
        //Waiting for the first Side Menu item is clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.id("box-apps-menu")));
    }

}
