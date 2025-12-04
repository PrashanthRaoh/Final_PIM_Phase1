package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	public WebDriver driver;
	private WebDriverWait wait;
	private By RootElement = By.cssSelector("#app");
	private WebElement tablist_Homescreen;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
	}

	public WebElement HomePage_SearchButton() {
		WebElement searchBtn = driver.findElement(RootElement).getShadowRoot().findElement(By.cssSelector("[id^='rs']"))
				.getShadowRoot().findElement(By.cssSelector("#navMenu")).getShadowRoot()
				.findElement(By.cssSelector("#pageMenuIcon_1"));
		return searchBtn;
	}

	public WebElement clickSearch_Products_Button() throws InterruptedException {
		return driver.findElement(RootElement).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#navMenu")).getShadowRoot()
				.findElement(By.cssSelector("#pageMenuIcon_2 > a.menu-icon.page-title-icon"));
	}
	public WebElement clickSearch_Products_Button_Digital() throws InterruptedException {
		return driver.findElement(RootElement).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#navMenu")).getShadowRoot()
				.findElement(By.cssSelector("#pageMenuIcon_3"));
	}



//	public void Select_Tab_By_Name(String tabName) throws InterruptedException {
//		tablist_Homescreen = driver.findElement(By.cssSelector("#app")).getShadowRoot()
//				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
//				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
//				.findElement(By.cssSelector("#rock-saved-searches")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='rock-saved-searches-component-']")).getShadowRoot()
//				.findElement(By.cssSelector("#rock-saved-searches-tabs"));
//
//		utils.waitForElement(tablist_Homescreen, "clickable");
//
//		WebElement TabtobeSelected = tablist_Homescreen.getShadowRoot().findElement(By.cssSelector("#tab-" + tabName));
//		TabtobeSelected.click();
//		Thread.sleep(3000);
//	}

//	public void SelectTab_ByName_ToDos_Tab(String tabName) throws InterruptedException {
//		String tb1 = tabName.replaceAll("\\s", "").toLowerCase();
//		WebElement tablist_My_Todos_Homescreen = driver.findElement(By.cssSelector("#app")).getShadowRoot()
//				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
//				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
//				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-']")).getShadowRoot()
//				.findElement(By.cssSelector("#rock-my-todos-tabs"));
//
//		utils.waitForElement(tablist_My_Todos_Homescreen, "clickable");
//		tablist_My_Todos_Homescreen.getShadowRoot().findElement(By.cssSelector("#tab-" + tb1 + " > div")).click();
//	}

	public WebElement EntityDataImports_DropDown_Object() throws InterruptedException {
		WebElement EntityDataImports_Dropdown = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-task-list")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-task-list-component-']")).getShadowRoot()
				.findElement(By.cssSelector(
						"div > div.base-grid-structure-child-1 > div > div.row-1.flex-container-row-1 > div.item.task-type > pebble-dropdown"));

		return EntityDataImports_Dropdown;
	}

	public WebElement Status_DropDown_Object() throws InterruptedException {
		WebElement Status_Dropdown = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-task-list")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-task-list-component-']")).getShadowRoot()
				.findElement(By.cssSelector(
						"div > div.base-grid-structure-child-1 > div > div.row-1.flex-container-row-1 > div.item.task-status > pebble-dropdown"));

		return Status_Dropdown;
	}

	public WebElement From_Last_DropDown_Object() throws InterruptedException {
		WebElement From_Last_Dropdown = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-task-list")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-task-list-component-']")).getShadowRoot()
				.findElement(By.cssSelector(
						"div > div.base-grid-structure-child-1 > div > div.row-1.flex-container-row-1 > div.item.task-from-last > pebble-dropdown"));

		return From_Last_Dropdown;
	}

	public WebElement EntityDataimports_Dropdown() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-task-list")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-task-list-component-']")).getShadowRoot()
				.findElement(By.cssSelector(
						"div > div.base-grid-structure-child-1 > div > div.row-1.flex-container-row-1 > div.item.task-type > pebble-dropdown"));
	}

	public WebElement AppHeader_Administrator() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions")).getShadowRoot()
				.findElement(By.cssSelector("div > div:nth-child(1) > div.header-action-wrapper"));
	}

	public WebElement Version_manager() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions")).getShadowRoot()
				.findElement(By.cssSelector("#userProfile")).getShadowRoot()
				.findElement(By.cssSelector("#rsVersionManager > span.option-value"));
	}

	public WebElement Version_manager_info_dialog() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions")).getShadowRoot().findElement(By.cssSelector("#userProfile")).getShadowRoot()
				.findElement(By.cssSelector("rock-version-manage")).getShadowRoot()
				.findElement(By.cssSelector("#attribute"));
	}

	public WebElement Version_manager_version_number() {
		WebElement App_Version = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions")).getShadowRoot()
				.findElement(By.cssSelector("#userProfile")).getShadowRoot()
				.findElement(By.cssSelector("rock-version-manage")).getShadowRoot()
				.findElement(By.cssSelector("#attrVal"));
		return App_Version;
	}

	public WebElement Version_manager_Close_btn() {
		WebElement Version_manager_Close_btn = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector(
						"app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions"))
				.getShadowRoot().findElement(By.cssSelector("#userProfile")).getShadowRoot()
				.findElement(By.cssSelector("rock-version-manage")).getShadowRoot()
				.findElement(By.cssSelector("#versionManagerDialog")).getShadowRoot()
				.findElement(By.cssSelector("#closeIcon"));
		return Version_manager_Close_btn;
	}

	public WebElement loggedin_User() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions")).getShadowRoot()
				.findElement(By.cssSelector("div > div:nth-child(1) > div.profile-text.text-ellipsis > div > span.profile-name-wrap > span"));
	}

	public WebElement enrichMarketingAttributelink() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));
	}
	
	public WebElement Logout_btn() {
	return driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
			    .findElement(By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions")).getShadowRoot()
			    .findElement(By.cssSelector("#userProfile")).getShadowRoot()
			    .findElement(By.cssSelector("#rsLogOut > span.option-value.text-ellipsis"));
	}
	
	public WebElement HomepageTabRootElement() {
		return driver.findElement(By.cssSelector("#app")).getShadowRoot()
        	    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
        	    .findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
        	    .findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
        	    .findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
        	    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
        	    .findElement(By.cssSelector("#rock-my-todos")).getShadowRoot()
        	    .findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
        	    .findElement(By.cssSelector("#rock-my-todos-tabs"));
	}
	
	public WebElement Moredetails_MarketingEnrich() {
		return HomepageTabRootElement().getShadowRoot()
        	    .findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
        	    .findElement(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));
    }
	
	public WebElement ReadyForTransistion_Market_Enrich() {
			    return HomepageTabRootElement().getShadowRoot()
			    .findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary")).getShadowRoot()
			    .findElement(By.cssSelector("#passedBCButton")).getShadowRoot()
			    .findElement(By.cssSelector("#button-text-box"));
	}
	
	public WebElement sellablematerialtabelement() {
		 return HomepageTabRootElement().getShadowRoot()
		    .findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
		    .findElement(By.cssSelector("pebble-list-view > pebble-list-item:nth-child(2) > my-todo-summary")).getShadowRoot()
		    .findElement(By.cssSelector("div"));
	}
	
	public WebElement BSAPIEUsecaseApprovalTab() {
		 return HomepageTabRootElement().getShadowRoot()
	    .findElement(By.cssSelector("#tab-bsapieusecaseapproval"))
	    .findElement(By.cssSelector("div"));
	}
}