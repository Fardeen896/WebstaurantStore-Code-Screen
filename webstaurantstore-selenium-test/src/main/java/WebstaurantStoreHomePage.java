import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WebstaurantStoreHomePage {
	WebDriver driver;
    By searchInput = By.id("searchval");

    public WebstaurantStoreHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goTo() {
        driver.get("https://www.webstaurantstore.com/");
    }

    public void searchFor(String searchTerm) {
        driver.findElement(searchInput).sendKeys(searchTerm);
        driver.findElement(searchInput).submit();
    }
}
