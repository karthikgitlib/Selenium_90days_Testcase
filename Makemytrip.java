package Day1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Makemytrip {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.navigate().to("https://www.makemytrip.com/");

		// 2) Click Hotels
		driver.findElementByXPath("(//a[@href='https://www.makemytrip.com/hotels/'])[1]").click();

		// 3) Enter city as Goa, and choose Goa, India
		driver.findElementByXPath("//label[@for='city']").sendKeys("Goa", Keys.TAB);

		// 4) Enter Check in date as Next month 17th (May 17) and Check out as start
		// date+6
		DateFormat df = new SimpleDateFormat("dd");
		Date dateobj = new Date();
		String checkin = df.format(dateobj);
		Thread.sleep(3000);
		driver.findElementByXPath("//div[text()='" + checkin + "']").click();

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 6);
		int checkout = (calendar.get(Calendar.DATE));
		Thread.sleep(3000);
		driver.findElementByXPath("//div[text()='" + checkout + "']").click();

		// 5) Click on ROOMS & GUESTS and click 2 Adults and one Children(age 12). Click
		// Apply Button.
		Thread.sleep(3000);
		driver.findElementByXPath("//div[contains(@class,'hsw_inputBox roomGuests')]").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//ul/li[@data-cy='adults-2']").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//ul/li[@data-cy='children-1']").click();

		Select child_age = new Select(driver.findElementByClassName("ageSelectBox"));
		child_age.selectByIndex(1);

		driver.findElementByXPath("//button[text()='APPLY']").click();

		// 6) Click Search button
		driver.findElementByXPath("//button[text()='Search']").click();

		// 7) Select locality as Baga
		driver.findElementByXPath("//div[contains(@class, 'mmBackdrop wholeBlack')]").click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,550)");
		Thread.sleep(3000);
		driver.findElementByXPath("//label[text()='Baga']").click();

		// 8) Select 5 start in Star Category under Select Filters
		js.executeScript("window.scrollBy(0,850)");
		Thread.sleep(3000);
		driver.findElementByXPath("//label[text()='5 Star']").click();

		// 9) Click on the Second resulting hotel and go to the new window
		driver.findElementByXPath("//div[@class='infinite-scroll-component ']/div[2]").click();

		Set<String> win_handles = driver.getWindowHandles();
		ArrayList<String> tabs = new ArrayList<String>(win_handles);
		String tab1_title = driver.switchTo().window(tabs.get(1)).getTitle();
		System.out.println("Tab 1 Title : " + tab1_title);

		// 10) Print the Hotel Name
		String hotel_name = driver.findElementById("detpg_hotel_name").getText();
		System.out.println("Hotel Name : " + hotel_name);

		// 11) Click MORE OPTIONS link and Select 3Months plan and close
		driver.findElementByXPath("//span[text()='MORE OPTIONS']").click();
		driver.findElementByXPath("//table[@class='tblEmiOption']/tbody/tr[2]/td[6]/span").click();
		driver.findElementByXPath("//span[@class='close']").click();

		// 12) Click on BOOK THIS NOW
		driver.findElementById("detpg_headerright_book_now").click();

		// 13) Print the Total Payable amount
		driver.findElementByXPath("//div[contains(@class,'_Modal modalCont')]/span[@class='close']").click();

		String paymnt_amt = driver.findElementById("revpg_total_payable_amt").getText();
		String amt = paymnt_amt.replaceAll("\\D", "");
		int booking_amt = Integer.parseInt(amt);
		System.out.println("Total Payable : " + booking_amt);

		// 14) Close the browser
		driver.quit();

	}

}
