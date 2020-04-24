package Day1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Bigbasket {

	public static void main(String[] args) throws InterruptedException {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// 1) Go to https://www.bigbasket.com/
		driver.get("https://www.bigbasket.com/");

		JavascriptExecutor js = (JavascriptExecutor) driver;

		Actions act = new Actions(driver);

		WebDriverWait wait = new WebDriverWait(driver, 30);

		// 2) mouse over on Shop by Category
		act.moveToElement(driver.findElementByXPath("//li[contains(@class,'dropdown')]")).build().perform();

		// 3)Go to FOODGRAINS, OIL & MASALA --> RICE & RICE PRODUCTS
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("(//a[contains(text(),'Foodgrains, Oil & Masala')])[2]")).build().perform();

		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("(//a[contains(text(),'Rice & Rice Products')])[2]")).build().perform();

		// 4) Click on Boiled & Steam Rice
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("(//a[contains(text(),'Boiled & Steam Rice')])[2]")).click().build().perform();

		// 5) Choose the Brand as bb Royal
		js.executeScript("window.scrollBy(0,350)");
		Thread.sleep(3000);
		driver.findElementByXPath("//span[text()='bb Royal']/parent::label").click();

		// 6) Go to Ponni Boiled Rice - Super Premium and select 5kg bag from Dropdown
		Thread.sleep(3000);
		driver.findElementByXPath("((//div[@qa='product'])[3]//button)[1]").click();
		Thread.sleep(3000);
		driver.findElementByXPath("(//div[@qa='product'])[3]//ul/li[1]").click();

		// 7) print the price of Rice
		Thread.sleep(3000);
		String ricevalue = driver.findElementByXPath("(//div[@qa='product'])[3]//div[@qa='price']/h4/span[2]").getText();
		String rice_value = ricevalue.replaceAll("\\D", "");
		int rice_cost = Integer.parseInt(rice_value);
		System.out.println("Rice Cost : " + rice_cost);

		// 8) Click Add button
		Thread.sleep(3000);
		driver.findElementByXPath("(//div[@qa='product'])[3]//button[@qa='add']").click();

		// 9) Verify the success message displayed
		//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='arrow-marker']"))).click();
		/*
		 * Thread.sleep(3000); String successmsg = driver.findElementByXPath(
		 * "//div[@id='toast-container']//div[@class='toast-title]").getText();
		 * System.out.println(successmsg);
		 */

		Thread.sleep(3000);
		driver.findElementByXPath("//a[text()='Continue']").click();

		// 10) Type Dal in Search field and enter
		driver.findElementById("input").sendKeys("dal", Keys.ENTER);

		// 11) Go to Toor/Arhar Dal and select 2kg & set Qty 2
		WebElement thoor_qnty = driver.findElementByXPath("(//div[@qa='product'])[3]//div[@qa='qty']/input");
		thoor_qnty.clear();
		thoor_qnty.sendKeys("2");

		// 12) Print the price of Dal
		Thread.sleep(3000);
		String dalvalue = driver.findElementByXPath("(//div[@qa='product'])[3]//div[@qa='price']/h4/span[2]").getText();
		String dal_value = dalvalue.replaceAll("\\D", "");
		int dal_cost = Integer.parseInt(dal_value);
		System.out.println("dal Cost : " + dal_cost);

		// 13) Click Add button
		Thread.sleep(3000);
		driver.findElementByXPath("(//div[@qa='product'])[3]//button[@qa='add']").click();

		// 14) Mouse hover on My Basket
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='toast-container']//button"))).click();
		Thread.sleep(3000);
		act.moveToElement(driver.findElement(By.xpath("//a[@qa='myBasket']"))).perform();

		// 15) Validate the Sub Total displayed for the selected items
		Thread.sleep(3000);
		String first_qntity = driver.findElementByXPath("(//div[contains(@class,'product-qty')])[1]").getText();
		String first_qnty = first_qntity.substring(0, 2).replaceAll("\\D", "");
		int firstitm_quantity = Integer.parseInt(first_qnty);

		String ponni_rice = driver.findElementByXPath("(//span[@qa='priceMB'])[1]").getText();
		String ponnirice = ponni_rice.substring(0, ponni_rice.length() - 2);
		String ponni_value = ponnirice.replaceAll("\\D", "");
		int ponni_cost = Integer.parseInt(ponni_value);
		System.out.println("Ponni Cost : " + ponni_cost);

		String second_qntity = driver.findElementByXPath("(//div[contains(@class,'product-qty')])[2]").getText();
		String second_qnty = second_qntity.substring(0, 2).replaceAll("\\D", "");
		int seconditm_quantity = Integer.parseInt(second_qnty);

		Thread.sleep(3000);
		String toor_dal = driver.findElementByXPath("(//span[@qa='priceMB'])[2]").getText();
		String toordal = toor_dal.substring(0, toor_dal.length() - 2);
		String toor_value = toordal.replaceAll("\\D", "");
		int toor_cost = Integer.parseInt(toor_value);
		System.out.println("Toor Cost : " + toor_cost);

		Thread.sleep(3000);
		String total = driver.findElementByXPath("//span[@qa='subTotalMB']").getText();
		String tot = total.substring(0, total.length() - 2);
		String total_cost = tot.replaceAll("\\D", "");
		int sub_total = Integer.parseInt(total_cost);
		System.out.println("Subtotal : " + sub_total);

		System.out.println(firstitm_quantity * ponni_cost + seconditm_quantity * toor_cost);

		if (sub_total == ponni_cost + (toor_cost * seconditm_quantity)) {
			System.out.println("Subtotal is Correct");
		} else {
			System.out.println("subtotal is Incorrect");
		}

		// 17) Reduce the Quantity of Dal as 1
		Thread.sleep(3000);
		driver.findElementByXPath("(//button[@qa='decQtyMB'])[2]").click();
		
		Thread.sleep(3000);
		String first_qntity2 = driver.findElementByXPath("(//div[contains(@class,'product-qty')])[1]").getText();
		String first_qnty2 = first_qntity2.substring(0, 2).replaceAll("\\D", "");
		int firstitm_quantity2 = Integer.parseInt(first_qnty2);

		Thread.sleep(3000);
		String second_qntity2 = driver.findElementByXPath("(//div[contains(@class,'product-qty')])[2]").getText();
		String second_qnty2 = second_qntity2.substring(0, 2).replaceAll("\\D", "");
		int seconditm_quantity2 = Integer.parseInt(second_qnty2);

		int sub_total2 = firstitm_quantity2 * ponni_cost + toor_cost * seconditm_quantity2;
		
		// 18) Validate the Sub Total for the current items
		if (sub_total2 == sub_total) {			
			System.out.println("After decreasing quantity. The Subtotal is InCorrect");
		} else {
			System.out.println("Subtotal after decreasing the quantity : "+sub_total2);
			System.out.println("After decreasing quantity. The Subtotal is correct");
		}

		// 19) Close the Browser
		driver.close();

	}

}
