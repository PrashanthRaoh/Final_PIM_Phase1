package pim.automation.framework;

/************************************************
TC 02 - Validating the Data Quality Check - Marketing
Description - Enters the long and short description and completes the transaction
************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class TC_001_Marketing_Owner_Test extends BaseTest {
	public ExtentTest test;

	@Test(groups={"OwnerMarketing"})
	public void Marketing_Owner() throws IOException, InterruptedException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		Thread.sleep(5000);
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
		assertTrue("Logged-in user should be Marketing owner", currentloggedinuser.getText().contains("attributeownermarketing.test1"));

		/***************************************
		 * ------- Click on Marketing enrichment link in my todos --------- *
		 *************************************************/
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement innerDiv = homePage.enrichMarketingAttributelink().getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
		String actualText = innerDiv.getText().trim();
		
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", innerDiv);
		try {
			innerDiv.click(); // Try clicking the real inner part
			System.out.println("Clicked on " + actualText);
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", innerDiv);
		}
		System.out.println("Clicked on Marketing Enrich link");
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		System.out.println("Search page grid displayed");
		test.pass("Search page grid displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/**************************************************
		 ***** Click on the filter and search for PIM Taxonomy in the search field****************
		 **************************************************/
		searchPage.getFilterButton().click();
		System.out.println("Clicked on Filter button");
		Thread.sleep(2000);
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		test.pass("Filter button Clicked");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		searchPage.ATO_Marketing_Enrichment_Buss_Condition_Filter().click();
		Thread.sleep(2000);
		searchPage.Failed_Buss_Condition().click();
		Thread.sleep(2000);
		searchPage.Apply_btn_FailedEnrichMarketing().click();
		test.pass("Failed Enrich Marketing filter button applied");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after filter applied");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/************************************************** 
		 * --------- Get Row count------- *
		 ********************************************************/
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
		String SellableMaterialDescription = RowByRow
				.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		/*************************************************
		 * --------- Click on the materialid from the result------- *
		 ************************************************/
		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);

		/*************************************************
		 * --------- Capture % completion-It should be 0 ------ *
		 ************************************************/
		utils.waitForElement(searchPage::ProgressRing, "visible");
		String percentagecompletion = searchPage.ProgressRing().getText();
		System.out.println("Percentage completion of " + matid + " is " + percentagecompletion + " % ");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription
				+ " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		List<WebElement> bussrule = searchPage.BusinessRule().getShadowRoot().findElements(By.cssSelector("#accordion\\ 0 > div > div > div"));
		System.out.println("There are  " + bussrule.size() + " bussrule");
		Assert.assertEquals(bussrule.size(), 1, "The size of bussrule should be 1");

		/*************************************************
		 * --------- Click on the Marketing enrichment business rule------- *
		 ************************************************/
		bussrule.get(0).click();
		Thread.sleep(4000);
		utils.waitForElement(searchPage::shortDescription, "clickable");
		test.pass("Summary page of the material displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*************************************************
		 * --------- Enter mandatory fields to complete the transaction------- *
		 ************************************************/
		searchPage.shortDescription().sendKeys("Short description to complete transaction");
		Thread.sleep(1000);
		searchPage.LongDescription().sendKeys("This is long description");
		Thread.sleep(1000);
		test.pass("Entered mandatory field values on summary page");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*************************************************
		 * --------- Save the transaction------- *
		 ************************************************/
		searchPage.SaveTransaction_btn().click();
		Thread.sleep(3000);
		/*************************************************
		 * --------- Wait for the banner to appear
		 ************************************************/
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

	    WebElement banner = wait.until(drv -> {
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
		
		/*************************************************
		 * --------- Refresh to get the updated workflow ------- *
		 ************************************************/
	    WebElement Workflow_Refresh_btn = driver.findElement(By.cssSelector("#app")).getShadowRoot()
	    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
	    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
	    .findElement(By.cssSelector("#entityManageHeader")).getShadowRoot()
	    .findElement(By.cssSelector("#entityActions")).getShadowRoot()
	    .findElement(By.cssSelector("#toolbar")).getShadowRoot()
	    .findElement(By.cssSelector("#refresh")); 
	    
	    Workflow_Refresh_btn.click();
		Thread.sleep(5000);
		test.pass("Refreshed transaction to get the latest workflow status");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*************************************************
		 * --------- Verify if Percentage completion is 100% ------- *
		 ************************************************/
		utils.waitForElement(() -> searchPage.ProgressRing(), "visible");
		String percentagecompletion2 = searchPage.ProgressRing().getText();
		System.out.println("Percentage completion is now " + percentagecompletion2);

		int percfullcompletion = Integer.parseInt(percentagecompletion2);

		if (percfullcompletion == 100) {
			test.pass("Percentage completion is 100%");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
			test.fail("% completion value is not 100");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.assertEquals(percfullcompletion, 100, "% completion value is not 100");
		}

		/*************************************************
		 * --------- Click on the search thing bread crum ------- *
		 ************************************************/
		searchPage.Search_things_BreadCrum().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
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
				test.log(Status.FAIL,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

			}
		}
	}
}