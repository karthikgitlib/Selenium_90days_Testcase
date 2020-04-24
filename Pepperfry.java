package Day1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Pepperfry {

	public static void main(String[] args) throws InterruptedException, IOException {

		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		Actions act = new Actions(driver);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		//1) Go to https://www.pepperfry.com/
		driver.get("https://www.pepperfry.com/");
		Thread.sleep(3000);
		driver.findElementByXPath("//div[@id='regPopUp']/a[@class='popup-close']").click();
		
		//2) Mouseover on Furniture and click Office Chairs under Chairs
		act.moveToElement(driver.findElementByXPath("(//a[text()='Furniture'])[1]")).perform();
		Thread.sleep(3000);
		driver.findElementByXPath("//a[text()='Office Chairs']").click();
		
		//3) click Executive Chairs
		Thread.sleep(3000);
		driver.findElementByXPath("(//li[contains(@class,'clip-main-cat')])[2]//div").click();
		
		//4) Change the minimum Height as 50 in under Dimensions
		js.executeScript("window.scrollBy(0,650)");
		Thread.sleep(3000);
		WebElement chair_height = driver.findElementByXPath("(//input[@class='clipFilterDimensionHeightValue'])[1]");
		chair_height.clear();
		chair_height.sendKeys("50", Keys.ENTER);
		
		//5) Add "Poise Executive Chair in Black Colour" chair to Wishlist
		js.executeScript("window.scrollBy(0,500)");
		Thread.sleep(3000);
		driver.findElementByXPath("//a[@data-productname='Poise Executive Chair in Black Colour']").click();
		
		//6) Mouseover on Homeware and Click Pressure Cookers under Cookware
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//a[text()='Homeware']")).perform();
		Thread.sleep(3000);
		driver.findElementByXPath("//a[text()='Pressure Cookers']").click();
		
		//7) Select Prestige as Brand
		js.executeScript("window.scrollBy(0,350)");
		Thread.sleep(3000);
		driver.findElementByXPath("//label[@for='brandsnamePrestige']").click();
		
		//8) Select Capacity as 1-3 Ltr
		Thread.sleep(3000);
		js.executeScript("window.scrollBy(0,450)");
		Thread.sleep(3000);
		driver.findElementByXPath("//label[@for='capacity_db1_Ltr_-_3_Ltr']").click();
		
		//9) Add "Nakshatra Cute Metallic Red Aluminium Cooker 2 Ltr" to Wishlist
		Thread.sleep(3000);
		driver.findElementByXPath("//a[contains(@data-productname,'Nakshatra Cute Metallic')]").click();
		
		//10) Verify the number of items in Wishlist
		Thread.sleep(3000);
		driver.findElementByXPath("//div[@class='wishlist_bar']/span").click();
		
		//11) Navigate to Wishlist
		Thread.sleep(3000);
		driver.findElementByXPath("//div[@class='wishlist_bar']").click();
		
		//12) Move Pressure Cooker only to Cart from Wishlist
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='item_card_wrapper'])[2]//a[@class='addtocart_icon']"))).click();
		//Thread.sleep(3000);
		//driver.findElementByXPath("(//div[@class='item_card_wrapper'])[2]//a[@class='addtocart_icon']").click();
		
		//13) Check for the availability for Pincode 600128
		Thread.sleep(3000);
		driver.findElementByXPath("//input[@class='srvc_pin_text']").sendKeys("627811", Keys.ENTER);
		
		//14) Click Proceed to Pay Securely
		Thread.sleep(3000);
		driver.findElementByXPath("//a[@class='proceed_cta']").click();
		
		//15) Click Proceed to Pay
		
		//16) Capture the screenshot of the item under Order Item
		Thread.sleep(3000);
		WebElement item_img = driver.findElementByXPath("(//form[@id='cart_form']//img)[1]");
		
		
		/*
		 * TakesScreenshot tks_ss = ((TakesScreenshot)driver); File screenshotAs =
		 * tks_ss.getScreenshotAs(OutputType.FILE); //Move image file to new destination
		 * 
		 * //File SrcFile = new File("C:\\Users\\Karthik\\Desktop"); File DestFile=new
		 * File("C:\\Users\\Karthik\\Desktop\\Kutty Nai\\test.png");
		 * 
		 * //Copy file at destination
		 * 
		 * FileUtils.copyFile(screenshotAs, DestFile);
		 */
		 
		TakesScreenshot tks_ss = ((TakesScreenshot)driver);
		File screenshotAs = tks_ss.getScreenshotAs(OutputType.FILE);
		BufferedImage  fullImg = ImageIO.read(screenshotAs);

		// Get the location of element on the page
		Point point = item_img.getLocation();

		// Get width and height of the element
		int eleWidth = item_img.getSize().getWidth();
		int eleHeight = item_img.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", screenshotAs);

		// Copy the element screenshot to disk
		File screenshotLocation = new File("C:\\Users\\Karthik\\Desktop\\Kutty Nai\\test.png");
		FileUtils.copyFile(screenshotAs, screenshotLocation);
		
		//17) Close the browser
        driver.close();
	}

}
