package Post_ETL_Update;

/************************************************
TC 007 - Capture the record status and key attributes like Weight, UPC Number, ECCN, and Application Type.
Compare each attribute's post-ETL value with the corresponding pre-ETL value.
Handle both plain text and tag-based attribute fields such as "Selling UOM Description" and "Munitions Indicator".
Log any changes or confirmations of unchanged values for reporting and validation.
 ************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
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
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

public class TC007_Post_ETL_UpdatingApprovedRecwithAutoApproveattributes extends BaseTest {
	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	List<String> searchFieldResults;

	@Test(groups = { "BSAPIEowner" })
	public void BeforeETL_GetAllAttributes() throws InterruptedException, IOException {

		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));
		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");

		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/AutoApproveattributes.txt";
		String POST_ETL_Filename = "/Post_ETL_Artifacts/Post_AutoApproveattributes.txt";

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		/*******************************************************
		 * Go to search thing domain
		 *******************************************************/
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on Pending Usecase Approval - BSA PIE");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);

		/*******************************************************
		 * Get the list of all materials and verify the values
		 *******************************************************/
		List<String> Matids = NotepadManager.GetMaterialIDs(PRE_ETL_Filename);
		Actions actions = new Actions(driver);
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

				System.out.println("Total rows after clicking on Pending Usecase Approval - BSA PIE Inprogress status -- "+ arrrowsdefined.size());
				test.pass("Rows after after clicking on Pending Usecase Approval - BSA PIE Inprogress status appeared");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				assertTrue("There should be results after applying filters with Inprogress status",
						arrrowsdefined.size() > 0);

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
				test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription+ " is selected for verification");
				test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Thread.sleep(2000);

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
							.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list"))
							.getShadowRoot().findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
							.findElement(By.cssSelector("#input")).getShadowRoot()
							.findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
							.findElement(By.cssSelector("#collectionContainer")).getShadowRoot().findElement(
									By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
				} catch (Exception inner) {
					System.out.println("❌ Neither attribute list nor 'attributes not available' element found.");
				}

				if (targetElement != null) {
					RecordStatus = targetElement.getText();
					System.out.println("Status is : " + RecordStatus);
					Assert.assertEquals(RecordStatus, "Approved",  "❌ Record status is not 'Approved");
					data.put("Status", RecordStatus);
					test.info("Status of the " + matid + "  is  : - " + RecordStatus);
					test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				}

				List<String> attributelist = Arrays.asList("X Plant Material Status Description (Global)",
						"X Plant Material Status Code (Global)", "Weight - Product Only (lbs)",
						"Weight - Product Only (kg)", "UPC Number", "Sellable Material ID", "Market Part Number",
						"ECCN", "Book Description (ZB11)", "Application Type Description");

				for (int j = 0; j < attributelist.size(); j++) {
					String fieldLabel = attributelist.get(j);
					String preETL_fieldvalue = NotepadManager.getValuesByKey(PRE_ETL_Filename, fieldLabel).get(0);

					if (!summaryPage.SearchInputfield().isDisplayed()) {
						summaryPage.SearchIcon().click();
					}
					summaryPage.SearchInputfield().sendKeys(fieldLabel);
					actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
					Thread.sleep(3000);

					try {
						utils.waitForElement(() -> BSAPIE_PO.Searchelement(), "visible");
						List<WebElement> tags = BSAPIE_PO.Searchelement().getShadowRoot().findElements(By.cssSelector(".attribute-view-value"));
						test.pass(fieldLabel + " field displayed");
						test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

						String postETL_Attribvalue = "";
						for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
							postETL_Attribvalue = tags.get(tagIndex).getText().trim();
						}
						System.out.println(fieldLabel + " :--  " + postETL_Attribvalue);
						data.put(fieldLabel, postETL_Attribvalue);
						/**************************************************
						 * Compare the value of each field Pre and Post
						 **************************************************/
						if (preETL_fieldvalue.equals(postETL_Attribvalue)) {
							test.info("No change in value for <b>" + fieldLabel + "</b><br>" + "Pre-ETL: "+ preETL_fieldvalue + "<br>" + "Post-ETL: " + postETL_Attribvalue);
							System.out.println("There is no change in the value for the " + fieldLabel + "\n Pre ETL value is :-" + preETL_fieldvalue + " \n Post ETL value is " + postETL_Attribvalue);
							System.out.println("----------------------------");
						} else {
							test.info("<b>Changed value for " + fieldLabel + "<br>Pre-ETL: " + preETL_fieldvalue + "<br>Post-ETL: " + postETL_Attribvalue + "</b>");
							System.out.println("Changed value for the " + fieldLabel + "\n Pre ETL value is :-"+ preETL_fieldvalue + " \n Post ETL value is " + postETL_Attribvalue);
							System.out.println("----------------------------");
						}
					} catch (Exception e) {
						System.out.println("For the field " + fieldLabel + " there is no field displayed");
						e.printStackTrace();
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				test.fail("Exception occured while fetching the field objects. Please verify with the applicaiton");
				test.log(Status.FAIL,
						MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}

			/*********************************
			 * Attributes not plane text
			 *******************************/
			try {
				List<String> searchFields = Arrays.asList("Selling UOM Description", "Selling UOM Code","Proprietary Indicator", "Munitions Indicator");
				for (int f = 0; f < searchFields.size(); f++) {
					String Attribute_Text = searchFields.get(f);
					String preETL_fieldvalue = NotepadManager.getValuesByKey(PRE_ETL_Filename, Attribute_Text).get(0);

					if (!summaryPage.SearchInputfield().isDisplayed()) {
						summaryPage.SearchIcon().click();
					}
					summaryPage.SearchInputfield().sendKeys(Attribute_Text);
					actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
					Thread.sleep(3000);
					utils.waitForElement(() -> BSAPIE_PO.BSAPIE_Record_Status(), "visible");

					test.pass(Attribute_Text + " field displayed");
					test.log(Status.PASS,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

					WebElement tagSection = BSAPIE_PO.BSAPIE_Record_Status().findElement(By.cssSelector("pebble-tags"));
					SearchContext shadowRoot = tagSection.getShadowRoot();

					List<WebElement> tags = shadowRoot.findElements(By.cssSelector("[id^='tag']"));

					String postETL_Attribvalue = "";
					for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
						postETL_Attribvalue = tags.get(tagIndex).getText().trim();
					}
					System.out.println(Attribute_Text + " :--  " + postETL_Attribvalue);
					data.put(Attribute_Text, postETL_Attribvalue);

					if (postETL_Attribvalue.equals(postETL_Attribvalue)) {
						test.info("No change in value for <b>" + Attribute_Text + "</b><br>" + "Pre-ETL: "+ preETL_fieldvalue + "<br>" + "Post-ETL: " + postETL_Attribvalue);
						System.out.println("No change in the value for " + Attribute_Text + "\n Pre ETL value is :-"+ preETL_fieldvalue + " \n Post ETL value is " + postETL_Attribvalue);
						System.out.println("----------------------------");
					} else {
						test.info("<b>Changed value for " + Attribute_Text + "<br>Pre-ETL: " + preETL_fieldvalue + "<br>Post-ETL: " + postETL_Attribvalue + "</b>");
						System.out.println("Changed value for the " + Attribute_Text + "\n Pre ETL value is :-"+ preETL_fieldvalue + " \n Post ETL value is " + postETL_Attribvalue);
						System.out.println("----------------------------");
					}
				}
			} catch (Exception e) {
				System.out.println("No Attributes present. So halted.");
				test.fail("❌ No Attributes present. So halted.");
				test.log(Status.FAIL,
						MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				Assert.fail("TNo Attributes present. So halted.");
			}
		}
		NotepadManager.ReadWriteNotepad(POST_ETL_Filename, data);
	}
}