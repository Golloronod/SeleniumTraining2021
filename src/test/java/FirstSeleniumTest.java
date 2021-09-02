import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirstSeleniumTest extends TestBase {

    public WebDriver drv1;

    @BeforeEach
    void setUp(){

        ChromeOptions opt = new ChromeOptions();
//        opt.setAcceptInsecureCerts(true);
//        opt.setHeadless(true);

        WebDriverManager.chromedriver().setup();
        drv1 = new ChromeDriver(opt);
//        System.out.println(((HasCapabilities) driver).getCapabilities());
    }

    @AfterEach
    void closeUp(){
        drv1.quit();
    }

    @Test
    void firstTest(){
        drv1.get("http://google.com");
        drv1.findElement(By.name("q")).sendKeys("Selenium" + Keys.ENTER);
        Assertions.assertEquals("Selenium: Benefits, Uses, Side Effects, Dosage, and More", drv1.findElement(By.tagName("h3")).getText());
    }

}
