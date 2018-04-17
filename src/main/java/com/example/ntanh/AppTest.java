package com.example.ntanh;

import java.util.Comparator;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.ntanh.dao.ExelRecommendDao;
import com.example.ntanh.entity.Recommend;
import com.example.ntanh.entity.Recommend_Child;

public class AppTest {
	// --Step login 1
	private static final String Submit = "//button[text()='次へ']";
	private static final String UI_USERNAME_ID = "rlogin-username-ja";
	private static final String UI_USERNAME_VALUE = "takahitoq43f";
	private static final String UI_PASS_ID = "rlogin-password-ja";
	private static final String UI_PASS_VALUE = "diytool0405";
	// --- login 2
	private static final String UI_USERNAME_ID_2 = "rlogin-username-2-ja";
	private static final String UI_USERNAME_VALUE_2 = "dung_d@monotos.biz";
	private static final String UI_PASS_ID_2 = "rlogin-password-2-ja";
	private static final String UI_PASS_VALUE_2 = "hoilamgi3";
	private static final String Submit_login_2 = "//button[text()='ログイン']";
	// -- next
	private static final String Submit_login_3 = "//input[@value='上記を遵守していることを確認の上、RMSを利用します']";
	{
		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
	}
	private WebDriver driver;
	private WebDriverWait wait;
	private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

	@Test(dataProvider = "getData")
	public void main(Recommend recommend) {
		// ---
		driver.get("https://bundle.rms.rakuten.co.jp/rms/mall/rsf/bundle/#/new");
		//
		String iput_path = "/html/body/div/bundle-root/bundle-info/div[2]/form/section[1]/table/tbody/tr[1]/td[2]/div/div/input";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(iput_path)));
		WebElement by_Name = driver.findElement(By.xpath(iput_path));
		by_Name.clear();
		by_Name.sendKeys("同時購入");
		// parent
		String parent_input_path = "/html/body/div/bundle-root/bundle-info/div[2]/form/section[2]/div/table/tbody/tr/td[2]/section[1]/div/div[1]/div/input";
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(parent_input_path)));
		WebElement by_parent_code = driver.findElement(By.xpath(parent_input_path));
		by_parent_code.clear();
		by_parent_code.sendKeys(recommend.parent);
		recommend.childs.sort(new Comparator<Recommend_Child>() {
			public int compare(Recommend_Child o1, Recommend_Child o2) {
				return o2.priority - o1.priority;
			}
		});

		for (int idx = 0; idx < 3 && idx < recommend.childs.size(); idx++) {
			Recommend_Child item = recommend.childs.get(idx);
			// parent
			String child_input_path = String.format(
					"/html/body/div/bundle-root/bundle-info/div[2]/form/section[3]/div/table/tbody/tr[%d]/td[3]/section[1]/div/div[2]/div/input",
					idx + 1);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(child_input_path)));
			WebElement by_child_code = driver.findElement(By.xpath(child_input_path));
			by_child_code.clear();
			by_child_code.sendKeys(item.code);
			by_child_code.sendKeys(Keys.TAB);
		}
		// submit
		String submit_path = "/html/body/div/bundle-root/bundle-info/div[2]/form/section[3]/div/div[2]/button";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(submit_path)));
		driver.findElement(By.xpath(submit_path)).click();

		String result_path = "/html/body/div/bundle-root/bundle-list/div[2]/bundle-message[1]/div/ul/li";
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(result_path)));
			System.out.println(driver.findElement(By.xpath(result_path)).getText());
		} catch (Exception ex) {
			Assert.fail();
			//throw new AssertionError(String.format("%s FAIL", recommend.parent));
		}
	}

	@BeforeClass
	public void beforeClass() {
		// Create a new instance of the Firefox driver
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 20);
		// Using get() method to open a webpage
		driver.get("https://glogin.rms.rakuten.co.jp/");
		// 2.2 click 「RMS」ログインはこちら
		WebDriverWait wait = new WebDriverWait(driver, 15);
		// login 1
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Submit)));
		driver.findElement(By.id(UI_USERNAME_ID)).sendKeys(UI_USERNAME_VALUE);
		driver.findElement(By.id(UI_PASS_ID)).sendKeys(UI_PASS_VALUE);
		driver.findElement(By.xpath(Submit)).click();
		// login 2
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Submit_login_2)));
		driver.findElement(By.id(UI_USERNAME_ID_2)).sendKeys(UI_USERNAME_VALUE_2);
		driver.findElement(By.id(UI_PASS_ID_2)).sendKeys(UI_PASS_VALUE_2);
		driver.findElement(By.id(UI_PASS_ID_2)).sendKeys(Keys.ENTER);
		// --
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Submit)));
		driver.findElement(By.xpath(Submit)).click();
		// --
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(Submit_login_3)));
		driver.findElement(By.xpath(Submit_login_3)).click();

	}

	@AfterClass
	public void afterMethod() {
		// Close the driver
		driver.get("https://mainmenu.rms.rakuten.co.jp/?act=logout");
		driver.quit();

	}

	@DataProvider
	public Object[][] getData() {
		return new ExelRecommendDao().getData();
	}

}
