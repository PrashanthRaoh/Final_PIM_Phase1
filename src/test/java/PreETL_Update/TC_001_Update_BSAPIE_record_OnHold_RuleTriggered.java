package PreETL_Update;

/************************************************
TC 001 - Updating BSA PIE record which is "On Hold (Rule Triggered)".
Descrption - This is Pre-ETL Update script which verifies what attributes to be checked.
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

public class TC_001_Update_BSAPIE_record_OnHold_RuleTriggered extends BaseTest {
	public ExtentTest test;

	Map<String, Object> data = new LinkedHashMap<>();

	@Test(groups = { "BSAPIEowner" })
	public void BSAPIEOwner() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		BSAPIE_Page BSAPIE_PO = new BSAPIE_Page(driver);
		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/OnHoldSystem.txt";

		/**************************************************
		 * ***** Click on Use case ApprovalTab
		 **************************************************/
		Thread.sleep(5000);
		homePage.BSAPIEUsecaseApprovalTab().click();
		Thread.sleep(5000);
		test.pass("Clicked on Approval tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);

		/********************************************
		 * Get number of items under use case approvals
		 ***************************************/
		List<WebElement> summaryElements = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));

		System.out.println("Total items: " + summaryElements.size());

		List<String> expectedItems = Arrays.asList("Pending Usecase Approval - BSA PIE",
				"On Hold - BSA PIE (User Selected)", "On Hold - BSA PIE (Rule Triggered)");

		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (int i = 0; i < summaryElements.size(); i++) {
			WebElement summary = summaryElements.get(i);
			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
			String actualText = innerDiv.getText().trim();
			System.out.println("Item " + (i + 1) + ":--" + actualText);
			Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));

			if (actualText.contains("On Hold - BSA PIE (Rule Triggered)")) {
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
		test.pass("BSA PIE Use case Approval entities listed ");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/***************************************
		 * ***** Click on On Hold - BSA PIE(Rule Triggered) ****
		 ***************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on On Hold - BSA PIE");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/**************************************************
		 * --------- Get Row count------- *
		 ********************************************************/
		Thread.sleep(5000);
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

		System.out.println("Total rows after clicking on On Hold - BSA PIE -- " + arrrowsdefined.size());
		test.pass("Rows after after clicking on On Hold - BSA PIE appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after applying filters", arrrowsdefined.size() > 0);

		/********************************************************
		 * Random number generator to click on row within row count
		 ********************************************************/
		Random rand = new Random();
		int min = 0;
		int max = arrrowsdefined.size();
		int randnum = rand.nextInt(max - min)  + min;
		System.out.println("Row chosen is " + randnum);

		WebElement RowByRow = arrrowsdefined.get(randnum);
		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		data.put("Material ID", matid);
		/*************************************************
		 * --------- Click on the materialid from the result------- *
		 ************************************************/
		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);

		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription+ " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);

		List<WebElement> conditions = digitalssetPage.Summarythingsneedtofix_grid().findElements(By.cssSelector(".data-list"));
		for (int i = 0; i < conditions.size(); i++) {
			String busscondname = conditions.get(i).findElement(By.cssSelector("[class*='entity-content']")).getAttribute("title");
			System.out.println("Condition " + (i + 1) + " -- " + busscondname);
			if (busscondname.contains("Validate Attributes for BSA PIE")) {
				System.out.println(busscondname + " found at row --" + (i + 1));
				break;
			}
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
					.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#input"))
					.getShadowRoot().findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
					.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
					.findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
		} catch (Exception inner) {
			System.out.println("❌ Neither attribute list nor 'attributes not available' element found.");
		}

		if (targetElement != null) {
			System.out.println("Status is : " + targetElement.getText());
			RecordStatus = targetElement.getText();
			test.pass("Status of the " + matid + "  is  : - " + RecordStatus);
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			BSAPIE_PO.Tabclose_Xmark().click();
			Thread.sleep(4000);

		} else {
			System.out.println("🔴 No target element to act upon.");
			test.fail("No element found");
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}

		data.put("BSA PIE Sellable Product Status", RecordStatus);
		/*************************************************
		 * --------- Click on search icon and enter hold ------- *
		 ************************************************/
		Thread.sleep(1000);
		summaryPage.SearchIcon().click();
		summaryPage.SearchInputfield().sendKeys("BSA PIE - Hold Attributes List (Rule Triggered)");
		Thread.sleep(1000);
		actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);
		utils.waitForElement(() -> summaryPage.Attributes_List(), "visible");
		test.pass("System attributes elemets shown up");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		WebElement BSAPIESection = summaryPage.Attributes_List();

		try {
			WebElement moreValuesList = summaryPage.Attributes_List().getShadowRoot().findElement(By.cssSelector("div > .more-values-message"));
			/**************************
			 * If More values text is present*****
			 **************************/
			if (moreValuesList.isDisplayed()) {
				String onholdMessage = moreValuesList.getText();
				System.out.println("There are " + onholdMessage + "  listed: ");
				/**************************
				 * Click on More values text*****
				 **************************/
				moreValuesList.click();
				Thread.sleep(5000);
				test.pass("Onhold items Expanded ");
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

			} else {
				System.out.println("There are no More elements text.On hold items directly listed");
				test.pass("Onhold items listed directly ");
				test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}

			/**************************
			 * Get List of all On Hold items *****
			 **************************/
			List<WebElement> tagElements = BSAPIESection.getShadowRoot().findElements(By.cssSelector("[id^='tag']"));
			System.out.println("There are " + tagElements.size() + " on Hold Items");
			data.put("Total OnHold Items", tagElements.size());
			List<String> tagTexts = new ArrayList<>();

			int tagIndex = 1;
			for (WebElement tag : tagElements) {
				String tagText = tag.getText().trim();
				System.out.println("On Hold Item  " + tagIndex + " -- " + tagText + ": " + tagText);
				tagTexts.add(tagText);
				tagIndex++;
			}
			data.put("BSA PIE Sellable Product Status items", tagTexts);
			test.info("Total OnHold Items" + tagElements.size());
			test.pass("Onhold items listed are \n" + tagTexts);
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} catch (Exception e) {
			System.out.println("No Hold items listed");
		}
		BSAPIE_PO.Tabclose_Xmark().click();
		Thread.sleep(4000);
		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename,data);
	}
}