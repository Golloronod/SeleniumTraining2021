import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SecondSeleniumTest extends TestBase {

    public static WebDriver drv1;

    @BeforeEach
    void setUp(){

        ChromeOptions opt = new ChromeOptions();
//        opt.setAcceptInsecureCerts(true);
//        opt.setHeadless(true);

        WebDriverManager.chromedriver().setup();
        drv1 = new ChromeDriver(opt);
//        System.out.println(((HasCapabilities) drv1).getCapabilities());
        drv1.get(getBASE_URL());

    }

    @AfterEach
    void closeUp(){
        drv1.close();
    }

    @Test
    void test1(){
        // 1. Looking for the web element for the "Search Products" field
        System.out.println("1. CSS: " + drv1.findElement(By.cssSelector(".form-control")).getAttribute("placeholder"));

        // 2. Looking for the web element for the "Customer Service" field
        System.out.println("2. CSS: " + drv1.findElement(By.cssSelector("div#default-menu ul.nav.navbar-nav.navbar-right li.customer-service")).getText());

        // 3. Looking for the web element for the "Remember Me" checkbox
        WebDriverWait wait = new WebDriverWait(drv1, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".dropdown-toggle")));
        drv1.findElement(By.cssSelector(".fa.fa-user")).click();
        System.out.println("3. CSS: " + drv1.findElement(By.cssSelector("form.navbar-form div.checkbox input[type=checkbox]")).getAttribute("name"));

        // 4. Looking for the web element for the "Lost your password?" link
        System.out.println("4. CSS: " + drv1.findElement(By.cssSelector("ul.dropdown-menu li.text-center:last-child")).getText());

        // 5. Looking for the web element for the "$18" price in Campaign Products
        System.out.println("5. CSS: " + drv1.findElement(By.cssSelector("#box-campaign-products .price-wrapper .campaign-price")).getText());

        // 6. Looking for the web element for the "Red Duck" image in Popular Products
        System.out.println("6. CSS: " + drv1.findElement(By.cssSelector("section#box-popular-products div.image-wrapper img[alt*='Red Duck']")).getAttribute("alt"));

        // 7. Looking for the web element for the 4th price in Popular Products
        System.out.println("7. CSS: " + drv1.findElement(By.cssSelector("section#box-popular-products article.product-column:nth-child(4) div.price-wrapper span.price")).getText());

        // 8. Looking for the web element for the "Red Duck" image in Popular Products
        System.out.println("8. CSS: " + drv1.findElement(By.cssSelector("section#box-popular-products div.sticker.sale[title='On Sale']")).getText());

    }

    @Test
    void test2(){
        // 1. Looking for the web element for the "Search Products" field
        System.out.println("1. XPath: " + drv1.findElement(By.xpath("//*[@class='form-control']")).getAttribute("placeholder"));

        // 2. Looking for the web element for the "Customer Service" field
        System.out.println("2. XPath: " + drv1.findElement(By.xpath("//div[@id='default-menu']/ul[@class='nav navbar-nav navbar-right']/li[@class='customer-service']")).getText());

        // 3. Looking for the web element for the "Remember Me" checkbox
        drv1.findElement(By.xpath("//i[@class='fa fa-user']")).click();
        System.out.println("3. XPath: " + drv1.findElement(By.xpath("//form[@class='navbar-form']//div[@class='checkbox']//input[@name='remember_me']")).getAttribute("name"));

        // 4. Looking for the web element for the "Lost your password?" link
        System.out.println("4. XPath: " + drv1.findElement(By.xpath("//ul[@class='dropdown-menu']/li[@class='text-center'][last()]")).getText());

        // 5. Looking for the web element for the "$18" price in Campaign Products
        System.out.println("5. XPath: " + drv1.findElement(By.xpath("//section[@id='box-campaign-products']//div[@class='price-wrapper']/strong[@class='campaign-price']")).getText());

        // 6. Looking for the web element for the "Red Duck" image in Popular Products
        System.out.println("6. XPath: " + drv1.findElement(By.xpath("//section[@id='box-popular-products']//div[@class='image-wrapper']/img[@alt='Red Duck']")).getAttribute("alt"));

        // 7. Looking for the web element for the 4th price in Popular Products
        System.out.println("7. XPath: " + drv1.findElement(By.xpath("//section[@id='box-popular-products']//article[@class='product-column'][4]//div[@class='price-wrapper']/span[@class='price']")).getText());

        // 8. Looking for the web element for the "Red Duck" image in Popular Products
        System.out.println("8. XPath: " + drv1.findElement(By.xpath("//section[@id='box-popular-products']//div[contains(@class,'sticker sale') and contains(@title,'On Sale')]")).getText());

    }

}
