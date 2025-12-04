package pim.automation.framework;

/************************************************
TC 07 - Validated"On Hold-BSA PIE(User Select)" workflow should not get trigger when DQ check get fail.
Description - Just lists the hold items.
************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import common_functions.Utils;
import pages.HomePage;
import pages.SearchPage2;
import pages.SummaryPage;

public class TC_003_OnHoldBSAPIE_UserSelect extends BaseTest {
	public ExtentTest test;
	public WebElement morevalues;

	@Test(groups = { "BSAPIEowner" })
	public void BSAPIEOwner() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);

		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");

		/**************************************************
		 * ***** Verify that logged in user is BSAPIEowner
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
		
		
		/*************
		 * Random number generator to click on row within row count
		 **/
		Random rand = new Random();
		int min = 0;
		int max = arrrowsdefined.size();
		int randnum = rand.nextInt(max - min)  + min;

		System.out.println("Row chosen is " + randnum);
		WebElement RowByRow = arrrowsdefined.get(randnum);
		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		/*************************************************
		 * --------- Click on the materialid from the result------- *
		 ************************************************/
		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription
				+ " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*************************************************
		 * --------- Click on search icon and enter hold ------- *
		 ************************************************/
		Thread.sleep(2000);
		Actions actions2 = new Actions(driver);
		summaryPage.SearchIcon().click();
		Thread.sleep(1000);
		summaryPage.SearchInputfield().sendKeys("hold");
		Thread.sleep(1000);
		actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);
		utils.waitForElement(() -> summaryPage.SystemAttributes(), "visible");
		test.pass("System attributes elemets shown up");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		WebElement BSAPIESection = summaryPage.BSAPIESection();

		int totalonholditems = 0;
		try {
			 WebElement moreValuesList = BSAPIESection.findElement(By.cssSelector(".more-values-message"));
			/**************************
			 * If More values text is present*****
			 **************************/
			if (moreValuesList.isDisplayed()) {
				String onholdMessage = moreValuesList.getText();
				System.out.println("There are " + onholdMessage + "  listed: "  );

				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(onholdMessage);

				if (matcher.find()) {
					totalonholditems = Integer.parseInt(matcher.group());
				}

				System.out.println("Number is " + totalonholditems);
				/**************************
				 * Click on More values text*****
				 **************************/
				moreValuesList.click();
				Thread.sleep(5000);
				test.pass("Onhold items Expanded " );
				test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
				
			} else {
				System.out.println("There are no More elements text.On hold items directly listed");
				test.pass("Onhold items listed directly " );
				test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}

			/**************************
			 * Get List of all On Hold items *****
			 **************************/
			List<WebElement> tagElements = BSAPIESection.findElements(By.cssSelector("[id^='tag']"));
			System.out.println("There are " + tagElements.size() + " on Hold Items");
			List<String> tagTexts = new ArrayList<>();

			int tagIndex = 1;
			for (WebElement tag : tagElements) {
				String tagText = tag.getText().trim();
				System.out.println("On Hold Item  " + tagIndex + " -- " + tagText + ": " + tagText);
				tagTexts.add(tagText);
				tagIndex++;
			}
			test.pass("Onhold items listed are \n" + tagTexts);
			test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		} catch (Exception e) {
			System.out.println("No Hold items listed");
		}
		/*******************************************************
		 * Verify the same entity is not there in On Hold - BSA PIE (User Selected)
		 *******************************************************/
		homePage.HomePage_SearchButton().click();
		Thread.sleep(2000);
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		homePage.BSAPIEUsecaseApprovalTab().click();
		Thread.sleep(5000);
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
		Thread.sleep(3000);
		test.pass("I am on Approval tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);
		/********************************
		 * click on On Hold - BSA PIE (User Selected) in approval tab
		 ********************************/
		List<WebElement> summaryElements1 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));

		for (int i = 0; i < summaryElements1.size(); i++) {
			WebElement summary = summaryElements1.get(i);
			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
			String actualText = innerDiv.getText().trim();
			System.out.println("Item " + (i + 1) + ":--" + actualText);
			if (actualText.contains("On Hold - BSA PIE (User Selected)")) {
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", innerDiv);
				try {
					innerDiv.click();
					Thread.sleep(2000);
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", innerDiv);
				}
				Thread.sleep(5000);
				break;
			}
		}
		/********************************
		 * Waiting for On Hold - BSA PIE (User Selected) tab to check material availability
		 ********************************/
		utils.waitForElement(() -> searchPage.searchthingdomain_Input_Mat_Id(), "clickable");
		test.pass("Search page grid displayed after clicking on On Hold - BSA PIE");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/********************************
		 * Enter the material ID On Hold - BSA PIE (User Selected)
		 ********************************/
		searchPage.searchthingdomain_Input_Mat_Id().click();
		searchPage.searchthingdomain_Input_Mat_Id().clear();
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid);
		test.pass("Material id " + matid + " is searched in On Hold - BSA PIE (User Selected)");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
		Thread.sleep(5000);

		/********************************
		 * Verify no rows are listed
		 ********************************/
		try {
			String txt = searchPage.rowsdisplayedtext().getText();
			String result = txt.split(" / ")[1];
			int zerorows = Integer.parseInt(result);
			System.out.println(zerorows);
			Assert.assertEquals(zerorows, 0);
			test.pass(matid + " is not listed under On Hold - BSA PIE (User Selected)");
			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		} catch (Exception e) {
			WebElement rowsredefined2 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
					.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
					.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
					.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
					.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
					.findElement(By.cssSelector("#grid"));
			List<WebElement> arrrowsdefined2 = rowsredefined2.getShadowRoot().findElements(By.cssSelector(
					"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div> div.ag-row.ag-row-even.ag-row-level-0"));

			if (arrrowsdefined2.size() > 0) {
				System.out.println("Records found for the search criteria");
				test.fail(matid + " completion is NOT 100%. Pleaes verify");
				test.log(Status.FAIL,
						MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
			}
		}
	}
}