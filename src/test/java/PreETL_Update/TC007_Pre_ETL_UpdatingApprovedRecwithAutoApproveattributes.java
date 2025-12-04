package PreETL_Update;

/************************************************
TC 007 - Gets all the attributes listed for Approved Record such as 
"Selling UOM Description", "Selling UOM Code","Proprietary Indicator", "Munitions Indicator"
 ************************************************/
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class TC007_Pre_ETL_UpdatingApprovedRecwithAutoApproveattributes extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	List<String> searchFieldResults;

	@Test(groups = { "BSAPIEowner" })
	public void BeforeETL_GetAllAttributes() throws InterruptedException, IOException {

		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression") .assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");

		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/AutoApproveattributes.txt";

		test.pass("Home Page is displayed");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");

		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on Pending Usecase Approval - BSA PIE");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);
		
		/**************************************************
		 * --------- Apply filter for BSA PIE with staus Approved ------- *
		 ************************************************/
		searchPage.getFilterButton().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
		searchPage.Search_MaterialType().sendKeys("BSA PIE Sellable Product Status");
		Thread.sleep(2000);
		utils.waitForElement(() -> digitalssetPage.SellableProductStatus(), "clickable");
		digitalssetPage.SellableProductStatus().click();
		Thread.sleep(4000);
		utils.waitForElement(() -> digitalssetPage.Status_InProgress_dropdownvalue(), "clickable");
		/**************************************************
		 * --------- Click on Approved filter------- *
		 ********************************************************/
		digitalssetPage.Status_Approved_dropdownvalue().click();
		Thread.sleep(2000);
		test.pass("SellableProductStatus is set to Approved");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Status_Apply_btn().click();
		Thread.sleep(3000);
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

		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		System.out.println("Total rows after clicking on Approved sellable product " + arrrowsdefined.size());
		test.pass("Rows appeared after clicking on Approved sellable product");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after selecting Approved sellable product", arrrowsdefined.size() > 0);

		/********************************************************
		 * Random number generator to click on row within row count
		 *********************************************/
		Random rand = new Random();
		int min = 0;
		int max = arrrowsdefined.size();
		int randnum = rand.nextInt(max - min) + min;

		System.out.println("Row chosen is " + randnum);

		WebElement RowByRow = arrrowsdefined.get(randnum);
		String SellableMaterialDescription = RowByRow .findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		matidElement.click();
		Thread.sleep(3000);

		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription + " is selected for completion");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);
		data.put("Material ID", matid);
		
		/************************************
		 * Get the status of the record
		************************************/
		summaryPage.SearchIcon().click();
		summaryPage.SearchInputfield().sendKeys("BSA PIE Sellable Product Status");
		actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
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
					.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#input"))
					.getShadowRoot().findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
					.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
					.findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
		} catch (Exception inner) {
			System.out.println("❌ Neither attribute list nor 'attributes not available' element found.");
		}

		if (targetElement != null) {
			RecordStatus = targetElement.getText();
			System.out.println("Status is : " + RecordStatus);
			Assert.assertEquals(RecordStatus, "Approved",  "❌ Record status is not 'Approved");
			data.put("Status", RecordStatus);
			test.info("Status of the " + matid + "  is  : - " + RecordStatus);
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
					/*********************************
					 * Attributes with plane text
					*******************************/
		List<String> attributelist = Arrays.asList(
			    "X Plant Material Status Description (Global)",
			    "X Plant Material Status Code (Global)",
			    "Weight - Product Only (lbs)",
			    "Weight - Product Only (kg)",
			    "UPC Number",
			    "Sellable Material ID",
			    "Market Part Number",
			    "ECCN",
			    "Book Description (ZB11)",
			    "Application Type Description"
			);
		
			for (int i = 0; i < attributelist.size(); i++) {
				String fieldLabel =attributelist.get(i);
				if (!summaryPage.SearchInputfield().isDisplayed()) {
					summaryPage.SearchIcon().click();
				}
		        summaryPage.SearchInputfield().sendKeys(fieldLabel);
		        actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		        Thread.sleep(2000);
		        
		        try {
		        	utils.waitForElement(() -> BSAPIE_PO.Searchelement(), "visible");
			        List<WebElement> tags = BSAPIE_PO.Searchelement().getShadowRoot().findElements(By.cssSelector(".attribute-view-value"));
			        test.pass(fieldLabel + " field displayed");
					test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			        List<String> tagNames = new ArrayList<>();
			        for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
			            String tagText = tags.get(tagIndex).getText().trim();
			            tagNames.add(tagText);
			        }
			        System.out.println(fieldLabel + " :--  " +tagNames );
			        data.put(fieldLabel, tagNames);
		        }
		        catch(Exception e) {
					System.out.println("Exception occured");
		        }
			}
			/*********************************
			 * Attributes not plane text
			*******************************/
			try {
				List<String> searchFields = Arrays.asList("Selling UOM Description", "Selling UOM Code","Proprietary Indicator", "Munitions Indicator");
				for (int i = 0; i < searchFields.size(); i++) {
					String fieldLabel = searchFields.get(i);
					if (!summaryPage.SearchInputfield().isDisplayed()) {
						summaryPage.SearchIcon().click();
					}
					summaryPage.SearchInputfield().sendKeys(fieldLabel);
					actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
					Thread.sleep(3000);
					utils.waitForElement(() -> BSAPIE_PO.BSAPIE_Record_Status(), "visible");
					test.pass(fieldLabel + " field displayed");
					test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					WebElement tagSection = BSAPIE_PO.BSAPIE_Record_Status().findElement(By.cssSelector("pebble-tags"));
					SearchContext shadowRoot = tagSection.getShadowRoot();

					List<WebElement> tags = shadowRoot.findElements(By.cssSelector("[id^='tag']"));
					List<String> tagNames1 = new ArrayList<>();
					for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
						String tagText = tags.get(tagIndex).getText().trim();
						tagNames1.add(tagText);
					}
					
					System.out.println(fieldLabel + " :--  " +tagNames1);
					data.put(fieldLabel, tagNames1);
				}
			} catch (Exception e) {
				System.out.println("No workflows present. So halted.");
				test.fail("❌ No workflows present. So halted.");
				test.log(Status.FAIL,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Assert.fail("There are no approval workflows to approve.");
			}
		
		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename, data);
	}
}