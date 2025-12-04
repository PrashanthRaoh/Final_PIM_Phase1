package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SummaryPage {
	private WebDriver driver;
	private By searchInputField = By.cssSelector("#app");

	public SummaryPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement common_element() {
		return driver.findElement(searchInputField)
				.getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']"))
				.getShadowRoot().findElement(By.cssSelector("[id^='app-entity-manage-component-rs']"))
				.getShadowRoot().findElement(By.cssSelector("#rockDetailTabs"));
	}

	public WebElement Things_INeedToFix() {
		return common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-entity-summary-component-rs']"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rs']"))
				.getShadowRoot().findElement(By.cssSelector("#rock-entity-tofix"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-entity-tofix-component-rs']"))
				.getShadowRoot().findElement(By.cssSelector("#accordion\\ 0"));
	}

	public WebElement SearchIcon() {
		return common_element().getShadowRoot().findElement(By.cssSelector("rock-entity-attribute-quick-search"));

	}
	public WebElement SearchInputfield() {
		return common_element().getShadowRoot().findElement(By.cssSelector("rock-entity-attribute-quick-search"))
				.getShadowRoot().findElement(By.cssSelector("#searchInput"));

	}

	public WebElement SystemAttributes() {
		return common_element().getShadowRoot()
				.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				.findElement(By.cssSelector("div > div > div:nth-child(3) > pebble-accordion")).getShadowRoot()
				.findElement(By.cssSelector("#accordion_content_container"));
	}

//	public WebElement OnholdMessage() {
//		return driver.findElement(searchInputField)
//				.getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
//				.getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']"))
//				.getShadowRoot().findElement(By.cssSelector("[id^='app-entity-manage-component-rs']"))
//				.getShadowRoot().findElement(By.cssSelector("#rockDetailTabs"))
//				.getShadowRoot().findElement(By.cssSelector("#rockTabs"))
//				.getShadowRoot().findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']"))
//				.getShadowRoot().findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']"))
//				.getShadowRoot().findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list"))
//				.getShadowRoot().findElement(By.cssSelector(".attribute-list-container.multi-attribute-group"))
//				.findElement(By.cssSelector("div.attribute-group-container-wrapper.full-height:nth-of-type(2)"))
//				.findElement(By.cssSelector("pebble-accordion"))
//				.findElement(By.cssSelector("[name='bsapieholdattributeslistruletriggered']"))
//				.findElement(By.cssSelector("rock-attribute"))
//				.getShadowRoot().findElement(By.cssSelector("[class='attribute list  referencelist']"))
//				.findElement(By.cssSelector("[class='attribute-main attribute-non-coalesced-label attribute-non-editable-label']"))
//				.findElement(By.cssSelector("[class='attribute-edit']"))
//				.findElement(By.cssSelector("#input"))
//				.getShadowRoot().findElement(By.cssSelector("[class='attribute-control ']"))
//				.getShadowRoot().findElement(By.cssSelector("#collectionContainer"))
//				.getShadowRoot().findElement(By.cssSelector(".d-flex"))
//				.findElement(By.cssSelector("pebble-tags"))
//				.getShadowRoot().findElement(By.cssSelector(".container"));
//	}
//	public WebElement OnholdMessage() {
//		return driver.findElement(searchInputField)
//			    .getShadowRoot().findElement(By.cssSelector("#contentViewManager"))
//			    .getShadowRoot().findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']"))
//			    .getShadowRoot().findElement(By.cssSelector("[id^='app-entity-manage-component-rs']"))
//			    .getShadowRoot().findElement(By.cssSelector("#rockDetailTabs"))
//			    .getShadowRoot().findElement(By.cssSelector("#rockTabs"))
//			    .getShadowRoot().findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']"))
//			    .getShadowRoot().findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']"))
//			    .getShadowRoot().findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list"))
//			    .getShadowRoot().findElement(By.cssSelector(".attribute-list-container.multi-attribute-group"))
//			    .findElement(By.cssSelector("div.attribute-group-container-wrapper.full-height:nth-of-type(1)"))
//			    .findElement(By.cssSelector("pebble-accordion"))
//			    .findElement(By.cssSelector("[name='bsapieholdattributeslistruletriggered']"))
//			    .findElement(By.cssSelector("rock-attribute"))
//			    .getShadowRoot().findElement(By.cssSelector("[class='attribute list  referencelist']"))
//			    .findElement(By.cssSelector("[class='attribute-main attribute-non-coalesced-label attribute-non-editable-label']"))
//			    .findElement(By.cssSelector("[class='attribute-edit']"))
//			    .findElement(By.cssSelector("#input"))
//			    .getShadowRoot().findElement(By.cssSelector("[class='attribute-control ']"))
//			    .getShadowRoot().findElement(By.cssSelector("#collectionContainer"))
//			    .getShadowRoot().findElement(By.cssSelector(".d-flex"))
//			    .findElement(By.cssSelector("pebble-tags"))
//			    .getShadowRoot().findElement(By.cssSelector(".container"));
//	}
	
	public WebElement BSAPIESection() {
	return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
			.findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			.findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
			.findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
			.findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
			.findElement(By.cssSelector(".attribute-list-container.multi-attribute-group"))
			.findElement(By.cssSelector("div.attribute-group-container-wrapper.full-height:nth-of-type(2)"))
			.findElement(By.cssSelector("pebble-accordion"))
			.findElement(By.cssSelector("[name='bsapieholdattributeslistruletriggered']"))
			.findElement(By.cssSelector("rock-attribute")).getShadowRoot()
			.findElement(By.cssSelector(".attribute.list.referencelist"))
			.findElement(By.cssSelector(".attribute-main.attribute-non-coalesced-label.attribute-non-editable-label"))
			.findElement(By.cssSelector(".attribute-edit")).findElement(By.cssSelector("#input")).getShadowRoot()
			.findElement(By.cssSelector(".attribute-control")).getShadowRoot()
			.findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
			.findElement(By.cssSelector(".d-flex")).findElement(By.cssSelector("pebble-tags")).getShadowRoot()
			.findElement(By.cssSelector(".container"));
	}
	public WebElement HomeBreadcrum() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#entityTitlebar")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#rockTitle > div.title-text > div.breadcrumb-wrapper > rock-breadcrumb")).getShadowRoot()
			    .findElement(By.cssSelector("div > div > span.breadcrumb-link.flex-nowrap.item-1 > span.breadcrumb-data.text-ellipsis"));
	}
	
	public WebElement Attributes_List() {
		 return  common_element().getShadowRoot().findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				  .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				  .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
				  .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				  .findElement(By.cssSelector("div > div > div > pebble-accordion > [slot='accordion-content'] > div > [name='bsapieholdattributeslistruletriggered'] > rock-attribute")).getShadowRoot()
				  .findElement(By.cssSelector("div > div > .attribute-edit > #input")).getShadowRoot()
				  .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				  .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				  .findElement(By.cssSelector("div > .d-flex > .tags-container > pebble-tags"));
	}
	
	
}
