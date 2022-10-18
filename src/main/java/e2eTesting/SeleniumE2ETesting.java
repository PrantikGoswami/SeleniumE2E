package e2eTesting;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumE2ETesting {

	private static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		//local driver path of Chrome driver downloaded from https://www.selenium.dev/documentation/webdriver/ 
		System.setProperty("webdriver.chrome.driver","C:\\Users\\p.goswami\\Documents\\Soft\\chromedriver_win32\\chromedriver.exe");		
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://clickdoc.de/cd-de/");
		
		List<WebElement> items = driver.findElements(By.cssSelector(".consent-button"));
		items.get(0).click();
		
		WebElement doctorType = driver.findElement(By.id("searchInput"));
		doctorType.sendKeys("Hausarzt");
		
		WebElement zipCode = driver.findElement(By.id("locationInput"));
		zipCode.sendKeys("50078");
		
		WebElement findButton = driver.findElement(By.cssSelector("button.search-button"));
		findButton.click();	
		
		Thread.sleep(5000);
		
		List<WebElement> capchaButtons = driver.findElements(By.cssSelector("button.ng-star-inserted"));
		for(WebElement capcha : capchaButtons) {
			if(capcha.getAttribute("class").contains("recaptcha-consent-btn")) {
				capcha.click();
				driver.findElement(By.id("search-query-typeahead")).sendKeys("Hausarzt");
				driver.findElement(By.id("search-location-typeahead")).sendKeys("50078");
				break;
			}
		}
		
		Thread.sleep(5000);
		
		WebElement onlineAppointment = driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[2]/div/div[1]/app-filter/div/div/div/div[4]/div/div"));
		onlineAppointment.click();
		
		WebElement search = driver.findElement(By.xpath("//*[@id=\"search\"]/div/div[2]/div/div[1]/app-filter/div/div/div/div[7]/div/button"));
		search.click();	
		
		Thread.sleep(5000);
		
		List<WebElement> contactCards = driver.findElements(By.tagName("app-contact-card"));
		WebElement timeSlot = null;
		outer: for(WebElement content : contactCards) {
			WebElement slotDetails = content.findElement(By.tagName("app-contact-slots-details"));
			if(slotDetails != null) {
				List<WebElement> timeSlotInfos = slotDetails.findElements(By.tagName("app-time-slot-info"));
				for(WebElement slot : timeSlotInfos) {
					timeSlot = slot.findElement(By.cssSelector("div.ng-star-inserted"));
					if(timeSlot.getAttribute("class").contains("time-cell")) {
						break outer;
					}
				}
			}
		}
		
		if(timeSlot != null) {
			timeSlot.click();
		}

		
		driver.quit();

	}

}
