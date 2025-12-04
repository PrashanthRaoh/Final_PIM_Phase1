package pim.automation.framework;

/************************************************
TC 08 - Verify user should be able to approve entity pending for "Marketing Workflow".
Description - Assigns the user and complete the record transaction
************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.Utils;
import pages.HomePage;
import pages.SearchPage2;

public class TC_002_MarketingWorkFlowApproval extends BaseTest {

	public ExtentTest test;

	@Test(groups={"OwnerMarketing"})
	public void MarketingWorkFlowApproval() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);

		utils.waitForElement(() -> homePage.enrichMarketingAttributelink(), "clickable");
		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		
		/**************************************************
		 ***** Verify that logged in user is Marketing owner****************
		 **************************************************/
		WebElement currentloggedinuser = homePage.loggedin_User();
		System.out.println("Logged in user is  " + currentloggedinuser.getText());
		test.pass("Current user logged in is " + currentloggedinuser.getText());
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("Logged-in user should be Marketing owner",
				currentloggedinuser.getText().contains("attributeownermarketing.test1"));
		Thread.sleep(5000);

		/**************************************************
		 ***** Click on Marketing Enrichment in workflow ****
		 **************************************************/
		WebElement detailsEnrichment = homePage.Moredetails_MarketingEnrich().getShadowRoot().findElement(By.cssSelector("#viewDetails > span"));
		detailsEnrichment.click();
		Thread.sleep(5000);

		/**************************************************
		 ***** Click on Ready for transisition link****
		 **************************************************/
		homePage.ReadyForTransistion_Market_Enrich().click();
		System.out.println("Clicked on Ready for Transistion on Marketing enrichment more details");
		Thread.sleep(5000);
		utils.waitForElement(searchPage::getgrid, "clickable");
		System.out.println("Search page grid displayed");
		test.pass("Search page grid displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/**************************************************
		 ***** Click on any one of the entity****
		 **************************************************/
		Actions actions = new Actions(driver);
		WebElement rowsredefined = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
				.findElement(By.cssSelector("#grid"));

		List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		System.out.println("Total rows after entering the wide range filter criteria -- " + arrrowsdefined.size());
		test.pass("Rows after entering 0s appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after applying filters", arrrowsdefined.size() > 0);

		WebElement RowByRow = arrrowsdefined.get(0);
		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		/************************************************** --------- 
		 * Click on the materialid from the result------- *
		 ************************************************/
		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(5000);
		utils.waitForElement(() -> searchPage.ProgressRing(), "visible");
		String percentagecompletion = searchPage.ProgressRing().getText();
		System.out.println("Percentage completion of " + matid + " is " + percentagecompletion + " % ");

		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription
				+ " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		searchPage.AssignUser().sendKeys("Blessy");
		Thread.sleep(2000);

		searchPage.Done_Button().click();
		Thread.sleep(8000);
		/*************************************************
		 * --------- Wait for the banner to appear
		 ************************************************/
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
	    Function<WebDriver, WebElement> getBannerElement = drv -> {
	        try {
	            return drv.findElement(By.cssSelector("#app")).getShadowRoot()
	                .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
	                .findElement(By.cssSelector("#pebbleAppToast > pebble-echo-html")).getShadowRoot()
	                .findElement(By.cssSelector("#bind-html"));
	        } catch (Exception e) {
	            return null;
	        }
	    };

	    WebElement banner = wait1.until(drv -> {
	        WebElement el = getBannerElement.apply(drv);
	        return (el != null && el.isDisplayed()) ? el : null;
	    });

	    String bannerText = banner.getText();
	    System.out.println("✅ Banner appeared with the text : " + bannerText);
		
	    Thread.sleep(6000);
	    test.pass("Data Saved");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		test.pass("Saved the transaction");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		/***********************************
		 *  Get action workflow compelte msg
		 ***********************************/		
		wait.until(ExpectedConditions.visibilityOf(searchPage.Actionworkflow_Message()));
		String workflowMsg = searchPage.Actionworkflow_Message().getText();
		System.out.println("Workflow message is " + workflowMsg);
		Assert.assertTrue(workflowMsg.contains("There are no active workflows in the current context for you"));
		test.pass("Assigned the work flow - Completed confirmation message is  - " + workflowMsg);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*************************************************
		 * --------- Click on the search thing bread crum ------- *
		 ************************************************/
		searchPage.Search_things_BreadCrum().click();
		Thread.sleep(2000);
		utils.waitForElement(() ->searchPage.getgrid(), "clickable");
		test.pass("Navigated back to search thing ");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*************************************************
		 * --------- Verify the record again in the search thing. It should not be listed ------- *
		 ************************************************/
		searchPage.searchthingdomain_Input_Mat_Id().click();
		searchPage.searchthingdomain_Input_Mat_Id().clear();
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid);
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
		Thread.sleep(5000);

		try {
			String txt = searchPage.rowsdisplayedtext().getText();
			String result = txt.split(" / ")[1];
			int zerorows = Integer.parseInt(result);
			System.out.println(zerorows);
			Assert.assertEquals(zerorows, 0);
			test.pass(matid + " completion is 100%. Hence not visible");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		} catch (Exception e) {
			WebElement rowsredefined2 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
					.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
					.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
					.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
					.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
					.findElement(By.cssSelector("#grid"));
			List<WebElement> arrrowsdefined2 = rowsredefined2.getShadowRoot().findElements(By.cssSelector(
					"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div> div.ag-row.ag-row-even.ag-row-level-0"));

			if (arrrowsdefined2.size() > 0) {
				System.out.println("Records found for the search criteria");
				test.fail(matid + " completion is NOT 100%. Pleaes verify");
				test.log(Status.FAIL,
						MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}
	}
}