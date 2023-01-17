import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebstaurantStoreTest {
	WebDriver driver;
	WebDriverWait wait;
	ArrayList<WebElement> products;

	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	}

	@Test
	public void searchAndVerify() {
		// Go to the webstauraunt website
		driver.get("https://www.webstaurantstore.com/");

		// Search for "stainless work table"
		WebElement searchInput = driver.findElement(By.id("searchval"));
		searchInput.sendKeys("stainless work table");
		searchInput.submit();

		// Wait for all the elements to load in
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main")));
		
		// Making sure that every product has the word "Table" in its title
		WebElement productListing = driver.findElement(By.id("product_listing"));
		products = (ArrayList<WebElement>) productListing.findElements(By.id("ProductBoxContainer"));
		for (WebElement product : products) {
			WebElement details = product.findElement(By.id("details"));
			String productTitle = details.findElement(By.xpath("//*[@id=\"details\"]")).getText();
			assert productTitle.contains("Table");
		}
	}

	@Test(dependsOnMethods= "searchAndVerify")
	public void addToCart() {
		// Adding the last of the found items to the cart
		WebElement lastItem = products.get(products.size() - 1);
		WebElement lastItemBtn = lastItem.findElement(By.className("btn"));
		lastItemBtn.click();
		
		// Wait 1 second or else an empty cart will show
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//If the notification header exists, then the item was added to the cart
		WebElement notificationHeader = driver.findElement(By.className("notification__heading"));
		assert notificationHeader.isDisplayed();
		
	}

	@Test(dependsOnMethods= "addToCart")
	public void emptyCart() {
		// Wait until the cart element pops up, then empty the cart
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"watnotif-wrapper\"]")));
		WebElement viewCartBtn = driver.findElement(By.xpath("//*[@id=\"watnotif-wrapper\"]/div/p/div[2]/div[2]/a[1]"));
		viewCartBtn.click();
		
		// Click the empty cart button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("emptyCartButton")));
		WebElement emptyCartBtn = driver.findElement(By.className("emptyCartButton"));
		emptyCartBtn.click();
		
		// Confirm
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"td\"]/div[11]/div/div/div/footer/button[1]")));
		WebElement confirmEmptyCartBtn = driver.findElement(By.xpath("//*[@id=\"td\"]/div[11]/div/div/div/footer/button[1]"));
		confirmEmptyCartBtn.click();
		WebElement confirmationText = driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div/div[2]/p[1]"));
		// The confirmation text will tell you that there are no items in your cart, thus validating that it is empty
		assert confirmationText.isDisplayed();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
