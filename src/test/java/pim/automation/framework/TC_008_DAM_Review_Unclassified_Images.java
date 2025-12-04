package pim.automation.framework;

/************************************************
TC_008_DAM_Review_Unclassified_Images
Description - Verifies if at least one image is there in Unclassified_Images
************************************************/

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.Utils;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

public class TC_008_DAM_Review_Unclassified_Images extends BaseTest {
	public ExtentTest test;
	@Test(groups = { "BSAPIEowner" })
  public void GetClassified_Images() throws IOException, InterruptedException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);

		utils.waitForElement(() -> homePage.Moredetails_MarketingEnrich(), "clickable");
		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/**************************************************
		 * ***** Verify that logged in user is Digital Asset owner
		 **************************************************/
		WebElement currentloggedinuser = homePage.loggedin_User();
		System.out.println("Logged in user is  " + currentloggedinuser.getText());
		test.pass("Current user logged in is " + currentloggedinuser.getText());
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("Logged-in user should be Digital Asset Ownerr",currentloggedinuser.getText().contains("attributeownerdigitalassets.test1"));
		Thread.sleep(2000);
		/**************************************************
		 ***** Click on Enrich Digital Assets more details ****
		 **************************************************/
		WebElement detailsEnrichment = homePage.Moredetails_MarketingEnrich().getShadowRoot().findElement(By.cssSelector("#viewDetails > span"));
		detailsEnrichment.click();
		Thread.sleep(2000);
		test.pass("More details clicked on Enrich Digital Asset tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/**********************************
		* Get all the elements under Enrich digital assets
		************************************/
		List<WebElement> detailItems = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary")).getShadowRoot()
				.findElement(By.cssSelector("#moreDetails"))
				.findElements(By.cssSelector("my-todo-detail-view-list-item"));

		utils.waitForElement(() -> detailItems.get(0), "clickable");
		System.out.println("There are " + detailItems.size() + " elements ");
		
		List<String> expectedItems = Arrays.asList("Ready for transition", "DAM: Review 2D Line Drawing","DAM: Review Representative Image (Primary)", "DAM: Review Secondary Image",
				"DAM: Review Unclassified Images");
		Assert.assertEquals(detailItems.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		/**********************************************
		 * Verify in which row DAM: Review Unclassified Images found
		**********************************************/
		int matchedRowIndex = -1; 
		for (int i = 0; i < detailItems.size(); i++) {
		    WebElement summary = detailItems.get(i);
		    WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#button-text-box"));
		    String actualText = innerDiv.getAttribute("title").trim().replaceFirst("^\\d+\\s", "");
		    System.out.println("Item " + (i + 1) + ":--" + actualText);
		    Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));
		    if (actualText.contains("DAM: Review Unclassified Images")) {
		        matchedRowIndex = i + 1; 
		        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", innerDiv);
		        try {
		            innerDiv.click();
		        } catch (Exception e) {
		            js.executeScript("arguments[0].click();", innerDiv);
		        }
		        Thread.sleep(5000);
		        break;
		    }
		}
		if (matchedRowIndex != -1) {
		    System.out.println("Found 'DAM: Review Unclassified Images' in row: " + matchedRowIndex);
		    test.pass("Clicked on DAM: Review Unclassified Images which is found at row -- " + matchedRowIndex);
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} else {
		    System.out.println("'DAM: Review Unclassified Images' not found in any row.");
		    test.fail("'DAM: Review Unclassified Images' not found in any row.");
		    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    Assert.fail("'DAM: Review Unclassified Images' not found in any row.");
		}

		/***************************************
		 * ****Clicked on DAM: Review Unclassified Images  ****
		 ***************************************/
		utils.waitForElement(() -> searchPage.GridStructure(), "clickable");
		utils.waitForElement(() -> digitalssetPage.TotalRowsText(), "visible");
		test.pass("Search thing domain page displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/***************************************
		 * ****IF any rows found click on it else exit  ****
		 ***************************************/
		Actions actions = new Actions(driver);
		String text = digitalssetPage.TotalRowsText().getText();  // "1 - 2 / 2"

		String lastPart = text.substring(text.lastIndexOf("/") + 1).trim();
		int totalRows = Integer.parseInt(lastPart);
		System.out.println("Total rows in Home page is : " + totalRows);

		if (totalRows == 0) {
		    test.fail("There are NO data for the operation to perform. Exiting");
		    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    Assert.fail("There are NO data for the operation to perform. Exiting");
		    return;
		}
		else {
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

			utils.waitForElement(() -> searchPage.getgrid(), "clickable");

			System.out.println("Total rows after clicking on DAM: Review Unclassified Images " + arrrowsdefined.size());
			test.pass("Rows appeared after clicking on DAM: Review Unclassified Images");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			assertTrue("There should be results after selecting DAM: Review Unclassified Images", arrrowsdefined.size() > 0);

			WebElement RowByRow = arrrowsdefined.get(0);
			String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
			String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
			System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);
		
			WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
			actions.moveToElement(RowByRow).build().perform();
			Thread.sleep(2000);
			matidElement.click();
			Thread.sleep(3000);
			utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
			test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription+ " is selected for completion");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		    // --------- Find "Unclassified image" condition (fresh fetch every loop) ---------
		    List<WebElement> conditions = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
		    for (int i = 0; i < conditions.size(); i++) {
		        WebElement cond = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list")).get(i); 

		        String busscondname = cond.findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
		        System.out.println("Condition " + (i + 1) + " -- " + busscondname);

		        if (busscondname.contains("DAM: Review Unclassified Images")) {
		            cond.click();
		            break;
		        }
		    }
		// --------- Validate Unclassified Images Page ---------
				try {
				    Thread.sleep(3000);
				    utils.waitForElement(() -> digitalssetPage.ClassifiedImages_Grid(), "clickable");
				    test.pass("Unclassified Images page displayed");
				    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				} catch (Exception e) {
				    test.fail("Unclassified Images page DID NOT display");
				    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				}

				// --------- Validate Image Count ---------
				try {
				    List<WebElement> allInfo = digitalssetPage.ImageInfo();
				    System.out.println("There are " + allInfo.size() + " DAM: Review Unclassified Images");
				    test.pass("There are " + allInfo.size() + " DAM: Review Unclassified Images");
				    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				} catch (Exception e) {
				    System.out.println("There are NO DAM: Review Unclassified Images even though the record is in the list");
				    test.fail("There are NO DAM: Review Unclassified Images even though the record is in the list");
				    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				}
		// --------- Close the tab ---------
		digitalssetPage.getLastTabCloseButton().click();
		Thread.sleep(3000);
		}
	}
}