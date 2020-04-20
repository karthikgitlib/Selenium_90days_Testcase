package Day1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HewlettPackard {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get("https://store.hp.com/in-en/");

		try {
			driver.findElementByXPath("//span[contains(@class,'close-icon')]").click();
		} catch (Exception e) {
		}

		Actions act = new Actions(driver);

		JavascriptExecutor js = (JavascriptExecutor) driver;

		WebDriverWait wait = new WebDriverWait(driver, 30);

		// 2) Mouse over on Laptops menu and click on Pavilion
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//nav[@class='navigation']/ul/li[3]")).build().perform();
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("(//span[text()='Pavilion'])[1]/parent::a")).click().build().perform();

		// 3) Under SHOPPING OPTIONS -->Processor -->Select Intel Core i7
		js.executeScript("window.scrollBy(0,250)");
		driver.findElementByXPath("(//span[text()='Processor'])[2]").click();
		driver.findElementByXPath("(//span[text()='Intel Core i7'])/parent::a").click();

		// 4) Hard Drive Capacity -->More than 1TB
		js.executeScript("window.scrollBy(0,550)");
		Thread.sleep(3000);
		driver.findElementByXPath("(//span[text()='More than 1 TB'])/parent::a").click();

		// 5) Select Sort By: Price: Low to High
		Thread.sleep(3000);
		Select sel = new Select(driver.findElementById("sorter"));
		sel.selectByValue("price_asc");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'inside_closeButton')]"))).click();

		// 6) Print the First resulting Product Name and Price
		js.executeScript("window.scrollBy(0,450)");
		String pdt_name = driver.findElementByXPath("//strong[contains(@class,'product-item-name')]/a").getText();
		System.out.println("Laptop Name : " + pdt_name);
		String pdt_price = driver.findElementByXPath("(//span[@class='price'])[2]").getText();
		String price = pdt_price.replaceAll("\\D", "");
		int laptop_price = Integer.parseInt(price);
		System.out.println("Laptop Price : " + laptop_price);
		driver.findElementByXPath("//strong[contains(@class,'product-item-name')]/a").click();
		String selected_laptop = driver.getTitle();
		System.out.println("Selected Laptop Page Title : " + selected_laptop);

		// 7) Click on Add to Cart
		Thread.sleep(3000);
		driver.findElementByXPath("//button[@title='Add to Cart']").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//button[@class='action-close']").click();

		// 8) Click on Shopping Cart icon --> Click on View and Edit Cart
		Thread.sleep(3000);
		driver.findElementByXPath("//a[@title='Shopping Cart']").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//span[text()='View and edit cart']/parent::a").click();

		// 9) Check the Shipping Option --> Check availability at Pincode
		js.executeScript("window.scrollBy(0,300)");
		driver.findElementByXPath("//input[@name='pincode']").sendKeys("627811", Keys.TAB);
		Thread.sleep(3000);
		driver.findElementByXPath("//button[text()='check']").click();

		// 10) Verify the order Total against the product price
		String order_tot = driver.findElementByXPath("//td[@data-th='Order Total']//span").getText();
		String orderprice = order_tot.replaceAll("\\D", "");
		int order_total = Integer.parseInt(orderprice);
		int compared_price = laptop_price - order_total;

		// 11) Proceed to Checkout if Order Total and Product Price matches
		if (compared_price == 0) {
			Thread.sleep(3000);
			driver.findElementById("sendIsCAC").click();
		}

		// 12) Click on Place Order
		js.executeScript("window.scrollBy(0,750)");
		Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[text()='Place Order']/parent::button[@class='action primary checkout'])[3]"))).click();

		// 13) Capture the Error message and Print
		String errormsg = driver.findElementById("customer-email-error").getText();
		System.out.println("Error Message : " + errormsg);

		// 14) Close Browser
		driver.close(); 
	}

}
