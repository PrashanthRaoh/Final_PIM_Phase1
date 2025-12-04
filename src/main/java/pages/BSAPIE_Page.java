package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BSAPIE_Page {

	private WebDriver driver;
	private By searchInputField = By.cssSelector("#app");

	public BSAPIE_Page(WebDriver driver) {
		this.driver = driver;
	}
		public WebElement common_element() {
			return driver.findElement(searchInputField).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector("#rockDetailTabs"));
	}
		
		public WebElement BSAPIESelectAttributesforHold() {
			return common_element().getShadowRoot()
				    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#input")).getShadowRoot()
				    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				    .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
		}
		
		public WebElement BSAPIEUsecaseSalesOrgRegions_Auto () {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
			    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
			    .findElements(By.cssSelector("[id^='rs']")).get(8).getShadowRoot()
			    .findElement(By.cssSelector("#input")).getShadowRoot()
			    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
			    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
			    .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container > pebble-tags")).getShadowRoot()
			    .findElement(By.cssSelector("#tag0")).getShadowRoot()
			    .findElement(By.cssSelector("#pebble-tag"))
			    .findElement(By.cssSelector(".tag-item > .tag-name-value > .attribute-name"));
		}
		
		public WebElement Workflow_Refresh_btn() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#entityManageHeader")).getShadowRoot()
				    .findElement(By.cssSelector("#entityActions")).getShadowRoot()
				    .findElement(By.cssSelector("#toolbar")).getShadowRoot()
				    .findElement(By.cssSelector("#refresh"));
		}
		
		public WebElement getInProgressWorkflowStep(List<WebElement> steps, String expectedTitle) {
		    System.out.println("****** Workflows listed in the Workflow tab are *****");
		    for (int i = 0; i < steps.size(); i++) {
		        WebElement step = steps.get(i);
		        SearchContext stepShadow = step.getShadowRoot();
		        String actualTitle = stepShadow.findElement(By.cssSelector("#label > #connectedBadge > #step-heading > #textWrapper > #step-title > span"))
		            .getAttribute("title");

		        boolean inProgress = step.getAttribute("class") != null &&
		                             step.getAttribute("class").contains("iron-selected");

		        System.out.println((i + 1) + ": " + actualTitle + (inProgress ? " (In Progress)" : ""));

		        if (expectedTitle.equals(actualTitle)) {
		            if (inProgress) {
		                System.out.println("✅ As Expected: '" + expectedTitle + "' is In Progress.");
		                return step;
		            } else {
		                throw new AssertionError("❌ '" + expectedTitle + "' is found but not in progress.");
		            }
		        }
		    }

		    throw new AssertionError("❌ '" + expectedTitle + "' not found in the workflow.");
		}
		
		public WebElement BSAPIE_Record_Status() {
			return driver.findElement(By.cssSelector("#app"))
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
			.getShadowRoot().findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
		}
		
		/*******************
		 * Close the last tab
		*******************/
		public WebElement Tabclose_Xmark() {
		return driver.findElement(By.cssSelector("#app"))
			    .getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']"))
			    .getShadowRoot().findElement(By.cssSelector("[id^='app-entity-manage-component-rs']"))
			    .getShadowRoot().findElement(By.cssSelector("#rockDetailTabs"))
			    .getShadowRoot().findElement(By.cssSelector("#rockTabs"))
			    .getShadowRoot().findElement(By.cssSelector("#tab-QuickSearchAttributes > div > div.tab-title > span.dynamic-close"));
		}
		
		public WebElement dropdownWrapper() {
		 return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("#tab-attributes")).getShadowRoot()
				.findElement(By.cssSelector("#dropdown-wrapper"));
		}
		
		public WebElement Attributenotdisplayed() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector(".base-grid-structure > .base-grid-structure-child-2 > #wizard-container > .base-grid-structure-child-2 > div > .base-grid-structure-child-2 > #step-container-manage > [name='QuickSearchAttributes']")).getShadowRoot()
				    .findElement(By.cssSelector(".base-grid-structure > div.base-grid-structure-child-1 > [align='center']"));
		}
		
		public WebElement Searchelement() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
		        	  .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
		        	  .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
		        	  .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
		        	  .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
		        	  .findElement(By.cssSelector("[id^='rs']"));
		}
		
		
		public List<WebElement>  Workflows() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
	            .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
	            .findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
	            .findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
	            .findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
	            .findElements(By.cssSelector("pebble-step"));
		}
		
		public WebElement Refresh_btn() {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector("#entityManageHeader")).getShadowRoot()
					.findElement(By.cssSelector("#entityActions")).getShadowRoot()
					.findElement(By.cssSelector("#toolbar")).getShadowRoot().findElement(By.cssSelector("#refresh"))
					.getShadowRoot().findElement(By.cssSelector("#simpleButton > pebble-icon"));
		}

}