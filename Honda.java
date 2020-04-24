package Day1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class Honda {

	public static void main(String[] args) throws InterruptedException {

		System.setProperty("webdriver.chrome.silentOutput", "true");
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// 1) Go to https://www.honda2wheelersindia.com/
		driver.get("https://www.honda2wheelersindia.com/");
		
		Actions act = new Actions(driver);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		String parentwindow = driver.getWindowHandle();
		
		Thread.sleep(3000);
		driver.findElementByXPath("//button[@class='close']").click();

		// 2) Click on scooters and click dio
		Thread.sleep(3000);
		driver.findElementById("link_Scooter").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//img[@src='/assets/images/thumb/dioBS6-icon.png']").click();
		
		// 3) Click on Specifications and mouseover on ENGINE
		Thread.sleep(3000);
		driver.findElementByXPath("//a[text()='Specifications']").click();
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//a[text()='ENGINE']")).click().perform();
		
		// 4) Get Displacement value
		Thread.sleep(3000);
		String dio_cc = driver.findElementByXPath("//div[contains(@class,'engine part-2')]/ul/li[3]/span[2]").getText().replaceAll("\\D", "");
		double diocc;
		if(dio_cc.length() >3) {
			dio_cc = dio_cc.substring(0, dio_cc.length() - 2);			
		}
		diocc = Double.parseDouble(dio_cc);
		System.out.println("DIO  Displacement Value : "+diocc);
		
		// 5) Go to Scooters and click Activa 125
		Thread.sleep(3000);
		driver.findElementById("link_Scooter").click();
		Thread.sleep(3000);
		driver.findElementByXPath("//img[@src='/assets/images/thumb/activa-125new-icon.png']").click();
		
		// 6) Click on Specifications and mouseover on ENGINE
		Thread.sleep(3000);
		driver.findElementByXPath("//a[text()='Specifications']").click();
		Thread.sleep(3000);
		act.moveToElement(driver.findElementByXPath("//a[text()='ENGINE']")).click().perform();
		
		// 7) Get Displacement value
		Thread.sleep(3000);
		String activa_cc = driver.findElementByXPath("//div[contains(@class,'engine part-4')]/ul/li[3]/span[2]").getText().replaceAll("\\D", "");
		double activacc = Double.parseDouble(activa_cc);
		System.out.println("ACTIVA 125CC  Displacement Value : "+activacc);
		
		// 8) Compare Displacement of Dio and Activa 125 and print the Scooter name having better Displacement.		
		if(diocc < activacc) {
			System.out.println("DIO is Best");
		}else {
			System.out.println("Activa 125 is Best");
		}
		
		// 9) Click FAQ from Menu
		Thread.sleep(3000);
		driver.findElementByXPath("(//a[text()='FAQ'])[1]").click();
		
		// 10) Click Activa 125 BS-VI under Browse By Product
		Thread.sleep(3000);
		driver.findElementByXPath("//a[text()='Activa 125 BS-VI']").click();
		
		// 11) Click Vehicle Price
		js.executeScript("window.scrollBy(0,300)");
		Thread.sleep(3000);
		driver.findElementByXPath("//a[text()=' Vehicle Price']").click();
		
		// 12) Make sure Activa 125 BS-VI selected and click submit
		boolean selected_veh = driver.findElementByXPath("//form[@id='formPriceMaster']/div[2]//select/option[4]").isSelected();
		System.out.println("Is Activa 125 BS-VI selected : " +selected_veh);
		Thread.sleep(3000);
		driver.findElementByXPath("//form[@id='formPriceMaster']//button").click();
		
		// 13) click the price link
		Thread.sleep(3000);
		driver.findElementByXPath("//table[@id='tblPriceMasterFilters']/tbody/tr/td[3]").click();
		
		// 14) Go to the new Window and select the state as Tamil Nadu and city as Chennai
		Set<String> win_handles = driver.getWindowHandles();
		List<String> li = new ArrayList<String>();
		li.addAll(win_handles);		
		String title = driver.switchTo().window(li.get(1)).getTitle();
		System.out.println("Page Title : " +title);
		
		Thread.sleep(3000);
		Select sel_state = new Select(driver.findElementByXPath("//select[@id='StateID']"));
		Thread.sleep(3000);
		sel_state.selectByVisibleText("Tamil Nadu");
		
		Thread.sleep(3000);
		Select sel_city = new Select(driver.findElementByXPath("//select[@id='CityID']"));
		Thread.sleep(3000);
		sel_city.selectByVisibleText("Chennai");
		
		// 15) Click Search
		Thread.sleep(3000);
		driver.findElementByXPath("//button[text()='Search']").click();		
		
		// 16) Print all the 3 models and their prices
		WebElement table = driver.findElementByXPath("//table[@id='gvshow']/tbody");
		List<WebElement> tr = table.findElements(By.tagName("tr"));
		for(int i = 1; i <= tr.size(); i++) {
			String model = driver.findElementByXPath("//table[@id='gvshow']/tbody/tr["+i+"]/td[contains(text(),'ACTIVA')]").getText();
			String price = driver.findElementByXPath("//table[@id='gvshow']/tbody/tr["+i+"]/td[contains(text(),'ACTIVA')]//following-sibling::td").getText();
			System.out.println(model+ "  " +price);
		}
		
		// 17) Close the Browser
		Thread.sleep(3000);
		driver.switchTo().window(li.get(1)).close();
		Thread.sleep(3000);
		driver.switchTo().window(parentwindow).close();

	}

}
