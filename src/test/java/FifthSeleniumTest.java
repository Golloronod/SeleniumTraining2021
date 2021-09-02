import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;


public class FifthSeleniumTest extends TestBase {

    EventFiringWebDriver drv;

    @BeforeEach
    void setUp() {
        drv = new EventFiringWebDriver(new ChromeDriver());
        drv.register(new Listener());
    }

    @AfterEach
    void closeUp() {
        drv.quit();
    }

    @Test
    void test1() throws InterruptedException {
        //Opening the admin page
        loginToAdmin();

        //Waiting for the Side Menu to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='sidebar']")));
        //Preparing the list of items in the Side Menu
        List<WebElement> sideMenu = drv.findElements(By.xpath("//ul[@id='box-apps-menu']/li[@class='app']"));
        System.out.println(sideMenu.size());
        //Clicking on them one by one and checking if the header is displayed
        for (int i=1;i<=sideMenu.size();i++) {
            drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]")).click();
            //"Ugly" workaround for wait
            Thread.sleep(500);
            //Asserting if the Header exists on the page, and if not, printing the corresponding message
            Assertions.assertTrue(isElementPresent(By.xpath("//div[@class='panel-heading']")), "Header is not present for the Side Menu item #" + i);
            //If the Header exists, printing its name
            System.out.println(drv.findElement(By.xpath("//div[@class='panel-heading']")).getText());

            //For each of the item in Side Menu preparing the list of the items in its Sub Menu
            List<WebElement> subMenu = drv.findElements(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li"));
            //If such Sub Menu items exist, clicking on them one by one and checking if the header is displayed
            if (0 < subMenu.size()) {
                for (int j=1;j<=subMenu.size();j++) {
                    drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li[" + j + "]")).click();
                    //Asserting if the Header exists on the page, and if not, printing the corresponding message
                    Assertions.assertTrue(isElementPresent(By.xpath("//div[@class='panel-heading']")), "   Header is not present for the SUB Menu item #" + j);
                    //If the Header exists, printing its name
                    System.out.println("   " + drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li[" + j + "]")).getText());
                }
            }
        }
    }


}
