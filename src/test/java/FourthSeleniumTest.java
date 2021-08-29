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

public class FourthSeleniumTest extends TestBase {

    public WebDriver drv;
    public WebDriverWait wait;


    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        drv = new ChromeDriver();
        wait = new WebDriverWait(drv, 5);
    }


    @AfterEach
    void closeUp() {
        drv.quit();
    }


    @Test
    void test1() {
        //Opening main page
        drv.get(getBASE_URL());
        //Accepting cookies
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='accept_cookies']"))).click();
        //Repeating the below 3 times so that 3 items were added to the cart
        for (int i=1;i<=3;i++) {
            //Waiting for the i-th element in the Popular Products to be clickable and clicking it
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//section[@id='box-popular-products']//article[" + i + "]"))).click();

            //Waiting for the "Add To Cart" button to be clickable and clicking it
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-success']"))).click();
            //Waiting for the proper number in the cart quantity
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@class='badge quantity']"), "" + i));

            if (i<3) {
                //Clicking to the logo for transferring to the main page
                drv.findElement(By.xpath("//a[@class='logotype']")).click();
            }
        }

        //Clicking on the Checkout icon for transferring to the cart
        drv.findElement(By.xpath("//div[@id='cart']")).click();
        //Waiting for the Shopping Cart to be loaded and verifying it contains 3 items
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//ul[@class='items list-unstyled']/li"), 3));
        //Waiting for the Payment Due amount is present on the page and saving this value to a variable
        String paymentDueInitial = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='table table-striped table-bordered data-table']//tr//td//span[@class='currency-amount']"))).getText();
        //Removing all 3 items in the cart one by one and verifying it changes the Payment Due amount
        for (int i=1;i<=3;i++) {
            //Removing always the 1st item in the list
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='items list-unstyled']/li[1]//button[@name='remove_cart_item']"))).click();
            //Waiting for the Shopping Cart to be loaded and verifying it contains for 1 item less after each removal
            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//ul[@class='items list-unstyled']/li"), 3-i));
            //Waiting for the Payment Due amount is present on the page and saving this value to a new variable
            String paymentDueNew = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='table table-striped table-bordered data-table']//tr//td//span[@class='currency-amount']"))).getText();
            //Verifying the Payment Due is changed by comparing Initial and New variables
            if (paymentDueInitial.equals(paymentDueNew)) {
                System.out.println("Something went wrong. Payment Due is not changed.");
            }
        }
        //Waiting for the "Back" button to be clickable on the page and clicking it
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-default']"))).click();
    }


    @Test
    void test2() {
        //Opening admin page
        drv.get(getBASE_URL() + "admin");
        //Entering credentials and clicking "Login" button
        drv.findElement(By.xpath("//input[contains(@class,'form-control') and contains(@name,'username')]")).sendKeys(getADMIN_USER());
        drv.findElement(By.xpath("//input[contains(@class,'form-control') and contains(@name,'password')]")).sendKeys(getADMIN_PWD());
        drv.findElement(By.xpath("//button[contains(@class,'btn btn-default') and contains(@name,'login')]")).click();
        //Waiting for the "Countries" menu item to be clickable and clicking it
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='box-apps-menu']/li/a//span[contains(@title, 'Countries')]"))).click();
        //Waiting for the "Add New Country" button to be clickable and clicking it
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='panel-action']/ul/li/a[@class='btn btn-default']"))).click();
        //Waiting for the 1st element with "arrow icon" to be clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='form-group col-md-6 required']/label[text()='Number (ISO 3166-1 numeric) ']/a/i")));
        //Saving the original window handling
        String origWindow = drv.getWindowHandle();
        //Preparing the list of all external links (elements with "arrow icons") on the page
        List<WebElement> externalLinks = drv.findElements(By.xpath("//form[@name='country_form']//a[contains(@href, '://')]"));
        //For each of the external links do...
        for (WebElement i : externalLinks) {
            //Clicking it
            i.click();
            //Switching to the new tab (window)
            for (String newWindow : drv.getWindowHandles()) {
                drv.switchTo().window(newWindow);
            }
            //Waiting for the header is present on the page
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[@id='firstHeading']")));
            //Printing the header text
            System.out.println(drv.findElement(By.xpath("//h1")).getText());
            //Closing this tab (window)
            drv.close();
            //Switching to the original window
            drv.switchTo().window(origWindow);
        }
    }


}
