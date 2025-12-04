package PreETL_Update;

/************************************************
TC 005 - Gets all the region added to the BSA PIE Auto Region Field.
Descrption - Verifies the Sales Org region auto countries. Post ETL it checks the deletion of countries added.
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
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

public class TC005_Pre_ETL_Region_DELETE_BSAPIEAutoRegionField extends BaseTest {

	public ExtentTest test;
	Map<String, Object> data = new LinkedHashMap<>();
	
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
		
		String PRE_ETL_Filename = "/Pre_ETL_Artifacts/Sales_Org_DELETE_Regions.txt";

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");

		/**************************************************
		 * ***** Verify that logged in user is BSAPIEowner
		 **************************************************/
		Thread.sleep(3000);
		homePage.BSAPIEUsecaseApprovalTab().click();
		Thread.sleep(3000);
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
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
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
		int randnum = rand.nextInt(max - min)  + min;

		System.out.println("Row chosen is " + randnum);

		WebElement RowByRow = arrrowsdefined.get(randnum);
		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);

		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription+ " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);
		data.put("Material ID", matid);
		/*************************************************
		 * --------- Verify workflow tab Hireacrhy. ------- *
		 ************************************************/
		try {
			// Try to locate all workflow steps
			List<WebElement> steps = driver.findElement(By.cssSelector("#app")).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
					.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
					.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector(
							".base-grid-structure > .base-grid-structure-child-2 > #workflows-content > #accordion0 > [slot='accordion-content'] > .workflow-content > #workflowStepper_bsapieusecaseapproval_workflowDefinition"))
					.findElements(By.cssSelector("pebble-step"));

			if (steps == null || steps.isEmpty()) {
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
				}
			}

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
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			
			 commentBox.sendKeys("Approving the record");
			    Thread.sleep(2000);

			    test.pass("Approving the record");
			    test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			    approveBtn.click();
			    Thread.sleep(5000);
			    
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
			    Thread.sleep(3000);
			    
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
			    int visibleCount = visibleSteps.size();
			    System.out.println("✅ Workflow that appeared after approval are : " + visibleCount);

			    Assert.assertEquals(visibleCount, 0, "❌ Expected no workflows, but found: " + visibleCount);
			    test.pass("Record moved to Approved state");
			    test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build()); 
			
			/*************************************************
			 * --------- Click on drop down next to Attributes tab
			 ************************************************/
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
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				
				/*************************************************
				 * --------- Click on search icon and enter BSA PIE Usecase Sales Org Regions (Auto) ------- *
				 ************************************************/
				Thread.sleep(2000);
				summaryPage.SearchIcon().click();
				summaryPage.SearchInputfield().sendKeys("BSA PIE Usecase Sales Org Regions (Auto)");
				actions.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
				utils.waitForElement(() -> BSAPIE_PO.BSAPIE_Record_Status(), "visible");
				test.pass("BSA PIE Usecase Sales Org Regions (Auto) displayed");
				test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				WebElement BSAPIESection = BSAPIE_PO.BSAPIE_Record_Status().findElement(By.cssSelector("pebble-tags"));
				
				/*************************************************
				 * --------- Try catch to check BSA PIE Usecase Sales Org Regions (Auto) eleemnts------- *
				 ************************************************/
				try {
					WebElement moreValuesList = BSAPIESection.getShadowRoot()
							.findElement(By.cssSelector("div > .more-values-message"));
					/**************************
					 * If More values text is present*****
					 **************************/
					if (moreValuesList.isDisplayed()) {
						/**************************
						 * Click on More values text*****
						 **************************/
						moreValuesList.click();
						Thread.sleep(5000);
						test.pass("Sales Org Regions (Auto) Expanded ");
						test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

					} else {
						System.out.println("There are no More elements text.BSA PIE Usecase Sales Org Regions (Auto) items directly listed");
						test.pass("BSA PIE Usecase Sales Org Regions (Auto) items listed directly ");
						test.log(Status.INFO,MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					}

					/**************************************************
					 * Get List of all Sales Org Regions (Auto)items *****
					 **************************************************/
					List<WebElement> tagElements = BSAPIESection.getShadowRoot().findElements(By.cssSelector("[id^='tag']"));
					System.out.println("There are " + tagElements.size() + " on Hold Items");
					data.put("Total BSA PIE Usecase Sales Org Regions (Auto) Items", tagElements.size());
					
					List<String> tagTexts = new ArrayList<>();
					
					int tagIndex = 1;
					for (WebElement tag : tagElements) {
						String tagText = tag.getText().trim();
						System.out.println("Sales Org Regions (Auto) Item  " + tagIndex + " -- " + tagText + ": " + tagText);
						tagTexts.add(tagText);
						tagIndex++;
					}
					data.put("BSA PIE Usecase Sales Org Regions (Auto)", tagTexts);
					test.pass("BSA PIE Usecase Sales Org Regions (Auto) listed are \n" + tagTexts);
					test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
					
					NotepadManager.ReadWriteNotepad(PRE_ETL_Filename,data);
					
					BSAPIE_PO.Tabclose_Xmark().click();
					Thread.sleep(4000);
				} catch (Exception e) {
					System.out.println("No BSA PIE Usecase Sales Org Regions (Auto) items listed");
				}

		} catch (Exception e) {
			test.fail("❌ Workflow approval test failed: " + e.getMessage());
			test.log(Status.FAIL, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			Assert.fail("There are no approval workflow to approve ");
		}
	}
}