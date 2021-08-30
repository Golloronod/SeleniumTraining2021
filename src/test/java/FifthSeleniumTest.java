import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FifthSeleniumTest extends  TestBase {

    EventFiringWebDriver drv;
    public WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        drv = new EventFiringWebDriver(new ChromeDriver(opt));
        drv.register(new Listener());
        drv.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        opt.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "normal");
        wait = new WebDriverWait(drv, 5);
        //Opening the admin page
        drv.get(getBASE_URL() + "admin");
    }

    @AfterEach
    void closeUp() {
        drv.quit();
    }

    @Test
    void test1() {
        //Entering credentials and clicking on the "Login" button
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@class,'form-control') and contains(@name,'username')]"))).sendKeys(getADMIN_USER());
        drv.findElement(By.xpath("//input[contains(@class,'form-control') and contains(@name,'password')]")).sendKeys(getADMIN_PWD());
        drv.findElement(By.xpath("//button[contains(@class,'btn btn-default') and contains(@name,'login')]")).click();

        //Waiting for the Side Menu to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sidebar']")));
        //Preparing the list of items in the Side Menu
        List<WebElement> sideMenu = drv.findElements(By.xpath("//ul[@id='box-apps-menu']/li[@class='app']"));
        System.out.println(sideMenu.size());
        //Clicking on them one by one and checking if the header is displayed
        for (int i=1;i<=sideMenu.size();i++) {
            drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]")).click();
            if (drv.findElement(By.xpath("//div[@class='panel-heading']")).isDisplayed()) {
                System.out.println(drv.findElement(By.xpath("//div[@class='panel-heading']")).getText());
            }   else {
                System.out.println("Header is not present for the Side Menu item #" + i);
            }

            //For each of the item in Side Menu preparing the list of the items in its Sub Menu
            List<WebElement> subMenu = drv.findElements(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li"));
            //If such Sub Menu items exist, clicking on them one by one and checking if the header is displayed
            if (0 < subMenu.size()) {
                for (int j=1;j<=subMenu.size();j++) {
                    drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li[" + j + "]")).click();
                    if (drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li[" + j + "]")).isDisplayed()) {
                        System.out.println("   " + drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li[" + j + "]")).getText());
                    }   else {
                        System.out.println("   Header is not present for the SUB Menu item #" + j);
                    }
                }
            }
        }
    }


}
