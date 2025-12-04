package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common_functions.Utils;

public class Login_Page {

	private WebDriver driver;
	private WebDriverWait wait;
	public Utils utils;
	public HomePage homePage;

	public By emailField = By.cssSelector("[id='username']");
	private By passwordfield = By.cssSelector("[id='password']");
	private By Submitbutton = By.xpath("(//button[@type=\"submit\"])[2]");

	public Login_Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		homePage = new HomePage(driver);
		this.utils = new Utils(driver, null);
	}

	public void enterEmail(String email) {
		WebElement emailElement = wait.until(ExpectedConditions.elementToBeClickable(emailField));
		emailElement.sendKeys(email);
	}

	public void enterPassword(String password) {
		WebElement emailElement = wait.until(ExpectedConditions.elementToBeClickable(passwordfield));
		emailElement.sendKeys(password);
	}

	public void LaunchSite() {
//		driver.get("https://timkends.syndigo.com/?idp=timkends_auth0_idp");
		driver.get("https://timkenfs.syndigo.com/?idp=timkenfs_auth0_go");
	}

	public void clickSubmit() {
		driver.findElement(Submitbutton).click();
	}

	public static String getProperty(String key) throws IOException {
		String filepath = System.getProperty("user.dir") + "/src/test/resources/" + "credentials.properties";
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(filepath);
		properties.load(fis);
		String val = (String) properties.get(key);
		fis.close();
		return val;
	}

	public void LogintoPIM(String UseCaseOwner) throws IOException {
		LaunchSite();
		String credentials = getProperty(UseCaseOwner);
		String[] abc = credentials.split(",");
		String username = abc[0];
		String password = abc[1];

		enterEmail(username);
		enterPassword(password);
		clickSubmit();
		homePage = new HomePage(driver);
	}
}
