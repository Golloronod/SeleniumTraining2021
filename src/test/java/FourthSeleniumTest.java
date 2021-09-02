import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.Random;

public class FourthSeleniumTest extends TestBase {

    public void selectRandomProduct() {
        //Preparing the list of all products in the Popular Products section
        List<WebElement> allProducts = drv.findElements(By.xpath("//section[@id='box-popular-products']/div[@class='listing products']/article/a"));
        //Declaring new variable with the type of Random
        Random rand = new Random();
        //Selecting the random integer number from the total amount of the Products
        int randomProduct = rand.nextInt(allProducts.size());
        //Clicking on the randomly selected Product
        allProducts.get(randomProduct).click();
    }


    @BeforeEach
    void setUp() {
        drv.get(getBASE_URL());
    }


    @AfterEach
    void closeUp() {
        drv.quit();
    }


    @Test
    void test1() {
        //Accepting cookies if they exist
        acceptCookies();
        //Repeating the below actions 3 times so that 3 items were added to the cart
        for (int i=1;i<=3;i++) {
            //Selecting random product from the Popular Products list
            selectRandomProduct();
            //Waiting for the "Add To Cart" button to be clickable and clicking it
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-success']"))).click();
            //Waiting for the proper number in the cart quantity
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@class='badge quantity']"), "" + i));

            if (i<3) {
                //Clicking to the logo for transferring to the main page
                drv.get(getBASE_URL());
            }
        }

        //Clicking on the Checkout icon for transferring to the cart
        drv.findElement(By.xpath("//div[@id='cart']")).click();
        //Waiting for the Shopping Cart to be loaded
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@class='items list-unstyled']")));
        //Saving the list of items in the cart
        List<WebElement> itemsInCart = drv.findElements(By.xpath("//ul[@class='items list-unstyled']/li//button[@name='remove_cart_item']"));
        //Waiting for the Payment Due amount is present on the page and saving this value to a variable
        String paymentDueInitial = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='table table-striped table-bordered data-table']//tr//td//span[@class='currency-amount']"))).getText();
        System.out.println("Payment Due Initial: " + paymentDueInitial);
        //Removing all items in the cart one by one and verifying it changes the Payment Due amount
        for (int i=1;i<=itemsInCart.size();i++) {
            //Removing always the 1st item in the list
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@class='items list-unstyled']/li[1]//button[@name='remove_cart_item']"))).click();
            //Waiting for the Shopping Cart to be loaded and verifying it contains for 1 item less after each removal
            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//ul[@class='items list-unstyled']/li"), itemsInCart.size()-i));
            //Waiting for the Payment Due amount is present on the page
            wait.until(ExpectedConditions.stalenessOf(drv.findElement(By.xpath("//table[@class='table table-striped table-bordered data-table']//tr//td//span[@class='currency-amount']"))));
            //Saving and comparing the new Payment Due amount for all except the last removal iteration
            if (i<itemsInCart.size()) {
                //Saving the value of the Payment Due amount to a new variable
                String paymentDueNew = drv.findElement(By.xpath("//table[@class='table table-striped table-bordered data-table']//tr//td//span[@class='currency-amount']")).getText();
                System.out.println("Payment Due New: " + paymentDueNew);
                //Verifying the Payment Due is changed by comparing Initial and New variables
                if (paymentDueInitial.equals(paymentDueNew)) {
                    System.out.println("Something went wrong. Payment Due is not changed.");
                }
            } else
                System.out.println("Payment Due New: USD $00.00");

        }
        //Waiting for the "Back" button to be clickable on the page and clicking it
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='btn btn-default']"))).click();
    }


    @Test
    void test2() {
        //Opening admin page
        loginToAdmin();
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
