package Day1;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Nykaa {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		//1) GOTO NYKAA.COM
		driver.get("https://www.nykaa.com/");

		Actions act = new Actions(driver);

		//2) Mouseover on Brands and Mouseover on Popular
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//a[text()='brands']")).perform();
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//div[@class='BrandsCategoryHeading']/a[1]")).perform();

		//3) Click L'Oreal PARIS
		driver.findElementByXPath("//div[@id='brandCont_Popular']/ul/li[5]/a").click();

		//4) Go to the newly opened window and check the title contains L'Oreal Paris
		Set<String> winHandles = driver.getWindowHandles();
		ArrayList<String> tabs = new ArrayList<String>(winHandles);
		String tab1_title = driver.switchTo().window(tabs.get(1)).getTitle();
		System.out.println("Tab 1 Title : "+tab1_title);
		
		//5) Click sort By and select customer top rated
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,600)");
		driver.findElementByXPath("//div[contains(@class,'sort-btn')]").click();
		driver.findElementByXPath("//span[contains(text(),'customer top rated')]/parent::div").click();
		
		//6) Click Category and click Shampoo
		js.executeScript("window.scrollBy(0,750)");
		Thread.sleep(3000);
		driver.findElementByXPath("//div[contains(text(),'Category')]").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//label[@for='chk_Shampoo_undefined'][1]").click();
		
		//7) check whether the Filter is applied with Shampoo
		WebElement filtered_shampoo = driver.findElementByXPath("//ul[contains(@class,'applied-filter-lists')]/li[contains(text(),'Shampoo')]");
		boolean shampoo_display = filtered_shampoo.isDisplayed();
		if (shampoo_display) {
			System.out.println("shampoo Category is selected");
		}
		else {
			System.err.println("Errors");
		}
		
		//8) Click on L'Oreal Paris Colour Protect Shampoo
		Thread.sleep(3000);
		driver.findElementByXPath("//span[contains(text(),'Colour Protect Shampoo')]").click();
		
		//9) GO to the new window and select size as 175ml
		Set<String> winHandles2 = driver.getWindowHandles();
		ArrayList<String> tabs2 = new ArrayList<String>(winHandles2);
		String tab2_title = driver.switchTo().window(tabs2.get(2)).getTitle();
		System.out.println("Tab 2 Title : "+tab2_title);
		driver.findElementByXPath("//span[text()='175ml']").click();
		
		//10) Print the MRP of the product
		String mrp = driver.findElementByXPath("(//span[@class='post-card__content-price-offer'])[1]").getText();
		String mrp_amount = mrp.replaceAll("\\D", "");
		int pdtprice = Integer.parseInt(mrp_amount);
		System.out.println("MRP  : "+pdtprice);
		
		//11) Click on ADD to BAG
		driver.findElementByXPath("(//button[contains(@class,'combo-add-to-btn')])[1]").click();
		
		//12) Go to Shopping Bag 
		driver.findElementByXPath("//div[contains(@class,'AddToBagbox')]").click();
		
		//13) Print the Grand Total amount
		String grandtotal = driver.findElementByXPath("(//div[contains(text(),'Grand Total')])[1]//following-sibling::div").getText();
		String grnd_total = grandtotal.replaceAll("\\D", "");
		int total_amt = Integer.parseInt(grnd_total);
		System.out.println("Grand Total : "+total_amt);
		
		//14) Click Proceed
		driver.findElementByXPath("//button[contains(@class,'proceed')]").click();
		
		//15) Click on Continue as Guest
		driver.findElementByXPath("//button[text()='CONTINUE AS GUEST']").click();
		
		//16) Print the warning message (delay in shipment)
		String errmsg = driver.findElementByXPath("//div[@class='message']").getText();
		System.out.println("Shipment Warning Message : "+errmsg);
		
		//17) Close all windows
		driver.quit();		

	}

}
