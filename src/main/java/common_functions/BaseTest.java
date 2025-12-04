package common_functions;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import pages.HomePage;
import pages.Login_Page;
import java.util.Date;

public class BaseTest {
	public static WebDriver driver;
	public static Utils utils;
	public static Login_Page loginPage;
	public HomePage homePage;
	public static ExtentReports extentreport;
	protected  static ExtentTest extentTest;
	public static final String filepathname = "target/PIM_Report.html";
	public static String reportDirPath;
	public static String screenshotDirPath;

	public void BaseTest() {
		this.driver = driver;
	}
	
	
//	public void intitializeReport() {
//		extentreport = new ExtentReports();
//		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filepathname);
//		sparkReporter.config().setTheme(Theme.DARK);
//		sparkReporter.config().setDocumentTitle("PIM Automation Report");
//		extentreport.attachReporter(sparkReporter);
//	}
	@BeforeSuite
	public void initReport() {
		String timestamp = new SimpleDateFormat("yyyyMM_dd_HHmmss").format(new Date());
		reportDirPath = System.getProperty("user.dir")+ "/Reports/Report_" + timestamp;
		screenshotDirPath = reportDirPath + "/Screenshots";
		
		new File(screenshotDirPath).mkdirs();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportDirPath + "/PIM_Report.html");
		sparkReporter.config().setTheme(Theme.STANDARD);
		extentreport = new ExtentReports();
		extentreport.setSystemInfo("Environment", "QA");
		extentreport.setSystemInfo("User", System.getProperty("user.name"));
		extentreport.attachReporter(sparkReporter);
	}
	
	@AfterSuite
	public void publishReport() throws IOException {
		extentreport.flush();
		Desktop.getDesktop().browse(new File(reportDirPath + "/PIM_Report.html").toURI());
	}

	@Parameters("UseCaseOwner")
	@BeforeClass
	public void setUp(String UseCaseOwner) throws IOException {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.manage().window().maximize();
		utils = new Utils(driver,extentTest);
		loginPage = new Login_Page(driver);
		loginPage.LogintoPIM(UseCaseOwner);
		homePage = new HomePage(driver);
	}
	
//	@AfterClass
	public void Logout() throws InterruptedException {
		homePage.AppHeader_Administrator().click();
		Thread.sleep(1000);
		homePage.Logout_btn().click();
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait3.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[id='username']"))));
		Thread.sleep(2000);
		driver.quit();
	}
}