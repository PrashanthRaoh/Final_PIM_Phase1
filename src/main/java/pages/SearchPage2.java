package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage2 {
	private WebDriver driver;
	private WebDriverWait wait;

	private By searchInputField = By.cssSelector("#app");
	private By rowsLocator = By.cssSelector("#app");
	private By filterButtonLocator = By.cssSelector("#filterButton");

	public WebElement commonelement() {
		return driver.findElement(searchInputField).getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
				.findElement(By.cssSelector("#search-filter"));
	}

	public SearchPage2(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(90));
	}

	public WebElement getSearchField() {
		return driver.findElement(searchInputField).getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#searchBar")).getShadowRoot().findElement(By.cssSelector("#input"));
	}

	public WebElement getFilterButton() {
		return commonelement().getShadowRoot()
				.findElement(filterButtonLocator).getShadowRoot()
				.findElement(By.cssSelector("#buttonTextBox"));
	}

	public WebElement Search_button_After_Filter() {
		return commonelement().getShadowRoot().findElement(
				By.cssSelector("#refineFilterPopover > div > div.p-relative.p-b-10.base-grid-structure-child-1"));
	}

	public WebElement Filter_Results() {
		return commonelement().getShadowRoot().findElement(By.cssSelector("#attributeModelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector("#modelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector(
						"div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#gird")).getShadowRoot().findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div"));
	}

	public WebElement getGridRoot() {
		return driver.findElement(rowsLocator).getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
				.findElement(By.cssSelector("#grid"));
	}

	public void enterMaterialId(String materialId) {
		WebElement searchField = getSearchField();
		wait.until(ExpectedConditions.elementToBeClickable(searchField));
		searchField.sendKeys(materialId);
		searchField.sendKeys(Keys.ENTER);
	}

	public WebElement getgrid() {
		WebElement gridRows = getGridRoot().getShadowRoot().findElement(By.cssSelector(
				"#lit-grid> div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed >div.ag-root.ag-unselectable.ag-layout-normal> div.ag-body-viewport.ag-layout-normal.ag-row-no-animation> div.ag-center-cols-clipper > div.ag-center-cols-viewport > div.ag-center-cols-container"));
		return gridRows;
	}

	public WebElement Search_MaterialType() {
		WebElement inputElement = commonelement().getShadowRoot()
				.findElement(By.cssSelector("#refineMoreSearchbox"))
				.getShadowRoot().findElement(By.cssSelector("#input"));
		return inputElement;
	}

	public WebElement ResultgridThingDomain() {
		WebElement resultgridthingdomain = commonelement().getShadowRoot()
				.findElement(By.cssSelector("#attributeModelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector("#modelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector(
						"div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot().findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div"));

		return resultgridthingdomain;
	}

	public WebElement PIMTaxonomy_Firstresult() {
		WebElement SearchThingfiltersearch_Firstresult = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
				.findElement(By.cssSelector("#search-filter")).getShadowRoot()
				.findElement(By.cssSelector("#attributeModelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector("#modelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector(
						"div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector("div.ag-row.ag-row-even > div > pebble-lov-item")).getShadowRoot()
				.findElement(By.cssSelector("div > div > div > span"));

		return SearchThingfiltersearch_Firstresult;
	}

	public WebElement SearchThingfiltersearch_Second_result() {
		WebElement SearchThingfiltersearch_Second_result = commonelement().getShadowRoot()
				.findElement(By.cssSelector("#attributeModelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector("#modelLov_thing")).getShadowRoot()
				.findElement(By.cssSelector(
						"div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot().findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div.ag-row.ag-row-no-focus.ag-row-odd.ag-row-level-0.ag-row-position-absolute.ag-row-last"));

		return SearchThingfiltersearch_Second_result;
	}

	public WebElement Classification_Search_inputbox() {
		return commonelement().getShadowRoot().findElement(By.cssSelector("#pathSelector")).getShadowRoot()
				.findElement(By.cssSelector("#classification-contextTree")).getShadowRoot()
				.findElement(By.cssSelector("#classificationSearchBar")).getShadowRoot()
				.findElement(By.cssSelector("#input"));
	}

	public WebElement widerangeCheckbox() {
		return commonelement().getShadowRoot().findElement(By.cssSelector("#pathSelector")).getShadowRoot()
				.findElement(By.cssSelector("#classification-contextTree")).getShadowRoot()
				.findElement(By.cssSelector("#contextTree")).getShadowRoot()
				.findElement(By.cssSelector("ul > pebble-tree-node")).getShadowRoot()
				.findElement(By.cssSelector("#expand-all-btn-container-li > div.check-box-wrapper > pebble-checkbox"));
	}

	public WebElement classficationwindow_Applybtn() {
		return commonelement().getShadowRoot().findElement(By.cssSelector("#pathSelector")).getShadowRoot()
				.findElement(By.cssSelector("#download")).getShadowRoot().findElement(By.cssSelector("#buttonTextBox"));
	}

	public WebElement searchthingdomain_Input_Mat_Id() {
		return driver.findElement(searchInputField).getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#searchBar")).getShadowRoot().findElement(By.cssSelector("#input"));

	}

	public WebElement ProgressRing() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageHeader")).getShadowRoot()
				.findElement(By.cssSelector("#graphProgress")).getShadowRoot()
				.findElement(By.cssSelector("#progressRing")).getShadowRoot().findElement(By.cssSelector("#score"));
	}

	public WebElement BusinessRule() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot().findElement(By.cssSelector("#rockTabs"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-entity-summary-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-entity-tofix")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-entity-tofix-component-rs']"));

	}

	public WebElement SummaryCommonElement() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage"));
	}

	public WebElement similarElementforInput_Summary() {
		return driver.findElement(searchInputField).getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot().findElement(By.cssSelector("#rockTabs"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				.findElement(By.cssSelector("div > div > div > pebble-accordion > div > div"));
	}

	public WebElement shortDescription() {
		return similarElementforInput_Summary()
				.findElement(By.cssSelector(".attribute-box.attribute-box-2-by-3.textarea.overflow-hidden[name='shortdescription']"))
				.findElement(By.cssSelector("[mode='edit']")).getShadowRoot()
				.findElement(By.cssSelector(".attribute.list.textarea"))
				.findElement(By.cssSelector("#input")).getShadowRoot()
				.findElement(By.cssSelector(".attribute-control")).getShadowRoot()
				.findElement(By.cssSelector("#textarea"));
	}

	public WebElement LongDescription() {
		return similarElementforInput_Summary()
				.findElement(By.cssSelector(
						".attribute-box.attribute-box-2-by-3.textarea.overflow-hidden[name='longdescription']"))
				.findElement(By.cssSelector("[mode='edit']")).getShadowRoot()
				.findElement(By.cssSelector(".attribute.list.textarea")).findElement(By.cssSelector("#input"))
				.getShadowRoot().findElement(By.cssSelector(".attribute-control")).getShadowRoot()
				.findElement(By.cssSelector("#textarea"));
	}

	public WebElement SaveTransaction_btn() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector(".base-grid-structure"))
				.findElement(By.cssSelector(".base-grid-structure-child-2"))
				.findElement(By.cssSelector("#tab-content"))
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector(".base-grid-structure"))
				.findElement(By.cssSelector(
						".base-grid-structure-child-2 > #wizard-container > .base-grid-structure-child-2 > div > .base-grid-structure-child-2 > #step-container-manage > rock-attribute-split-screen"))
				.getShadowRoot().findElement(By.cssSelector(".attribute-split-screen-container > .base-grid-structure"))
				.findElement(By.cssSelector(".base-grid-structure-child-2"))
				.findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
				.findElement(By.cssSelector("#buttonContainer")).findElement(By.cssSelector("#next"));

	}

	public WebElement ATO_Marketing_Enrichment_Buss_Condition_Filter() {
		return driver.findElement(searchInputField).getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
				.findElement(By.cssSelector("#search-filter")).getShadowRoot()
				.findElement(By.cssSelector("#BCModelLov")).getShadowRoot().findElement(By.cssSelector("#BCModelLov"))
				.getShadowRoot()
				.findElement(By.cssSelector(
						"div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div > div > pebble-lov-item"))
				.getShadowRoot().findElement(By.cssSelector("div > div"));
	}

	public WebElement common_Enrichment_Filter_Ele() {
		return driver.findElement(searchInputField).getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchFilter")).getShadowRoot()
				.findElement(By.cssSelector("#search-filter")).getShadowRoot()
				.findElement(By.cssSelector("#businessConditionLov"));
	}

	public WebElement Failed_Buss_Condition() {
		return common_Enrichment_Filter_Ele().getShadowRoot().findElement(By.cssSelector(
				"div.base-grid-structure.p-relative.hideLovHeader > div.base-grid-structure-child-2.overflow-auto.p-relative > pebble-grid"))
				.getShadowRoot().findElement(By.cssSelector("#grid")).getShadowRoot()
				.findElement(By.cssSelector(
						"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div.ag-row.ag-row-odd.ag-row-level-0.ag-row-position-absolute.ag-row-no-focus > div > pebble-lov-item"))
				.getShadowRoot().findElement(By.cssSelector("div > div"));
	}

	public WebElement Apply_btn_FailedEnrichMarketing() {
		return commonelement().getShadowRoot().findElement(By.cssSelector("#businessConditionLov")).getShadowRoot()
				.findElement(By.cssSelector("#confirmButton")).getShadowRoot()
				.findElement(By.cssSelector("#buttonTextBox"));
	}

	public WebElement Search_things_BreadCrum() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityTitlebar")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockTitle > div.title-text > div.breadcrumb-wrapper > rock-breadcrumb"))
				.getShadowRoot().findElement(By.cssSelector(
						"div > div > span.breadcrumb-link.flex-nowrap.item-2 > span.breadcrumb-data.text-ellipsis > span.subtitle"));

	}
	public WebElement rowsdisplayedtext() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot().findElement(By
						.cssSelector("#gridHeader > div.grid-actions.row > span.text-ellipsis.m-r-5.m-l-5.page-range"));

	}

	/*******************************
	 * Work flow block on right panel
	 *******************************/
	public WebElement Workflowblocksameelement() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
				.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']"));
	}

	public WebElement AssignUser() {
		return Workflowblocksameelement().getShadowRoot()
				.findElement(By.cssSelector("#stepComments-enrichmarketingattributes")).getShadowRoot()
				.findElement(By.cssSelector("#textarea")).getShadowRoot().findElement(By.cssSelector("#input-1"))
				.getShadowRoot().findElement(By.cssSelector("#textarea"));
	}

	public WebElement Done_Button() {
		return Workflowblocksameelement().getShadowRoot()
				.findElement(By.cssSelector("#action-button-done")).getShadowRoot()
				.findElement(By.cssSelector("#buttonTextBox"));
	}

	public WebElement Spinner() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleAppToast")).getShadowRoot()
				.findElement(By.cssSelector("div > div:nth-child(1)"));
	}
	
	public WebElement Actionworkflow_Message() {
		return  Workflowblocksameelement().getShadowRoot().findElement(By.cssSelector("#workflowPanelMessage"));
	}
	
	public WebElement GridStructure() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
			    .findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
			    .findElement(By.cssSelector(".base-grid-structure > .base-grid-structure-child-2"));
	}
}
