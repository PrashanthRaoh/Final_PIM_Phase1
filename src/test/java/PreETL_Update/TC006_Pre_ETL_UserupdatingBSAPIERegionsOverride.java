package PreETL_Update;

/************************************************
TC 006 - User updates the value in BSA PIE Usecase Sales Org Regions (Override)
Descrption - Post ETL verifies entries are still in BSA PIE Usecase Sales Org Regions (Override) 
 ************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class TC006_Pre_ETL_UserupdatingBSAPIERegionsOverride extends BaseTest {

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

		test.pass("Home Page is displayed");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");

		/**************************************************
		 * ***** Verify that logged in user is BSAPIEowner
		 **************************************************/
		Thread.sleep(3000);
		homePage.BSAPIEUsecaseApprovalTab().click();
		Thread.sleep(3000);
		test.pass("Clicked on Approval tab");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
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
		List<String> expectedItems = Arrays.asList("Pending Usecase Approval - BSA PIE", "On Hold - BSA PIE (User Selected)", "On Hold - BSA PIE (Rule Triggered)");

		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		Actions actions = new Actions(driver);

		for (int i = 0; i < summaryElements.size(); i++) {
			WebElement summary = summaryElements.get(i);
			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
			String actualText = innerDiv.getText().trim();
			System.out.println("Item " + (i + 1) + ":--" + actualText);
			Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));

			if (actualText.contains("Pending Usecase Approval - BSA PIE")) {
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", innerDiv);
				try {
					actions.moveToElement(innerDiv).perform();
					Thread.sleep(500);
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
		 * ***** Click on Pending Usecase Approval - BSA PIE ****
		 ***************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on Pending Usecase Approval - BSA PIE");
		test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(5000);

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

		System.out.println("Total rows after clicking on Pending Usecase Approval - BSA PIE -- " + arrrowsdefined.size());
		test.pass("Rows after after clicking on Pending Usecase Approval - BSA PIE appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after applying filters", arrrowsdefined.size() > 0);

		/************
		 * Random number generator to click on row within row count
		 ************/
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
		 * --------- Verify workflow tab Hireacrhy. ------- *
		 ************************************************/
		List<String> autoRegionList = new ArrayList<>();
		List<String> regionList = new ArrayList<>();

		try {
		    List<String> searchFields = Arrays.asList("BSA PIE Usecase Sales Org Regions (Auto)", "BSA PIE Usecase Sales Org Regions");

		    for (int i = 0; i < searchFields.size(); i++) {
		        String fieldLabel = searchFields.get(i);

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
		        System.out.println("There are " + tags.size() + " tags under " + fieldLabel);
		        data.put("Total " + fieldLabel + " Items", tags.size());

		        List<String> tagNames = new ArrayList<>();
		        for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
		            String tagText = tags.get(tagIndex).getText().trim();
		            tagNames.add(tagText);
		        }

		        // Store tags in appropriate list
		        if (fieldLabel.contains("(Auto)")) {
		            autoRegionList.addAll(tagNames);
		        } else {
		            regionList.addAll(tagNames);
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
		        Thread.sleep(2000);
		        digitalssetPage.Use_Case_Attributes_selection().click();
		        Thread.sleep(2000);
		    }
		} catch (Exception e) {
		    System.out.println("No workflows present. So halted.");
		    test.fail("❌ No workflows present. So halted.");
		    test.log(Status.FAIL,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		    Assert.fail("There are no approval workflows to approve.");
		}

		System.out.println("Sales Region (Auto) List: " + autoRegionList);
		System.out.println("Sales Region Override List: " + regionList);
		
		/****************************************
		 * Compare the List difference between Sales Region (Auto) and Sales region
		 ****************************************/
		List<String> differences = Utils.Show_List_Differences(autoRegionList, regionList);
		if (differences.isEmpty()) {
			System.out.println("No differences between the lists.");
			 Assert.assertTrue(true, " Lists match.");
		} else {
			System.out.println("Differences found: " + differences);
			Assert.fail("❗ Lists are not equal. Differences: " + differences);
		}
		
		NotepadManager.ReadWriteNotepad(PRE_ETL_Filename, data);
		/******************************
		 * Search BSA PIE Usecase Sales Org Regions (Override)
		*****************************/
		summaryPage.SearchIcon().click();
        summaryPage.SearchInputfield().clear();
        summaryPage.SearchInputfield().sendKeys("BSA PIE Usecase Sales Org Regions (Override)");
        actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();

        utils.waitForElement(() -> BSAPIE_PO.BSAPIE_Record_Status(), "visible");
        test.pass("BSA PIE Usecase Sales Org Regions (Override) field displayed");
        test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
        /******************************
		 * ✅ Enter all the countries found in Sales Auto region (Auto)
		*****************************/
		try {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_dropdown()));
		    Assert.assertTrue(dropdown.isDisplayed(), "Dropdown element should be visible");
		    dropdown.click();
		    Thread.sleep(2000);

		    for (int i = 0; i < autoRegionList.size(); i++) {
		        digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_Search_Input().sendKeys(autoRegionList.get(i));
		        Thread.sleep(2000);
		        utils.waitForElement(() -> digitalssetPage.Total_Checkboxes(), "clickable");
		        List<WebElement> totalcbs = digitalssetPage.Total_Checkboxes().findElements(By.cssSelector("[ref='eBodyViewport'] > [name='left'] > [role='row']"));
		        System.out.println("Found " + totalcbs.size() + " regions for: " + autoRegionList.get(i));
		        assertTrue("Expected search results for " + autoRegionList.get(i), totalcbs.size() > 0);
		        totalcbs.get(0).click();
		        Thread.sleep(1000);
		        test.pass("Selected region: " + autoRegionList.get(i));
		        test.log(Status.PASS,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		        digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_Search_Input().clear();
		        digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_Search_Input().sendKeys(Keys.ENTER);
		        Thread.sleep(1000);
		    }
		} catch (Exception e) {
		    test.fail("❌ Failed during region override selection: " + e.getMessage());
		    Assert.fail("Error during region override selection");
		    test.log(Status.FAIL,  MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		}
		digitalssetPage.Save_btn_BSA_PIE_Transaction().click();
		test.pass("Transaction saved ");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*************************************************
	     * --------- Wait for the banner to appear --------
	     ************************************************/
	    WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
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

	    WebElement banner = wait1.until(drv -> {
	        WebElement el = getBannerElement.apply(drv);
	        return (el != null && el.isDisplayed()) ? el : null;
	    });

	    String bannerText = banner.getText();
	    System.out.println("✅ Banner appeared with the text : " + bannerText);
	    test.pass("Banner appeared with the text : " + bannerText);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	    Thread.sleep(5000);
	}
}