package Post_ETL_Update;

/************************************************
TC 009 - Logs in as BSA PIE verifies workflow and status In Progress
Updates 2D and Primary value as Digital asset owner
Updates Mandatory value logging as Marketing enrichment
Logs in as BSA PIE again and approves the record
************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
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
import common_functions.NotepadManager;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;
public class TC009_Post_BSAPIErecNonAutoApproveattributes extends BaseTest{
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	
	@Test(groups = { "BSAPIEowner" })
  public void GetNonApprovedAttribs() throws InterruptedException, IOException {
	  String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression") .assignDevice(System.getenv("COMPUTERNAME"));
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		HomePage homePage = new HomePage(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/Non_AutoApproveattributes.txt";
//		String POST_ETL_Filename = "/Pre_ETL_Artifacts/Post_Non_AutoApproveattributes.txt";
		
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
		test.pass("Home Page is displayed");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on Search thing domain");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		List<String> Matids = NotepadManager.GetMaterialIDs(PRE_ETL_Filename);
		Actions actions = new Actions(driver);
		String Matid = Matids.get(0);
		System.out.println(Matid);
		
		try {
		    utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		    searchPage.searchthingdomain_Input_Mat_Id().click();
		    searchPage.searchthingdomain_Input_Mat_Id().clear();
		    searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
		    searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
		    test.pass("Material id " + Matid + " is searched in Search thing domain");
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    Thread.sleep(5000);

		    data.put("Material ID", Matid);
		    WebElement rowsredefined = driver.findElement(By.cssSelector("#app")).getShadowRoot()
		        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
		        .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
		        .findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
		        .findElement(By.cssSelector("#entityGrid")).getShadowRoot()
		        .findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
		        .findElement(By.cssSelector("#grid"));

		    utils.waitForElement(() -> searchPage.getgrid(), "clickable");

		    List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
		        "#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		    System.out.println("Total rows after clicking entering the data is -- " + arrrowsdefined.size());
		    test.pass("Total rows after clicking entering the data appeared");
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    assertTrue("There should be results after applying filters", arrrowsdefined.size() > 0);

		    WebElement RowByRow = arrrowsdefined.get(0);
		    String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		    String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		    System.out.println("Material ID -- " + matid + " Material Description -- " + SellableMaterialDescription);

		    WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		    actions.moveToElement(RowByRow).build().perform();
		    Thread.sleep(500);
		    matidElement.click();
		    Thread.sleep(5000);

		    utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		    test.pass("Material ID -- " + matid + " Material Description -- " + SellableMaterialDescription + " is selected for verification");
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		    /***************************** 
		     * Check any presence of Workflow 
		     * ***************************/
//		    try {
//		        List<WebElement> allSteps = driver.findElement(By.cssSelector("#app")).getShadowRoot()
//		            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
//		            .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
//		            .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
//		            .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
//		            .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
//		            .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
//		            .findElements(By.cssSelector("pebble-step"));
//
//		        List<WebElement> visibleSteps = allSteps.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
//		        int visibleCount = visibleSteps.size();
//		        System.out.println("✅ Workflow that appeared after approval are: " + visibleCount);
//
//		        if (visibleCount > 0) {
//		            String message = "❌ Expected no workflows, but found: " + visibleCount;
//		            System.out.println(message);
//		            test.fail(message);
//		            test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//		            Assert.fail(message); 
//		        } else {
//		            System.out.println("✅ As expected, there are no workflows found.");
//		            test.pass("As expected, there are no workflows found.");
//		            test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//		        }
//
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		        System.out.println("There are workflows defined.Exitting");
//		        test.fail("There are workflows defined");
//		        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//		    }
		    /*************************************************
		     *  Get Non Approved Elements Attributes
		     * **************************************************/
		    List<String> attributelist = Arrays.asList("Special Features");
		    for (String fieldLabel : attributelist) {
		        if (!summaryPage.SearchInputfield().isDisplayed()) {
		            summaryPage.SearchIcon().click();
		        }
		        summaryPage.SearchInputfield().sendKeys(fieldLabel);
		        actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		        Thread.sleep(2000);
		        
		        String preETL_SpecialFeaturesvalue = NotepadManager.getValuesByKey(PRE_ETL_Filename, fieldLabel).get(0).toString();
		        try {
		            utils.waitForElement(() -> BSAPIE_PO.Searchelement(), "visible");
		            List<WebElement> tags = BSAPIE_PO.Searchelement().getShadowRoot().findElements(By.cssSelector(".attribute-view-value"));
		            test.pass(fieldLabel + " field displayed");
		            test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		            List<String> tagNames = new ArrayList<>();
		            for (WebElement tag : tags) {
		                tagNames.add(tag.getText().trim());
		            }
		            System.out.println(fieldLabel  + " Pre ETL value was " + preETL_SpecialFeaturesvalue);
		            System.out.println(fieldLabel + " :-- " + tagNames);
		            data.put(fieldLabel, tagNames);
		        } catch (Exception e) {
		            System.out.println("Exception occurred while fetching attribute: " + fieldLabel);
		            test.fail("TException occurred while fetching attribute:" + fieldLabel);
			        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		        }
		    }
		    /******************************
		     * Additional Search Fields 
		     * ****************************/
		    try {
		        List<String> searchFields = Arrays.asList("BSA PIE Sellable Product Status", "Construction Style Code");
		        for (int i = 0; i < searchFields.size(); i++) {
		        	String fieldLabel = searchFields.get(i);
					String preETL_fieldvalue = NotepadManager.getValuesByKey(PRE_ETL_Filename, fieldLabel).get(0).toString();
		        
		            if (!summaryPage.SearchInputfield().isDisplayed()) {
		                summaryPage.SearchIcon().click();
		            }
		            summaryPage.SearchInputfield().sendKeys(fieldLabel);
		            actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		            Thread.sleep(3000);

		            utils.waitForElement(() -> BSAPIE_PO.BSAPIE_Record_Status(), "visible");
		            test.pass(fieldLabel + " field displayed");
		            test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		            WebElement tagSection = BSAPIE_PO.BSAPIE_Record_Status().findElement(By.cssSelector("pebble-tags"));
		            SearchContext shadowRoot = tagSection.getShadowRoot();
		            List<WebElement> tags = shadowRoot.findElements(By.cssSelector("[id^='tag']"));
		            List<String> tagNames = tags.stream().map(tag -> tag.getText().trim()).collect(Collectors.toList());

		            System.out.println(fieldLabel + " Pre ETL field value was : - " + preETL_fieldvalue);
		            System.out.println(fieldLabel + " :-- " + tagNames);
		            data.put(fieldLabel, tagNames);
		        }
		        
		    } catch (Exception e) {
		        System.out.println("Exception occurred while updating the data. Please verify with the application.");
		        test.fail("Exception occurred while updating the data.");
		        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    }
		} 
		catch (Exception e) {
		    System.out.println("Exception occurred while processing material data. Please verify with the application.");
		    test.fail("Exception occurred while processing material data.");
		    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
			/******************
			 * Logout as BSA PIE Owner
			 ********/
			homePage.AppHeader_Administrator().click();
			Thread.sleep(1000);
			homePage.Logout_btn().click();
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait3.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[id='username']"))));
			Thread.sleep(2000);
			
	/*******************************************************************
	 * Login as Digital Asset Owner and complete adding images
	 ******************************************************************/
		loginPage.LogintoPIM("DigitalAssetowner");
		Thread.sleep(3000);
		utils.waitForElement(() -> homePage.Moredetails_MarketingEnrich(), "clickable");
		test.pass("<b> Home Page is displayed for Digital Asset Owner Login </b>");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/**************************************************
		 * ***** Verify that logged in user is Digital Asset owner
		 **************************************************/
		WebElement currentloggedinuser = homePage.loggedin_User();
		System.out.println("Logged in user is  " + currentloggedinuser.getText());
		test.pass("<b>Current user logged in is " + currentloggedinuser.getText() + "</b>");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("Logged-in user should be Digital Asset Ownerr",currentloggedinuser.getText().contains("attributeownerdigitalassets.test1"));
		Thread.sleep(2000);
		System.out.println(Matid);
		Thread.sleep(5000);
		homePage.clickSearch_Products_Button_Digital().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on the material ID ");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);

		try {
		    utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		    searchPage.searchthingdomain_Input_Mat_Id().click();
		    searchPage.searchthingdomain_Input_Mat_Id().clear();
		    searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
		    searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
		    test.pass("Material id <b>" + Matid + "</b> is searched in Search thing domain");
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    Thread.sleep(3000);

		    WebElement rowsredefined = driver.findElement(By.cssSelector("#app")).getShadowRoot()
		        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
		        .findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
		        .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
		        .findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
		        .findElement(By.cssSelector("#entityGrid")).getShadowRoot()
		        .findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
		        .findElement(By.cssSelector("#grid"));

		    utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		    List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
		        "#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		    System.out.println("Total rows after clicking entering the data is -- " + arrrowsdefined.size());
		    test.pass("Total rows after clicking entering the data appeared");
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    assertTrue("There should be results after applying filters", arrrowsdefined.size() > 0);

		    WebElement RowByRow = arrrowsdefined.get(0);
		    String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		    String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		    System.out.println("Material ID -- " + matid + " Material Description -- " + SellableMaterialDescription);

		    WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		    actions.moveToElement(RowByRow).build().perform();
		    Thread.sleep(500);
		    matidElement.click();
		    Thread.sleep(5000);

		    utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		    test.pass("Material ID -- " + matid + " Material Description -- " + SellableMaterialDescription + " is selected for verification");
		    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

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
			Thread.sleep(2000);
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
			
			if(totalsearchimagescount < 0) {
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
				 * Wait for the banner to appear
				*******************************/
				waitforbanner();
				/*******************************
				 * Check for how many images attached after adding a image
				*******************************/
				String totalimages_selected_txt_after = digitalssetPage.txt_Images_Selected().getText();
				String img_selcted_after = totalimages_selected_txt_after.split(" / ")[1];
				int totalsearchimagescount_after = Integer.parseInt(img_selcted_after);
				System.out.println("There are " + totalsearchimagescount_after + " after adding the image " );
				Assert.assertTrue(totalsearchimagescount_after > 0);
			}
			else {
				try {
					test.pass("Already there are images attached.");
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					digitalssetPage.Back_btn().click();
					Thread.sleep(1000);
					utils.waitForElement(() -> digitalssetPage.primary_Image_Required_dropdown_obj(), "clickable");
					test.pass("Back button clicked to approve the transaction");
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					
					digitalssetPage.primary_Image_Required_dropdown_obj().click();
					Thread.sleep(1000);
					utils.waitForElement(() -> digitalssetPage.ImageRequired_Yes(), "clickable");
					digitalssetPage.ImageRequired_Yes().click();
					Thread.sleep(1000);
					utils.waitForElement(() -> digitalssetPage.ApprovePrimaryImagedropdown_obj(), "clickable");
					digitalssetPage.ApprovePrimaryImagedropdown_obj().click();
					Thread.sleep(1000);
					utils.waitForElement(() -> digitalssetPage.Approve_Primary_Image_dropdownvalue(), "clickable");
					digitalssetPage.Approve_Primary_Image_dropdownvalue().click();
					Thread.sleep(2000);
					test.pass("Updated Primary image approval");
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					/*************************************************
					 * --------- Save ------ *
					 ************************************************/
					digitalssetPage.AddPrimaryImage_Save_btn().click();
					Thread.sleep(5000);
					waitforbanner();
					test.pass("Transaction saved");
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					/*******************
					 * Close DAM review Primary tab
					*******************/
					digitalssetPage.Close_DAM_review_Primary_tab().click();
					Thread.sleep(2000);
				} catch (Exception e) {
					System.out.println("Exception occured while adding images in DAM review Primary");
					test.fail("Exception occured while adding images in DAM review Primary");
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				}
			}
			/**********************************************
			 * From Summary tab select DAM: Review Representative Image (Primary) business condition
			**********************************************/
			List<WebElement> conditions_refresh = digitalssetPage.summarybcs().findElements(By.cssSelector(".data-list"));
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
				System.out.println("DAM: Review 2D Line Drawing is at position " + (matchedRowIndex_2D + 1));
				action1.moveToElement(conditions_refresh.get(matchedRowIndex_2D)).build().perform();
				conditions_refresh.get(matchedRowIndex_2D).click();
			} else {
				System.out.println("DAM: Review 2D Line Drawing was not found.");
				test.fail("DAM: Review 2D Line Drawing was not found.");
				test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}

			System.out.println("DAM: Review Representative Image (Primary) found at row -- " + matchedRowIndex_Primary);
			System.out.println("2D Line Drawing found at row --" + matchedRowIndex_2D );
			
			test.pass("Clicked on DAM: Review 2D Line Drawing which is found at row -- " + matchedRowIndex_2D);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			
			WebElement Line_2D_Data = digitalssetPage.common_ele_2dlinedrawingDropdown().getShadowRoot()
			.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
			.findElement(By.cssSelector(".d-flex"));
			
			utils.waitForElement(() -> Line_2D_Data, "visible");
			Thread.sleep(2000);
			
			if(Line_2D_Data.getText() == null || Line_2D_Data.getText().isEmpty() ) {
				WebElement approve2dlinedrawing_dropdown = digitalssetPage.common_ele_2dlinedrawingDropdown()
						.getShadowRoot().findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
						.findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));

				utils.waitForElement(() -> approve2dlinedrawing_dropdown, "visible");
				Thread.sleep(2000);
				test.pass("2d line drawing window appeared");
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				approve2dlinedrawing_dropdown.click();
				Thread.sleep(1000);

				try {
					WebElement approvedropdownvalue = digitalssetPage.common_ele_2dlinedrawingDropdown().getShadowRoot()
							.findElement(By.cssSelector("#lov")).getShadowRoot()
							.findElement(By.cssSelector("div.base-grid-structure.p-relative > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid")).getShadowRoot()
							.findElement(By.cssSelector("#grid")).getShadowRoot()
							.findElement(By.cssSelector("#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div > div > pebble-lov-item")).getShadowRoot()
							.findElement(By.cssSelector("div > div > div > span"));
					/*******************
					 * Approve 2d line drawing
					 *******************/
					approvedropdownvalue.click();
					Thread.sleep(3000);
					digitalssetPage.Save_2d_Line_Drawring().click();
					Thread.sleep(3000);
					utils.waitForElement(() -> digitalssetPage.Save_2d_Line_Drawring(), "clickable");
					Thread.sleep(2000);
					waitforbanner();
					test.pass("Approved 2d Line drawing");
					test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					BSAPIE_PO.Refresh_btn().click();
					Thread.sleep(8000);
				} catch (Exception e) {
					System.out.println("Exception occured while approving DAM: Review 2D Line Drawing");
					test.fail("Exception occured while adding approving DAM: Review 2D Line Drawing");
					test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				}
			}
			else {
				System.out.println("2d line drawing already has a status :- " + Line_2D_Data.getText());
				test.pass("2d line drawing already has a status :- " + Line_2D_Data.getText());
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		/*************************************************
		 * --------- After approving and adding the image check the % now ------ *
		 ************************************************/
		utils.waitForElement(() -> searchPage.ProgressRing(), "visible");
		String percentagecompletion_AddPrimaryimage = searchPage.ProgressRing().getText();
		System.out.println("Percentage completion of " + matid + " is " + percentagecompletion_AddPrimaryimage + " % ");
		String percentClean = percentagecompletion_AddPrimaryimage.replace("%", "").trim();
		int percentValue = Integer.parseInt(percentClean);
		// Assert it's 100
		Assert.assertEquals(percentValue, 100, "Expected percentage completion to be 100%");
		test.pass("Percentage completion after approving the Add Primary image is " + percentagecompletion_AddPrimaryimage);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/****************************************
		 * Logout as Digital asset owner
		 *****************************************/
		homePage.AppHeader_Administrator().click();
		Thread.sleep(1000);
		homePage.Logout_btn().click();
		WebDriverWait wait31 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait31.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[id='username']"))));
		Thread.sleep(2000);

		/*******************************************************************
		 * Login as Marketing owner and update long and short description
		 ******************************************************************/
		loginPage.LogintoPIM("OwnerMarketing");
		Thread.sleep(3000);
		utils.waitForElement(() -> homePage.enrichMarketingAttributelink(), "clickable");
		test.pass("<b> Home Page is displayed for Marketing Owner Login </b>");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(3000);
		/**************************************************
		 ***** Verify that logged in user is Marketing owner****************
		 **************************************************/
		WebElement currentloggedinuser1 = homePage.loggedin_User();
		System.out.println("Logged in user is  " + currentloggedinuser1.getText());
		test.pass("Current user logged in is " + currentloggedinuser1.getText());
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("Logged-in user should be Marketing owner", currentloggedinuser1.getText().contains("attributeownermarketing.test1"));
		Thread.sleep(5000);
		
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on Pending Usecase Approval - BSA PIE");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);
		
			try {
			    utils.waitForElement(() -> searchPage.getgrid(), "clickable");
			    searchPage.searchthingdomain_Input_Mat_Id().click();
			    searchPage.searchthingdomain_Input_Mat_Id().clear();
			    searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
			    searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
			
			    test.pass("Material id " + Matid + " is searched in Search thing domain");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Thread.sleep(5000);

				WebElement rowsredefined1 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
						.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
						.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
						.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
						.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
						.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
						.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
						.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
						.findElement(By.cssSelector("#grid"));

				utils.waitForElement(() -> searchPage.getgrid(), "clickable");

				List<WebElement> arrrowsdefined1 = rowsredefined1.getShadowRoot().findElements(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));
				
				System.out.println("Total rows after clicking on Pending Usecase Approval - BSA PIE Inprogress status -- " + arrrowsdefined1.size());
				test.pass("Rows after after clicking Selecting the record appeared");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				assertTrue("There should be results after entering the Material id in search thing domain ", arrrowsdefined1.size() > 0);

				WebElement RowByRow1 = arrrowsdefined1.get(0);
				System.out.println("Material ID -- " + Matid + " Material Description --" + SellableMaterialDescription);

				WebElement matidElement1 = RowByRow1.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
				actions.moveToElement(RowByRow1).build().perform();
				Thread.sleep(500);
				matidElement1.click();
				Thread.sleep(3000);
				utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
				test.pass("Summary page displayed after clicking on the Material id ");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Thread.sleep(2000);
				List<WebElement> bussrule = searchPage.BusinessRule().getShadowRoot().findElements(By.cssSelector("#accordion\\ 0 > div > div > div"));
				System.out.println("There are  " + bussrule.size() + " bussiness rule");
				Assert.assertEquals(bussrule.size(), 1, "The size of bussiness rule should be 1");
				/*************************************************
				 * --------- Click on the Marketing enrichment business rule------- *
				 ************************************************/
				bussrule.get(0).click();
				Thread.sleep(4000);
				utils.waitForElement(searchPage::shortDescription, "clickable");
				test.pass("Marketing enrichment business rule page of the material displayed");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				/*************************************************
				 * --------- Enter mandatory fields to complete the transaction------- *
				 ************************************************/
				String shortDescValue = searchPage.shortDescription().getAttribute("value");
				String longDescValue  = searchPage.LongDescription().getAttribute("value");

				if ((shortDescValue == null || shortDescValue.isBlank()) &&
				    (longDescValue == null || longDescValue.isBlank())) {
				    searchPage.shortDescription().sendKeys("Short description to complete transaction");
				    Thread.sleep(1000);
				    searchPage.LongDescription().sendKeys("This is long description");
				    Thread.sleep(1000);
				    test.pass("Entered mandatory field values on summary page");
				    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				    searchPage.SaveTransaction_btn().click();
					Thread.sleep(3000);
					waitforbanner();
				}
				else {
					 System.out.println("Long and short description has already values");
					 test.pass("Long and short description has already values");
					 test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				}
			}
			catch(Exception e) {
				System.out.println("Error while updating the data for Marketing Owner");
				e.printStackTrace();
				test.fail("Error while updating the data for Marketing Owner");
				test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
			/********************************
			 * Logout as Marketing owner
			*******************************/
			homePage.AppHeader_Administrator().click();
			Thread.sleep(1000);
			homePage.Logout_btn().click();
			WebDriverWait wait311 = new WebDriverWait(driver, Duration.ofSeconds(15));
			wait311.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[id='username']"))));
			Thread.sleep(2000);
		
			/********************************
			 * Logout as BSA PIE owner again and approve the record
			*******************************/
			loginPage.LogintoPIM("BSAPIEowner");
			
			utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");
			test.pass("Home Page is displayed");
			test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
			Thread.sleep(5000);
			homePage.clickSearch_Products_Button().click();
			Thread.sleep(3000);
			utils.waitForElement(() -> searchPage.getgrid(), "clickable");
			test.pass("Search page grid displayed after clicking on Search thing domain");
			test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Thread.sleep(3000);
			try {
			    utils.waitForElement(() -> searchPage.getgrid(), "clickable");
			    searchPage.searchthingdomain_Input_Mat_Id().click();
			    searchPage.searchthingdomain_Input_Mat_Id().clear();
			    searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
			    searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
			    test.pass("Material id " + Matid + " is searched in Search thing domain");
			    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    Thread.sleep(5000);

			    data.put("Material ID", Matid);
			    WebElement rowsredefined1 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
			        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
			        .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
			        .findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
			        .findElement(By.cssSelector("#entityGrid")).getShadowRoot()
			        .findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
			        .findElement(By.cssSelector("#grid"));

			    utils.waitForElement(() -> searchPage.getgrid(), "clickable");

			    List<WebElement> arrrowsdefined1 = rowsredefined1.getShadowRoot().findElements(By.cssSelector(
			        "#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

			    System.out.println("Total rows after clicking entering the data is -- " + arrrowsdefined1.size());
			    test.pass("Total rows after clicking entering the data appeared");
			    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    assertTrue("There should be results after applying filters", arrrowsdefined1.size() > 0);

			    WebElement RowByRow1 = arrrowsdefined1.get(0);
			    String SellableMaterialDescription1 = RowByRow1.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
			    String matid1 = RowByRow1.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
			    System.out.println("Material ID -- " + matid1 + " Material Description -- " + SellableMaterialDescription1);
			    WebElement matidElement1 = RowByRow1.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
			    actions.moveToElement(RowByRow1).build().perform();
			    Thread.sleep(500);
			    matidElement1.click();
			    Thread.sleep(5000);
			    utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
			    test.pass("Material ID -- " + matid1 + " Material Description -- " + SellableMaterialDescription1 + " is selected for verification");
			    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    
			    /***************************** 
			     * Check any presence of Workflow 
			     * ***************************/
			    try {
			        List<WebElement> steps = BSAPIE_PO.Workflows().stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
			        int visibleCount = steps.size();
			        System.out.println("✅ Workflow that appeared after approval are: " + visibleCount);
			        Assert.assertTrue(visibleCount > 0, "❌ Expected workflows, but nothing found.");
			        System.out.println("As expected, there are " + visibleCount + "  workflows found");
			        test.pass("As expected, there are " + visibleCount + "  workflows found.");
			        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			        
			        if (steps == null || steps.isEmpty()) {
			        	test.fail("❌ No workflow steps found — elements are missing.");
				        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
						throw new AssertionError("❌ No workflow steps found — 'pebble-step' elements are missing.");
					}

					/*************************************************
					 * --------- Verify "Pending Usecase Approval - BSA PIE" is the active workflow status
					 ************************************************/
					String expectedTitle = "Pending Usecase Approval - BSA PIE";
					WebElement activeStep = null;
					System.out.println("**********Listing all workflow steps:**********");
					for (int i = 0; i < steps.size(); i++) {
						WebElement step = steps.get(i);
						SearchContext stepShadow = step.getShadowRoot();
						String actualTitle = stepShadow.findElement(By.cssSelector("#label > #connectedBadge > #step-heading > #textWrapper > #step-title > span")).getAttribute("title");

						boolean isActive = step.getAttribute("class") != null && step.getAttribute("class").contains("iron-selected");
						System.out.println((i + 1) + ": " + actualTitle + (isActive ? " (🟢 Active)" : ""));

						if (isActive && actualTitle.equals(expectedTitle)) {
							activeStep = step;
						}}
					if (activeStep == null) {
						throw new AssertionError("❌ Expected active step '" + expectedTitle + "' not found.");
					}
					System.out.println("As Expected active workflow step is: " + expectedTitle);
					test.pass("As Expected active workflow step is: " + expectedTitle);
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

					/*************************************************
					 * --------- Verify approve button is present
					 ************************************************/
					WebElement commentBox = digitalssetPage.Pending_Use_Case_Approval_Commentinputbox();
					WebElement approveBtn = digitalssetPage.Pending_Use_Case_Approval_Approve_btn();

					if (commentBox == null || approveBtn == null || !approveBtn.isDisplayed()) {
						throw new AssertionError("❌ Approval UI elements not found. Cannot proceed with approval.");
					}
					test.pass("Approve button is present and enabled");
					test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					commentBox.sendKeys("Approving the record");
					Thread.sleep(2000);
					test.pass("Approving the record");
					test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					approveBtn.click();
					Thread.sleep(5000);
					/*************************************************
					 * --------- Wait for the banner to appear --------
					 ************************************************/
					waitforbanner();
					    /*************************************************
					     * --------- Check Workflow to be completed -------
					     ************************************************/
					    List<WebElement> allSteps = driver.findElement(By.cssSelector("#app")).getShadowRoot()
					            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					            .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
					            .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
					            .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
					            .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
					            .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
					            .findElements(By.cssSelector("pebble-step"));

					    List<WebElement> visibleSteps = allSteps.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
					    int visibleCount1 = visibleSteps.size();
					    System.out.println("✅ Workflow that appeared after approval are : " + visibleCount1);

					    Assert.assertEquals(visibleCount1, 0, "❌ Expected no workflows, but found: " + visibleCount1);
					    test.pass("Record moved to Approved state");
					    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					    
						/********************************
						 Verify the status to be approved
						*********************************/
						Actions actions2 = new Actions(driver);
						summaryPage.SearchIcon().click();
						summaryPage.SearchInputfield().sendKeys("BSA PIE Sellable Product Status");
						actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
						Thread.sleep(5000);
						WebElement targetElement = null;
						String RecordStatus = null;
						try {
							targetElement = driver.findElement(By.cssSelector("#app")).getShadowRoot()
									.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
									.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
									.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
									.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
									.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
									.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
									.findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
									.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
									.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
									.findElement(By.cssSelector("#input")).getShadowRoot()
									.findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
									.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
									.findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
						} catch (Exception inner) {
							System.out.println("❌ Neither attribute list nor 'attributes not available' element found.");
							inner.printStackTrace();
						}

						if (targetElement != null) {
						    System.out.println("Status is : " + targetElement.getText());
						    RecordStatus = targetElement.getText();
						    test.info("Status of the " + matid + "  is  : - " + RecordStatus);

						    if (!"Approved".equals(RecordStatus)) {
						        test.fail("❌ RecordStatus is not 'Approved'. It is: " + RecordStatus + " So cannot continue with the workflow");
						        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
						    }
						    Assert.assertEquals(RecordStatus, "Approved", "RecordStatus is not 'Approved' and its status is " + RecordStatus);
						    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
						    BSAPIE_PO.Tabclose_Xmark().click();
						    Thread.sleep(4000);

						} else {
						    System.out.println("🔴 No target element to act upon.");
						    test.fail("❌ No element found to validate status");
						    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
						}
			    } catch (IOException e) {
			        e.printStackTrace();
			        System.out.println("There are NO workflows defined.Exitting");
			        test.fail("There are NO workflows defined which should be");
			        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    }
			}
			catch(Exception e) {
				 System.out.println("Error occured while approving the data as BSA PIE Owner");
				 e.printStackTrace();
				 test.fail("Error occured while approving the data as BSA PIE Owner");
			     test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}
		catch(Exception e) {
			System.out.println("Error while updating the data for BSA PIE Owner - Outer");
			e.printStackTrace();
			test.fail("Error occured while approving the data as BSA PIE Owner - Outer");
		    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
	}
	
/***************************************************
 * Function to wait for the banner
 ***************************************************/
		public void waitforbanner() {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
			System.out.println("✅ Banner appeared with the text : " + banner.getText());
		}
}