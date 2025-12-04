package Post_ETL_Update;

/************************************************
TC 004 - New Region added to the BSA PIE Auto Region Field.
Descrption - Verifies the Sales Org region auto countries. Post ETL it checks the addition of countries.
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
import org.openqa.selenium.JavascriptExecutor;
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

public class TC004_Post_ETL_NewRegion_ADD_BSAPIEAutoRegionField extends BaseTest {

	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	List<String> tagTexts;
	
	@Test(groups = { "BSAPIEowner" })
	public void BeforeETL_GetAllRegions_AutoRegionField() throws InterruptedException, IOException {

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
		
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/Sales_Org_Regions_Auto_ADD.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/Post_ETL_Sales_Org_Regions_Auto_ADD.txt";

		List<String> Matids = NotepadManager.GetMaterialIDs(PRE_ETL_Filename);

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
				Thread.sleep(3000);
				WebElement dropdownWrapper = driver.findElement(By.cssSelector("#app")).getShadowRoot()
						.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
						.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
						.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
						.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
						.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
						.findElement(By.cssSelector("#tab-attributes")).getShadowRoot()
						.findElement(By.cssSelector("#dropdown-wrapper"));

				dropdownWrapper.click();
				Thread.sleep(2000);
				digitalssetPage.Use_Case_Attributes_selection().click();
				Thread.sleep(2000);

				test.pass("Use case attribute selection page displayed");
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

				Thread.sleep(2000);
				summaryPage.SearchIcon().click();
				summaryPage.SearchInputfield().sendKeys("BSA PIE Usecase Sales Org Regions (Auto)");
				actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
				utils.waitForElement(() -> BSAPIE_PO.BSAPIE_Record_Status(), "visible");
				test.pass("BSA PIE Usecase Sales Org Regions (Auto) displayed");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

				WebElement BSAPIESection = BSAPIE_PO.BSAPIE_Record_Status().findElement(By.cssSelector("pebble-tags"));

				try {
					WebElement moreValuesList = BSAPIESection.getShadowRoot().findElement(By.cssSelector("div > .more-values-message"));
					if (moreValuesList.isDisplayed()) {
						moreValuesList.click();
						Thread.sleep(5000);
						test.pass("Sales Org Regions (Auto) Expanded ");
						test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					} else {
						System.out.println("There are no More elements text. On hold items directly listed");
						test.pass("Onhold items listed directly ");
						test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					}

					List<WebElement> tagElements = BSAPIESection.getShadowRoot().findElements(By.cssSelector("[id^='tag']"));
					System.out.println("There are " + tagElements.size() + " on Hold Items");
					data.put("Total Sales Org Regions (Auto) Items", tagElements.size());

					 tagTexts = new ArrayList<>();
					int tagIndex = 1;
					for (WebElement tag : tagElements) {
						String tagText = tag.getText().trim();
						System.out.println("Sales Org Regions (Auto) Item  " + tagIndex + " -- " + tagText + ": " + tagText);
						tagTexts.add(tagText);
						tagIndex++;
					}
					data.put("BSA PIE Usecase Sales Org Regions (Auto)", tagTexts);
					test.pass("BSA PIE Usecase Sales Org Regions (Auto) listed are \n" + tagTexts);
					test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					
//					NotepadManager.Over_WriteNotepad(POST_ETL_Filename, data);
					NotepadManager.ReadWriteNotepad(POST_ETL_Filename, data);
					
					BSAPIE_PO.Tabclose_Xmark().click();
					Thread.sleep(4000);
					
					List<String> preETL_SalesorgAuto = NotepadManager.getValuesByKey(PRE_ETL_Filename,"BSA PIE Usecase Sales Org Regions (Auto)");
					List<String> postETL_SalesorgAuto = NotepadManager.getValuesByKey(POST_ETL_Filename,"BSA PIE Usecase Sales Org Regions (Auto)");
					
					System.out.println("Sales org countries Before ETL upgrade are " + preETL_SalesorgAuto );
					System.out.println("Sales org countries After ETL upgrade are " + postETL_SalesorgAuto );
					
					test.info("Sales org countries Before ETL upgrade are " + preETL_SalesorgAuto );
					test.info("Sales org countries After ETL upgrade are " + postETL_SalesorgAuto );
					System.out.println(" ----------");
					
					Map<String, List<String>> diff = NotepadManager.getListDifferences(preETL_SalesorgAuto, postETL_SalesorgAuto);

				    List<String> addedInPost = diff.get("Added in Post List");
				    List<String> removedFromPre = diff.get("Removed from Pre List");

				    System.out.println("Added in Post List: " + addedInPost);
				    System.out.println("Removed from Pre List: " + removedFromPre);
				    
				    test.info("Country Added After ETL upgrade is :-- " + addedInPost);
				    test.info("Country Removed before ETL upgrade is :-- " + removedFromPre);
					
				} catch (Exception e) {
					System.out.println("No Sales Org Regions (Auto) items listed");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				test.fail("Exception occurred during Sales Org Regions (Auto) flow for the Entity -- " + Matid);
				test.log(Status.FAIL,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}
	}
}