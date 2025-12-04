package common_functions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.util.function.Function;
import java.util.function.Supplier;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class Utils  {
	private WebDriver driver;
	public WebDriverWait wait;
	final Exception[] lastException = { null };
	private ExtentTest test;
	public static String filepath = "src/test/resources/Test.xlsx";

	public Utils(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	/*****************************************************
	 ************* Updated code  ************************
	 ******************************************************/
	public WebElement waitForElement(Supplier<WebElement> elementSupplier, String conditionType) {
		try {
			switch (conditionType.toLowerCase()) {
				case "clickable":
					return wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return (el != null && el.isDisplayed() && el.isEnabled()) ? el : null;
						} catch (Exception ignored) {
							return null;
						}
					});

				case "visible":
					return wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return (el != null && el.isDisplayed()) ? el : null;
						} catch (Exception ignored) {
							return null;
						}
					});

				case "invisibility":
					wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return el == null || !el.isDisplayed();
						} catch (Exception ignored) {
							return true;
						}
					});
					return null;

				default:
					throw new IllegalArgumentException("Invalid wait condition type: " + conditionType);
			}
		} catch (Exception e) {
			try {
				String screenshotPath = Takescreenshot(driver);
				if (test != null) {
					test.fail("Wait failed: " + e.getMessage(),
						MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
				}
			} catch (IOException io) {
				io.printStackTrace();
			}
			throw new RuntimeException("waitForElement failed: " + e.getMessage(), e);
		}
	}
	
	/*******************
	 * Taking screenshot method
	*******************/
	public static String Takescreenshot(WebDriver driver) throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMM_dd_HHmmss").format(new Date());
		String screenshotFileName = "screenshot_" +  timestamp + ".png";
		String relativePath = "Screenshots/" + screenshotFileName;
		
		String fullPath = BaseTest.reportDirPath + "/" + relativePath;
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	    FileUtils.copyFile(src, new File(fullPath));

	    return relativePath;
		
	}
//	public static String Takescreenshot(WebDriver driver) throws IOException {
//		Date today = new Date();
//		SimpleDateFormat SIMPDFORMAT = new SimpleDateFormat("ddMMYY_HHmmss");
//		String date = SIMPDFORMAT.format(today);
//		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		File Destinationfilepath = new File("./Screenshots/ " + date + ".png");
//		FileUtils.copyFile(src, Destinationfilepath);
//		String destinationpath = Destinationfilepath.getAbsolutePath();
//		return destinationpath;
//	}

	/*******************
	 * Get Class Name method
	*******************/
	public String getClassName(Object obj) {
		return obj.getClass().getSimpleName();
	}
	/*******************
	 * Wait for the banner to appear and get text
	*******************/
	public static String waitForBannerAndGetText(WebDriver driver, Duration timeout) {
	    WebDriverWait wait = new WebDriverWait(driver, timeout);
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

	    WebElement banner = wait.until(drv -> {
	        WebElement el = getBannerElement.apply(drv);
	        return (el != null && el.isDisplayed()) ? el : null;
	    });

	    String bannerText = banner.getText();
	    System.out.println("✅ Banner appeared with the text : " + bannerText);

	    // Wait for banner to disappear
	    new WebDriverWait(driver, Duration.ofSeconds(10)).until(drv -> {
	        WebElement el = getBannerElement.apply(drv);
	        return el == null || !el.isDisplayed();
	    });

	    System.out.println("✅ Banner disappeared.");
	    return bannerText;
	}
	
	/*********************************************
	 * Get the difference between 2 Lists
	*********************************************/
	public static <T> List<T> Show_List_Differences(List<T> list1, List<T> list2) {
        Set<T> allItems = new HashSet<>(list1);
        allItems.addAll(list2);

        List<T> differences = new ArrayList<>();
        for (T item : allItems) {
            boolean inList1 = list1.contains(item);
            boolean inList2 = list2.contains(item);
            if (inList1 != inList2) {
                differences.add(item);
            }
        }
        return differences;
    }
	
	public static <T> Map<String, List<T>> ShowList_Item_Differences(List<T> pre, List<T> post) {
		 List<T> removed = new ArrayList<>(pre);
		    removed.removeAll(post);  // Items in pre but not in post

		    List<T> added = new ArrayList<>(post);
		    added.removeAll(pre);     // Items in post but not in pre

		    List<T> retained = new ArrayList<>(pre);
		    retained.retainAll(post); // Items common to both

		    Map<String, List<T>> result = new HashMap<>();
		    result.put("removed", removed);
		    result.put("added", added);
		    result.put("retained", retained);

		    return result;
	}
}