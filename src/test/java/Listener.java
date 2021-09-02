import org.openqa.selenium.*;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Listener extends AbstractWebDriverEventListener {

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        System.out.println("SEARCHING: " + by);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        System.out.println("FOUND OK\n");
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        System.out.println("EXCEPTION: " + throwable.getMessage().split(":")[0]);
        File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(tempFile.toPath(), (new File(System.currentTimeMillis() + "screen.png")).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screenshot is made.\n");
    }

}
