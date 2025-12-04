package Post_ETL_Update;

/************************************************
TC 006 - Verify the fields: Sales Org Regions (Auto), Sales Org Regions, and Sales Org Regions (Override).
Compare Pre-ETL and Post-ETL Auto Region values to identify added, removed, or retained countries.
 ************************************************/
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class TC006_Post_ETL_UserupdatingBSAPIERegionsOverride extends BaseTest {

	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	List<String> searchFieldResults;

	@Test(groups = { "BSAPIEowner" })
	public void BeforeETL_GetAllRegions_AutoRegionField() throws InterruptedException, IOException {

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

		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/SalesOrgRegions_Override.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/Post_ETL_RegionsOverride.txt";

		test.pass("Home Page is displayed");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		
		List<String> Matids = NotepadManager.GetMaterialIDs(PRE_ETL_Filename);
		List<String> BSAPIEUsecaseSalesOrgRegions_Auto = NotepadManager.getValuesByKey(PRE_ETL_Filename, "BSA PIE Usecase Sales Org Regions (Auto)");
		

		for (int i = 0; i < Matids.size(); i++) {
			String Matid = Matids.get(i);
			System.out.println(Matid);
			homePage.clickSearch_Products_Button().click();
			Thread.sleep(3000);
			
			try {
				utils.waitForElement(() -> searchPage.getgrid(), "clickable");
				searchPage.searchthingdomain_Input_Mat_Id().click();
				searchPage.searchthingdomain_Input_Mat_Id().clear();
				searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Matid);
				searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
				test.pass("Material id " + Matid + " is searched in Search thing domain");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
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
				
				System.out.println("Total rows after clicking on Pending Usecase Approval - BSA PIE Inprogress status -- " + arrrowsdefined.size());
				test.pass("Rows after after clicking on Pending Usecase Approval - BSA PIE Inprogress status appeared");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				assertTrue("There should be results after applying filters with Inprogress status", arrrowsdefined.size() > 0);

				WebElement RowByRow = arrrowsdefined.get(0);
				String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
				String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
				System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

				WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
				actions.moveToElement(RowByRow).build().perform();
				Thread.sleep(500);
				matidElement.click();
				Thread.sleep(3000);
				utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
				test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription	+ " is selected for verification");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				
				Thread.sleep(2000);
				data.put("Material ID", matid);
				/*************************************************
				 * --------- Verify workflow tab Hireacrhy. ------- *
				 ************************************************/
				List<String> Post_autoRegionList = new ArrayList<>();
				List<String> Post_regionList = new ArrayList<>();
				List<String> Post_OverrideList = new ArrayList<>();
				
				try {
				    List<String> searchFields = Arrays.asList("BSA PIE Usecase Sales Org Regions (Auto)", "BSA PIE Usecase Sales Org Regions","BSA PIE Usecase Sales Org Regions (Override)");

				    for (int j = 0; j < searchFields.size(); j++) {
				        String fieldLabel = searchFields.get(j);
				        summaryPage.SearchIcon().click();
				        summaryPage.SearchInputfield().clear();
				        summaryPage.SearchInputfield().sendKeys(fieldLabel);
				        actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();

				        utils.waitForElement(() -> BSAPIE_PO.BSAPIE_Record_Status(), "visible");
				        test.pass(fieldLabel + " field displayed");
				        test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				        WebElement tagSection = BSAPIE_PO.BSAPIE_Record_Status().findElement(By.cssSelector("pebble-tags"));
				        SearchContext shadowRoot = tagSection.getShadowRoot();

				        try {
				            WebElement moreValues = shadowRoot.findElement(By.cssSelector("div > .more-values-message"));
				            if (moreValues.isDisplayed()) {
				                moreValues.click();
				                Thread.sleep(3000);
				                test.pass(fieldLabel + " expanded via 'More values'");
				                test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				            }
				        } catch (Exception e) {
				            System.out.println("No 'More values' found for: " + fieldLabel);
				            test.pass(fieldLabel + " items listed directly");
				            test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				        }

				        List<WebElement> tags = shadowRoot.findElements(By.cssSelector("[id^='tag']"));
				        System.out.println("There are " + tags.size() + " Countries under " + fieldLabel);
				        data.put("Total " + fieldLabel + " Items", tags.size());

				        List<String> tagNames = new ArrayList<>();
				        for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
				            String tagText = tags.get(tagIndex).getText().trim();
				            tagNames.add(tagText);
				        }

				        // Store tags in appropriate list
				        if (fieldLabel.contains("(Auto)")) {
				        	Post_autoRegionList.addAll(tagNames);
				        } else {
				        	Post_regionList.addAll(tagNames);
				        }
						/*************************************************************
						 * Put the elements in to data variable to put in to notepad
						************************************************************/
				        data.put(fieldLabel, tagNames);
				        test.pass(fieldLabel + " listed are:\n" + tagNames);
				        test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

				        BSAPIE_PO.Tabclose_Xmark().click();
				        Thread.sleep(2000);

				        WebElement dropdownWrapper1 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				            .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				            .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				            .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				            .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				            .findElement(By.cssSelector("#tab-attributes")).getShadowRoot()
				            .findElement(By.cssSelector("#dropdown-wrapper"));
				        dropdownWrapper1.click();
				        Thread.sleep(1000);
				        digitalssetPage.Use_Case_Attributes_selection().click();
				        Thread.sleep(3000);
				    }
				} 
				catch (Exception e) {
				    System.out.println("Exception Occured while getting the Countries in the Sales Org regions.");
				    test.fail("Exception Occured while getting the Countries in the Sales Org regions");
				    test.log(Status.FAIL,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				    Assert.fail("Exception Occured while getting the Countries in the Sales Org regions.");
				}

				System.out.println("Sales Region (Auto) : " + Post_autoRegionList);
				System.out.println("Sales Region : " + Post_regionList);
				System.out.println("Sales Region Override : " + Post_OverrideList);
				
				NotepadManager.ReadWriteNotepad(POST_ETL_Filename, data);
				
				Map<String, List<String>> differences = Utils.ShowList_Item_Differences(BSAPIEUsecaseSalesOrgRegions_Auto,Post_autoRegionList);

				System.out.println("Removed: " + differences.get("removed"));
				System.out.println("Added: " + differences.get("added"));
				System.out.println("Retained: " + differences.get("retained"));

				test.info("Country Added After ETL upgrade is :-- " + differences.get("added"));
				test.info("Country Removed After ETL upgrade is :-- " + differences.get("removed"));
				test.info("Country Retained After ETL upgrade is :-- " + differences.get("retained"));
			}
			
			catch (Exception ex) {
				ex.printStackTrace();
				test.fail("There are no Approval workflow for the  " + Matid);
				test.log(Status.FAIL,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
			
		}
	}
}