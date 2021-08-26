import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ThirdSeleniumTest extends TestBase {

    public WebDriver drv;


    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        drv = new ChromeDriver();
        drv.get(getBASE_URL() + "admin");
        drv.findElement(By.xpath("//input[contains(@class,'form-control') and contains(@name,'username')]")).sendKeys(getADMIN_USER());
        drv.findElement(By.xpath("//input[contains(@class,'form-control') and contains(@name,'password')]")).sendKeys(getADMIN_PWD());
        drv.findElement(By.xpath("//button[contains(@class,'btn btn-default') and contains(@name,'login')]")).click();

        WebDriverWait wait = new WebDriverWait(drv, 5);
        //Waiting for the first SIde Menu item is clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='box-apps-menu']")));


    }

    @AfterEach
    void closeUp() {
        drv.close();
    }

    @Test
    void test1() {

        List<WebElement> sideMenu = drv.findElements(By.xpath("//ul[@id='box-apps-menu']/li[@class='app']"));
        for (int i=1;i<=sideMenu.size();i++) {
            drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]")).click();
            if (drv.findElement(By.xpath("//div[@class='panel-heading']")).isDisplayed()) {
                System.out.println(drv.findElement(By.xpath("//div[@class='panel-heading']")).getText());
            }   else {
                System.out.println("Header is not present for the Side Menu item #" + i);
            }

            List<WebElement> subMenu = drv.findElements(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li"));
            if (0 < subMenu.size()) {
                for (int j=1;j<=subMenu.size();j++) {
                    drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li[" + j + "]")).click();
                    if (drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li[" + j + "]")).isDisplayed()) {
                        System.out.println("   " + drv.findElement(By.xpath("//ul[@id='box-apps-menu']/li[" + i + "]//ul/li[" + j + "]")).getText());
                    }   else
                        System.out.println("   Header is not present for the SUB Menu item #" + j);
                }
            }
        }


    }
}
