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

    @AfterEach
    void closeUp(){
        drv1.close();
    }

    @Test
    void test1(){
        drv1.get("http://158.101.173.161/");
        WebDriverWait wait = new WebDriverWait(drv1, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".form-control")));

        // 1. Looking for the web element for the "Search Products" field
        System.out.println(drv1.findElement(By.cssSelector(".form-control")).getAttribute("placeholder"));

        // 2. Looking for the web element for the "Customer Service" field
        System.out.println(drv1.findElement(By.cssSelector("div#default-menu ul.nav.navbar-nav.navbar-right li.customer-service")).getText());

        // 3. Looking for the web element for the "Remember Me" checkbox
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dropdown-toggle")));
        drv1.findElement(By.cssSelector(".fa.fa-user")).click();
        System.out.println(drv1.findElement(By.cssSelector("form.navbar-form div.checkbox input[type=checkbox]")).getAttribute("name"));

        // 4. Looking for the web element for the "Lost your password?" link
        System.out.println(drv1.findElement(By.cssSelector("ul.dropdown-menu li.text-center:last-child")).getText());

        // 5. Looking for the web element for the "$18" price in Campaign Products
        System.out.println(drv1.findElement(By.cssSelector("#box-campaign-products .price-wrapper .campaign-price")).getText());

        // 6. Looking for the web element for the "Red Duck" image in Popular Products
        System.out.println(drv1.findElement(By.cssSelector("section#box-popular-products div.image-wrapper img[alt*='Red Duck']")).getAttribute("alt"));

        // 7. Looking for the web element for the 4th price in Popular Products
        System.out.println(drv1.findElement(By.cssSelector("section#box-popular-products article.product-column:nth-child(4) div.price-wrapper span.price")).getText());

        // 8. Looking for the web element for the "Red Duck" image in Popular Products
        System.out.println(drv1.findElement(By.cssSelector("section#box-popular-products div.sticker.sale[title='On Sale']")).getText());

    }

}