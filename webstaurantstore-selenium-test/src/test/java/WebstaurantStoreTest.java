import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebstaurantStoreTest {
	WebDriver driver;
	WebDriverWait wait;
	WebstaurantStoreHomePage homePage;
	SearchResultsPage searchResultsPage;
	CartPage cartPage;

	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		homePage = new WebstaurantStoreHomePage(driver);
		searchResultsPage = new SearchResultsPage(driver);
		cartPage = new CartPage(driver);
	}

	@Test
	public void searchAndVerify() {
		homePage.goTo();
		homePage.searchFor("stainless work table");
		searchResultsPage.waitForResults();
		ArrayList<WebElement> products = searchResultsPage.getProducts();
		for (WebElement product : products) {
			String productTitle = product.getText();
			assert productTitle.contains("Table");
		}
	}

	@Test(dependsOnMethods = "searchAndVerify")
	public void addToCart() {
		searchResultsPage.addLastItemToCart();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assert cartPage.isNotificationDisplayed();
	}

	@Test(dependsOnMethods = "addToCart")
	public void emptyCart() {
		cartPage.viewCart();
		cartPage.emptyCart();
		cartPage.confirmEmptyCart();
		assert cartPage.isCartEmpty();
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
