import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecondSeleniumTest {

    public WebDriver drv1;

    @BeforeEach
    void setUp(){

        ChromeOptions opt = new ChromeOptions();
//        opt.setAcceptInsecureCerts(true);
//        opt.setHeadless(true);

        WebDriverManager.chromedriver().setup();
        drv1 = new ChromeDriver(opt);
//        System.out.println(((HasCapabilities) drv1).getCapabilities());
    }

//    @AfterEach
    void closeUp(){
        drv1.close();
    }

    @Test
    void test1(){
        drv1.get("http://158.101.173.161/");
        WebDriverWait wait = new WebDriverWait(drv1, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".form-control")));
//        drv1.findElement(By.name("q")).sendKeys("Selenium" + Keys.ENTER);
//        drv1.findElement(By.cssSelector(".form-control"));
//        Assertions.assertEquals("Selenium: Benefits, Uses, Side Effects, Dosage, and More", drv1.findElement(By.tagName("h3")).getText());

        // 1. Looking for the web element for the "Search Products" field
        System.out.println(drv1.findElement(By.cssSelector(".form-control")).hashCode());
//        System.out.println(drv1.findElement(By.cssSelector("[name=query]")).hashCode());

        // 2. Looking for the web element for the "Customer Service" field
        System.out.println(drv1.findElement(By.cssSelector(".customer-service")).hashCode());
//        System.out.println(drv1.findElement(By.cssSelector("li.customer-service")).hashCode());

        // 3. Looking for the web element for the "Remember Me" checkbox
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dropdown-toggle")));
        drv1.findElement(By.cssSelector(".fa.fa-user")).click();
        System.out.println(drv1.findElement(By.cssSelector("[name=remember_me]")).hashCode());

        // 4. Looking for the web element for the "Lost your password?" link
        System.out.println(drv1.findElement(By.cssSelector("#default-menu .text-center:last-child")).hashCode());

        // 5. Looking for the web element for the "$18" price in Campaign Products
        System.out.println(drv1.findElement(By.cssSelector("#box-campaign-products .price-wrapper .campaign-price")).hashCode());

        // 6. Looking for the web element for the "Red Duck" image in Popular Products
        System.out.println(drv1.findElement(By.cssSelector("section#box-popular-products div.image-wrapper img[alt*='Red Duck']")).hashCode());

        // 7. Looking for the web element for the 4th price in Popular Products
        System.out.println(drv1.findElement(By.cssSelector("section#box-popular-products article.product-column:nth-child(4) div.price-wrapper span.price")).hashCode());

        // 8. Looking for the web element for the "Red Duck" image in Popular Products
        System.out.println(drv1.findElement(By.cssSelector("section#box-popular-products div.sticker.sale[title='On Sale']")).hashCode());

    }

}
