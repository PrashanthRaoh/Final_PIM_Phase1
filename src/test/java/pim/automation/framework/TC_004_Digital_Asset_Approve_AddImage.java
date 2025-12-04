package pim.automation.framework;

/************************************************
TC 09 - Verify user should be able to approve entity pending for "Digital Asset Workflow"
Description - Adds Image DAM: Review Representative Image (Primary)
************************************************/

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class TC_004_Digital_Asset_Approve_AddImage extends BaseTest {
	public ExtentTest test;

	@Test(groups = { "DigitalAssetowner" })
	public void DigitalAsset() throws IOException, InterruptedException {
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
		* Verify in which row DAM: Review 2D Line Drawing was found
		************************************/
		List<WebElement> detailItems = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
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
		 * Verify in which row DAM: Review 2D Line Drawing was found
		**********************************************/
		int matchedRowIndex = -1; 
		for (int i = 0; i < detailItems.size(); i++) {
		    WebElement summary = detailItems.get(i);
		    WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#button-text-box"));
		    String actualText = innerDiv.getAttribute("title").trim().replaceFirst("^\\d+\\s", "");
		    System.out.println("Item " + (i + 1) + ":--" + actualText);
		    Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));
		    if (actualText.contains("DAM: Review 2D Line Drawing")) {
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
		    System.out.println("Found 'DAM: Review 2D Line Drawing' in row: " + matchedRowIndex);
		} else {
		    System.out.println("'DAM: Review 2D Line Drawing' not found in any row.");
		}
		test.pass("Clicked on DAM: Review 2D Line Drawing which is found at row -- " + matchedRowIndex);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/***************************************
		 * ****Clicked on DAM: Review 2D Line Drawing  ****
		 ***************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/**************************************************
		 * --------- Select filters for rows not having images------- *
		 ********************************************************/
		digitalssetPage.AdvanceSearch_Dropdown().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> digitalssetPage.generalDropdown_First(), "clickable");
		test.pass("Advance Search option clicked");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);
		digitalssetPage.generalDropdown_second().click();
		Thread.sleep(1000);
		digitalssetPage.HavingNoImagesFilterDropdownValue().click();
		Thread.sleep(1000);

		/**************************************************
		 * --------- Select relationship dropdown------- *
		 ********************************************************/
		digitalssetPage.RelationshipMaindropdown_Obj().click();
		Thread.sleep(500);
		digitalssetPage.HasImagesDropdownvalue().click();
		Thread.sleep(500);
		test.pass("Has No images filter value set");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Apply_btn_onFilter().click();
		Thread.sleep(5000);
		/**************************************************
		 * --------- wait for rows to appear after Filtering data with No Images------* *
		 ********************************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page No images records");
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

		System.out.println("Total rows after entering setting the filter criteria -- " + arrrowsdefined.size());
		test.pass("Rows after setting the filter criteria appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after applying filters", arrrowsdefined.size() > 0);

		WebElement RowByRow = arrrowsdefined.get(1);
		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);
		
		/**************************************************
		 * --------- Apply filter for BSA PIE with staus Pending ------- *
		 ************************************************/
		searchPage.getFilterButton().click();
		System.out.println("Clicked on Filter button");
		Thread.sleep(2000);
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		
		searchPage.Search_MaterialType().sendKeys("BSA PIE Sellable Product Status");
		Thread.sleep(5000);
		
		digitalssetPage.SellableProductStatus().click();
		Thread.sleep(5000);
		digitalssetPage.OnHoldSystem_dropdownvalue().click();
		Thread.sleep(2000);
		digitalssetPage.Status_Apply_btn().click();
		Thread.sleep(5000);
		/**************************************************
		 * --------- Click on the materialid from the result------- *
		 ************************************************/
		WebElement rowsApplyfilter = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
				.findElement(By.cssSelector("#grid"));

		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		
		List<WebElement> status_Pending_rows = rowsApplyfilter.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		System.out.println("Total rows after applying Pending status filter criteria -- " + status_Pending_rows.size());
		test.pass("Total rows after applying Pending status filter criteria is "  + status_Pending_rows.size());
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after applying filters", arrrowsdefined.size() > 0);

		WebElement firstrow = status_Pending_rows.get(1);
		String SellableMaterialDescription_status = firstrow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid_status = firstrow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid_status + " Material Description --" + SellableMaterialDescription_status);		
		
		WebElement matidElement_status = firstrow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(firstrow).build().perform();
		Thread.sleep(2000);
		matidElement_status.click();
		Thread.sleep(5000);
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		Thread.sleep(2000);
		test.pass("Material ID -- " + matid_status + " Material Description --" + SellableMaterialDescription_status+ " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*************************************************
		 * --------- Capture % completion-It should be 0 ------ *
		 ************************************************/
		utils.waitForElement(() -> searchPage.ProgressRing(), "visible");
		String percentagecompletion = searchPage.ProgressRing().getText();
		System.out.println("Percentage completion of " + matid + " is " + percentagecompletion + " % ");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription + " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/**************************************************
		 * --------- Fill up the business condition-DAM: Review Representative Image (Primary)------ *
		 ************************************************/
		List<WebElement> conditions = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
		for (int i = 0; i < conditions.size(); i++) {
			String busscondname = conditions.get(i).findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
			System.out.println("Condition " + (i + 1) + " -- " + busscondname );
			if(busscondname.contains("DAM: Review Representative Image (Primary)")) {
				conditions.get(i).click();
				break;
			}
		}
		/*************************************************
		 * --------- Wait for the image required drop down after clicking on Review Representative Image (Primary) ------ *
		 ************************************************/
		utils.waitForElement(() -> digitalssetPage.primary_Image_Required_dropdown_obj(), "clickable");
		test.pass("Clicked on DAM: Review Representative Image (Primary) and arrived on Manage attributes page");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*************************************************
		 * --------- Click Next ------ *
		 ************************************************/
		digitalssetPage.Next_btn().click();
		Thread.sleep(3000);
		/*******************
		 * Wait for the More actions drop down to appear
		*******************/
		utils.waitForElement(() -> digitalssetPage.MoreActions_Dropdown_1(), "clickable");
		Thread.sleep(1000);
		test.pass("More actions page displayed to attach a image");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*******************************
		 * Check for how many images attached before adding a image
		*******************************/
		String totalimages_selected_txt = digitalssetPage.txt_Images_Selected().getText();
		String img_selcted = totalimages_selected_txt.split(" / ")[1];
		int totalsearchimagescount = Integer.parseInt(img_selcted);
		System.out.println("There are " + totalsearchimagescount + " before adding the image " );
		Assert.assertEquals(totalsearchimagescount, 0);
		
		/*******************************
		 * Click Add images drop down value
		*******************************/
		digitalssetPage.MoreActions_Dropdown_2().click();
		Thread.sleep(2000);
		digitalssetPage.AddImage_dropdownValue().click();
		Thread.sleep(5000);
		utils.waitForElement(() -> digitalssetPage.Search_Images_input(), "clickable");
		test.pass("Arrived at adding image page");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Search_Images_input().sendKeys("primary",Keys.ENTER);
		Thread.sleep(3000);
		utils.waitForElement(() -> digitalssetPage.First_Image_checkbox(), "clickable");
		/*******************************
		 * Select the first image and save
		*******************************/
		digitalssetPage.First_Image_checkbox().click();
		Thread.sleep(2000);
		test.pass("Images with the filters appeared and is selected");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Save_btn_Add_Image().click();
		utils.waitForElement(() -> digitalssetPage.MoreActions_Dropdown_1(), "clickable");
		Thread.sleep(2000);
		/*******************************
		 * Check for how many images attached after adding a image
		*******************************/
		String totalimages_selected_txt_after = digitalssetPage.txt_Images_Selected().getText();
		String img_selcted_after = totalimages_selected_txt_after.split(" / ")[1];
		int totalsearchimagescount_after = Integer.parseInt(img_selcted_after);
		System.out.println("There are " + totalsearchimagescount_after + " after adding the image " );
		Assert.assertTrue(totalsearchimagescount_after>0);
		
		/*************************************************
		 * --------- After approving and adding the image check the % now ------ *
		 ************************************************/
		utils.waitForElement(() -> searchPage.ProgressRing(), "visible");
		String percentagecompletion_Allprocess = searchPage.ProgressRing().getText();
		System.out.println("Percentage completion after approving the stages for " + matid + " is " + percentagecompletion_Allprocess + " % ");
		test.pass("Percentage completion after adding image in the DAM: Review Representative Image (Primary) stages is " + percentagecompletion_Allprocess);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*************************************************
		 * --------- Again click on Back ------ *
		 ************************************************/
		digitalssetPage.Back_btn().click();
		Thread.sleep(2000);
		System.out.println("Back button clicked");
		utils.waitForElement(() -> digitalssetPage.primary_Image_Required_dropdown_obj(), "clickable");
		test.pass("Back button clicked to approve the transaction");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		digitalssetPage.primary_Image_Required_dropdown_obj().click();
		Thread.sleep(2000);
		digitalssetPage.ImageRequired_Yes().click();
		Thread.sleep(2000);
		
		digitalssetPage.ApprovePrimaryImagedropdown_obj().click();
		Thread.sleep(2000);
		digitalssetPage.Approve_Primary_Image_dropdownvalue().click();
		Thread.sleep(2000);
		/*************************************************
		 * --------- Save ------ *
		 ************************************************/
		digitalssetPage.AddPrimaryImage_Save_btn().click();
		Thread.sleep(5000);
		/*************************************************
		 * --------- Check the % completion after approving again------ *
		 ************************************************/
		utils.waitForElement(() -> searchPage.ProgressRing(), "visible");
		String percentagecompletion_Approvingagain = searchPage.ProgressRing().getText();
		System.out.println("Percentage completion after approving the image primary again clicking on back button for " + matid + " is " + percentagecompletion_Approvingagain + " % ");
		test.pass("Percentage completion after approving the image primary again clicking on back button is -- " + percentagecompletion_Approvingagain);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*******************
		 * Close DAM review Primary tab
		*******************/
		digitalssetPage.Close_DAM_review_Primary_tab().click();
		Thread.sleep(3000);
		
		/**********************************************
		 * From Summary tab select DAM: Review Representative Image (Primary) business condition
		**********************************************/
		WebElement summarybcs = driver.findElement(By.cssSelector("#app")).getShadowRoot()
		.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
		.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
		.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
		.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
		.findElement(By.cssSelector("[id^='rock-entity-summary-component-rs']")).getShadowRoot()
		.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
		.findElement(By.cssSelector("#rock-entity-tofix")).getShadowRoot()
		.findElement(By.cssSelector("[id^='rock-entity-tofix-component-rs']")).getShadowRoot()
		.findElement(By.cssSelector(".tofix-data-container > pebble-accordion"))
		.findElement(By.cssSelector("[slot='accordion-content']"));
		
		List<WebElement> conditions_refresh = summarybcs.findElements(By.cssSelector(".data-list"));
		System.out.println("There are " + conditions_refresh.size() + " elements after closing the summary tab----");
		
		int matchedRowIndex_2D = -1;
		int matchedRowIndex_Primary = -1;
		for (int i = 0; i < conditions_refresh.size(); i++) {
		    String busscondname = conditions_refresh.get(i).findElement(By.cssSelector("[class^='entity-content']")).getAttribute("title");
		    System.out.println("Condition " + (i + 1) + " -- " + busscondname );
		    
		    if (busscondname.equals("DAM: Review 2D Line Drawing")) {
		        matchedRowIndex_2D = i;
		    }
		    if (busscondname.equals("DAM: Review Representative Image (Primary)")) {
		        matchedRowIndex_Primary = i;
		    }
		}
		
		Actions action1 = new Actions(driver);
		if (matchedRowIndex_2D != -1) {
			System.out.println("\"DAM: Review 2D Line Drawing\" is at position " + (matchedRowIndex_2D + 1));
			action1.moveToElement(conditions_refresh.get(matchedRowIndex_2D)).build().perform();
			conditions_refresh.get(matchedRowIndex_2D).click();
		} else {
			System.out.println("\"DAM: Review 2D Line Drawing\" was not found.");
		}

		System.out.println("DAM: Review Representative Image (Primary) found at row -- " + matchedRowIndex_Primary);
		System.out.println("2D Line Drawing found at row --" + matchedRowIndex_2D );
		
		test.pass("Clicked on DAM: Review 2D Line Drawing which is found at row -- " + matchedRowIndex_2D);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		WebElement approve2dlinedrawing_dropdown = digitalssetPage.common_ele_2dlinedrawingDropdown().getShadowRoot()
				.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				.findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
		
		utils.waitForElement(() -> approve2dlinedrawing_dropdown, "visible");
		Thread.sleep(2000);
		test.pass("2d line drawing window appeared");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		approve2dlinedrawing_dropdown.click();
		Thread.sleep(2000);
		WebElement approvedropdownvalue = digitalssetPage.common_ele_2dlinedrawingDropdown().
		getShadowRoot().findElement(By.cssSelector("#lov")).
		getShadowRoot().findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).
		getShadowRoot().findElement(By.cssSelector("#grid")).
		getShadowRoot().findElement(By.cssSelector("#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div > div > pebble-lov-item")).
		getShadowRoot().findElement(By.cssSelector("div > div > div > span"));
		
		/*******************
		 * Approve 2d line drawing
		*******************/
		approvedropdownvalue.click();
		Thread.sleep(2000);
		digitalssetPage.Save_2d_Line_Drawring().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> digitalssetPage.Save_2d_Line_Drawring(), "clickable");
		Thread.sleep(2000);
		test.pass("Approved 2d Line drawing");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*************************************************
		 * --------- After approving and adding the image check the % now ------ *
		 ************************************************/
		utils.waitForElement(() -> searchPage.ProgressRing(), "visible");
		String percentagecompletion_AddPrimaryimage = searchPage.ProgressRing().getText();
		System.out.println("Percentage completion of " + matid_status + " is " + percentagecompletion_AddPrimaryimage + " % ");
		
		String percentClean = percentagecompletion_AddPrimaryimage.replace("%", "").trim();
		int percentValue = Integer.parseInt(percentClean);

		// Assert it's 100
		Assert.assertEquals(percentValue, 100, "Expected percentage completion to be 100%");
		test.pass("Percentage completion after approving the Add Primary image is " + percentagecompletion_AddPrimaryimage);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

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
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid_status);
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
					"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

			if (arrrowsdefined2.size() > 0) {
				System.out.println("Records found for the search criteria");
				test.fail(matid + " completion is NOT 100%. Pleaes verify");
				test.log(Status.FAIL,
						MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}
	}
}