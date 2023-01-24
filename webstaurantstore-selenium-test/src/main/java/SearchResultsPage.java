import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage {
	WebDriver driver;
    WebDriverWait wait;
    
    By productListing = By.id("product_listing");
    By productBoxContainer = By.id("ProductBoxContainer");
    By itemBtn = By.className("btn-container");

    public SearchResultsPage (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitForResults() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main")));
    }
    
    public ArrayList<WebElement> getProducts() {
    	ArrayList<WebElement> products = (ArrayList<WebElement>) driver.findElement(productListing).findElements(productBoxContainer);
        return products;
    }

    public void addLastItemToCart() {
    	ArrayList<WebElement> products = getProducts();
    	WebElement lastProductBtn = products.get(products.size() - 1).findElement(itemBtn);
    	lastProductBtn.click();
    }
}
