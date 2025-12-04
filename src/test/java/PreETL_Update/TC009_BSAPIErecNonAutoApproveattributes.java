package PreETL_Update;

/************************************************
 TC 009 - Makes the approved record as Non-approved by entering attributes
 ************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
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

public class TC009_BSAPIErecNonAutoApproveattributes extends BaseTest {

	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	List<String> searchFieldResults;

	@Test(groups = { "BSAPIEowner" })
  public void GetNonApprovedAttribs() throws IOException, InterruptedException  {
	  
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

		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/Non_AutoApproveattributes.txt";

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
		
		/*************************************************
	     * --------- Check any presence of Workflow -------
	     ************************************************/
	    try {
			List<WebElement> allSteps = driver.findElement(By.cssSelector("#app")).getShadowRoot()
			        .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			        .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
			        .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
			        .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
			        .findElements(By.cssSelector("pebble-step"));

			List<WebElement> visibleSteps = allSteps.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
			int visibleCount = visibleSteps.size();
			System.out.println("✅ Workflow that appeared after approval are : " + visibleCount);

			Assert.assertEquals(visibleCount, 0, "❌ Expected no workflows, but found: " + visibleCount);
			System.out.println("As Expected there are no workflows found.");
			test.pass("As Expected there are no workflows found.");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		/*********************************
		 * Get Non Approved elements attributes
		*******************************/
		List<String> attributelist = Arrays.asList("Special Features");
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
		        }}
		
		try {
			List<String> searchFields = Arrays.asList("BSA PIE Sellable Product Status", "Construction Style Code");
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
			System.out.println("Exception occured while retreiving search fields");
			test.fail("Exception occured while retreiving search fields");
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.fail("Search elements could not be retrieved");
		}
		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename, data);
  }
}