package Post_ETL_Update;

/************************************************
TC 001 - Gets all the  "BSA PIE - Hold Attributes List (Rule Triggered)" items.
Description - Post ETL what are the attributes on hold should be cleared and status should be InProgress
 ************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

public class TC_001_OnHold_PostETLCheck extends BaseTest {

	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	
	@Test(groups = { "BSAPIEowner" })
	public void Post_ETL_Upgrade_Check() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		
		String PRE_ETL_Filename =  "/Pre_ETL_Artifacts/OnHoldSystem.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/Post_ETL_Onhold_System.txt";

		/**************************************
		 * Get the material ids from the notepad
		**************************************/
		List<String> Matids = NotepadManager.GetMaterialIDs(PRE_ETL_Filename);

		for (int i = 0; i < Matids.size(); i++) {
			System.out.println(Matids.get(i));
			String Matid = Matids.get(i);
			homePage.clickSearch_Products_Button().click();
			Thread.sleep(3000);

			utils.waitForElement(() -> searchPage.getgrid(), "clickable");
			searchPage.searchthingdomain_Input_Mat_Id().click();
			searchPage.searchthingdomain_Input_Mat_Id().clear();
			searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
			searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
			test.pass("Material id " + Matid + " is searched in Search thing domain");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Thread.sleep(5000);
			
			data.put("Material ID", Matid);
			
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

			utils.waitForElement(() -> searchPage.getgrid(), "clickable");

			List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
					"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

			System.out.println("Total rows after clicking on Pending Usecase Approval - BSA PIE Inprogress status -- "
					+ arrrowsdefined.size());
			test.pass("Rows after after clicking on Pending Usecase Approval - BSA PIE Inprogress status appeared");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			assertTrue("There should be results after applying filters with Inprogress status",arrrowsdefined.size() > 0);

			WebElement RowByRow = arrrowsdefined.get(0);
			String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
			String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
			System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

			/*************************************************
			 * --------- Click on the materialid from the result------- *
			 ************************************************/
			WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
			actions.moveToElement(RowByRow).build().perform();
			Thread.sleep(500);
			matidElement.click();
			Thread.sleep(3000);
			utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
			test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription + " is selected for verification");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

			List<WebElement> conditions = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
			boolean conditionFound = false;

			for (int j = 0; j < conditions.size(); j++) {
			    String busscondname = conditions.get(j).findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");

			    System.out.println("Condition " + (j + 1) + " -- " + busscondname);

			    if (busscondname.contains("Validate Attributes for BSA PIE")) {
			        System.out.println(busscondname + " found at row --" + (j + 1));
			        test.pass("\"Validate Attributes for BSA PIE\" found at row " + (j + 1));
			        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			        conditionFound = true;
			        break;
			    }
			}

			if (!conditionFound) {
			    System.out.println("\"Validate Attributes for BSA PIE\" not found in the list.");
			    test.fail("\"Validate Attributes for BSA PIE\" is not found in the New Sellable Materials. It might be in General category");
			    test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}

			/*************************************************
			 * --------- Click on search icon and enter Status ------- *
			 ************************************************/
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
			}

			if (targetElement != null) {
			    System.out.println("Status is : " + targetElement.getText());
			    RecordStatus = targetElement.getText();
			    test.info("Status of the " + matid + "  is  : - " + RecordStatus);

			    if (!"InProgress".equals(RecordStatus)) {
			        test.fail("❌ RecordStatus is not 'InProgress'. It is: " + RecordStatus + " So cannot continue with the workflow");
			        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    }

			    Assert.assertEquals(RecordStatus, "InProgress", "RecordStatus is not 'InProgress' and its status is " + RecordStatus);
			    data.put("Status", RecordStatus);

			    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    BSAPIE_PO.Tabclose_Xmark().click();
			    Thread.sleep(4000);

			} else {
			    System.out.println("🔴 No target element to act upon.");
			    test.fail("❌ No element found to validate status");
			    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}

			/*************************************************
			 * --------- Click on search icon and enter BSA PIE - Hold Attributes List (Rule Triggered) ------- *
			 ************************************************/
			Thread.sleep(2000);
			summaryPage.SearchIcon().click();
			summaryPage.SearchInputfield().sendKeys("BSA PIE - Hold Attributes List (Rule Triggered)");
			actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
			Thread.sleep(1000);
			utils.waitForElement(() -> BSAPIE_PO.BSAPIE_Record_Status(), "visible");
			test.pass("System attributes elemets shown up");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//			__________________________________________________
			
			int totalOnHoldItems = 0; 
			try {
			    WebElement pebbleTags = driver.findElement(By.cssSelector("#app"))
			        .getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
			        .getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']"))
			        .getShadowRoot().findElement(By.cssSelector("[id^='app-entity-manage-component-rs']"))
			        .getShadowRoot().findElement(By.cssSelector("#rockDetailTabs"))
			        .getShadowRoot().findElement(By.cssSelector("#rockTabs"))
			        .getShadowRoot().findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']"))
			        .getShadowRoot().findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']"))
			        .getShadowRoot().findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list"))
			        .getShadowRoot().findElement(By.cssSelector("[id^='rs']"))
			        .getShadowRoot().findElement(By.cssSelector("#input"))
			        .getShadowRoot().findElement(By.cssSelector("bedrock-lov"))
			        .getShadowRoot().findElement(By.cssSelector("#collectionContainer"))
			        .getShadowRoot().findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container > pebble-tags"));

			    SearchContext tagsShadowRoot = pebbleTags.getShadowRoot();

			    List<WebElement> tagElements = tagsShadowRoot.findElements(By.cssSelector("[id^='tag']"));
			    if (tagElements.isEmpty()) {
			        List<WebElement> moreMessage = tagsShadowRoot.findElements(By.cssSelector(".more-values-message"));
			        if (!moreMessage.isEmpty() && moreMessage.get(0).isDisplayed()) {
			            moreMessage.get(0).click();
			            Thread.sleep(3000); 
			            tagElements = tagsShadowRoot.findElements(By.cssSelector("[id^='tag']"));
			        }
			    }

			    List<String> tagTexts = new ArrayList<>();
			    for (WebElement tag : tagElements) {
			        tagTexts.add(tag.getText().trim());
			    }
			    totalOnHoldItems = tagTexts.size();
			    
			    data.put("Total BSA PIE - Hold Attributes List (Rule Triggered) Items", totalOnHoldItems);
			    data.put("BSA PIE - Hold Attributes List (Rule Triggered) items", tagTexts);
			    
			    if (totalOnHoldItems > 0) {
			        System.out.println("All tag texts: " + tagTexts);
			        System.out.println("Total On Hold Items: " + totalOnHoldItems);
			        test.fail("There are " +totalOnHoldItems + " on hold items for the record " + matid);
			        test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    } else {
			        System.out.println("As expected there are no on hold items");
			        test.pass("As expected there are no on hold items. No tags found.");
			        test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    }

			} catch (Exception e) {
			    System.out.println("Exception occurred: " + e.getMessage());
			}

			BSAPIE_PO.Tabclose_Xmark().click();
			Thread.sleep(4000);
			/*******************************
			 * Write the data to Notepad
			******************************/
		NotepadManager.ReadWriteNotepad(POST_ETL_Filename,data);
		}
	}
}