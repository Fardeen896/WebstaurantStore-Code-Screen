import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {
	
	WebDriver driver;
    WebDriverWait wait;
    
    By notificationHeader = By.className("notification__action");
    By viewCartBtn = By.xpath("//*[@id=\"watnotif-wrapper\"]/div/p/div[2]/div[2]/a[1]");
    By emptyCartBtn = By.className("emptyCartButton");
    By emptyCartConfirmationBtn = By.xpath("//*[@id=\"td\"]/div[11]/div/div/div/footer/button[1]");
    By emptyConfirmationMsg = By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div/div[2]/p[1]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void viewCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='watnotif-wrapper']")));
        driver.findElement(viewCartBtn).click();
    }

    public void emptyCart() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartBtn));
        driver.findElement(emptyCartBtn).click();
    }
    
    public void confirmEmptyCart() {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartConfirmationBtn));
    	driver.findElement(emptyCartConfirmationBtn).click();
    }

    public boolean isNotificationDisplayed() {
        return driver.findElement(notificationHeader).isDisplayed();
    }

    public boolean isCartEmpty() {
        return driver.findElement(emptyConfirmationMsg).isDisplayed();
    }
    
}
