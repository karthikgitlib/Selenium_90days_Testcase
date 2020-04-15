package Day1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Myntra {

	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.myntra.com/");

		Actions act = new Actions(driver);
		WebElement womenmenu = driver.findElementByXPath("(//div[@class='desktop-navContent'])[2]/div/a");

		// HOVER ON MAIN MENU(WOMEN)
		Thread.sleep(3000);
		act.moveToElement(womenmenu).perform();

		// HOVER ON SUB MENU(JACKETS & COATS)
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//a[@href='/women-jackets-coats']")).click().perform();

		Thread.sleep(3000);
		String str = driver.findElementByXPath("(//main[@class='search-base']/div)[2]//span").getText();
		String text = str.replaceAll("\\D", "");
		int total_catg = Integer.parseInt(text);
		System.out.println(total_catg);

		// VALIDATE NO OF PRODUCTS IS SAME OR NOT
		String cat1_text = driver.findElementByXPath("//ul[@class='categories-list']/li[1]//span").getText();
		String cat1 = cat1_text.replaceAll("\\D", "");
		int jackets = Integer.parseInt(cat1);

		String cat2_text = driver.findElementByXPath("//ul[@class='categories-list']/li[2]//span").getText();
		String cat2 = cat2_text.replaceAll("\\D", "");
		int coats = Integer.parseInt(cat2);

		int total_cat = jackets + coats;
		System.out.println(total_cat);

		if (total_cat == total_catg) {
			System.out.println("Number of Category is same");
		}

		// SELECT COAT CATEGORY CHECKBOX
		act.moveToElement(driver.findElementByXPath("//input[@value='Coats']")).click().perform();

		// CLICK BRAND MORE LINK
		act.moveToElement(driver.findElementByXPath("//div[@class='brand-more']")).click().perform();

		// SEARCH AND SELECT MANGO BRAND, CLOSE POPUP
		Thread.sleep(3000);
		driver.findElementByXPath("//input[@class='FilterDirectory-searchInput']").sendKeys("Mango", Keys.TAB);
		Thread.sleep(3000);
		driver.findElementByXPath("//label[@class=' common-customCheckbox']//div[1]").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//span[@class='myntraweb-sprite FilterDirectory-close sprites-remove']").click();

		// CHECK All PRODUCTS ARE MANGO OR NOT
		List<WebElement> noofbrands = driver.findElementsByXPath("//h3[@class='product-brand']");
		int count = 0;
		for (WebElement brand : noofbrands) {
			if (brand.getText().equals("MANGO")) {
				count = count + 1;
			}
		}

		if (count == noofbrands.size()) {
			System.out.println("All are Mango Products");
		} else {
			System.out.println("error");
		}

		act.moveToElement(driver.findElementByXPath("//div[@class='sort-sortBy']")).perform();
		driver.findElementByXPath("//label[text()='Better Discount']").click();

		List<WebElement> discnt_prices = driver.findElementsByXPath("//span[@class='product-discountedPrice']");
		String firstitem = discnt_prices.get(0).getText();
		String firstitem_price = firstitem.replaceAll("\\D", "");
		int itemprice = Integer.parseInt(firstitem_price);
		System.out.println(itemprice);

		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//span[@class='product-discountedPrice'][1]")).perform();

		driver.findElementByXPath("(//span[text()='wishlist now'])[1]").click();

		driver.close();
	}
}
