package hska.iwi.eShopMaster;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductSearch {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testProductSearch() throws Exception {
    driver.get(baseUrl + "/webshop/LogoutAction.action");
    driver.findElement(By.id("LoginAction_username")).clear();
    driver.findElement(By.id("LoginAction_username")).sendKeys("mmustermann");
    driver.findElement(By.id("LoginAction_password")).clear();
    driver.findElement(By.id("LoginAction_password")).sendKeys("1234");
    driver.findElement(By.id("LoginAction__execute")).click();
    driver.findElement(By.id("SearchAction_searchMinPrice")).clear();
    driver.findElement(By.id("SearchAction_searchMinPrice")).sendKeys("6.5");
    driver.findElement(By.id("SearchAction_searchMaxPrice")).clear();
    driver.findElement(By.id("SearchAction_searchMaxPrice")).sendKeys("6.5");
    driver.findElement(By.id("SearchAction_search_submit")).click();
    try {
      assertEquals("Pizza", driver.findElement(By.xpath("//div[@id='startpage_products']/table/tbody/tr[2]/td[2]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertEquals("6.5", driver.findElement(By.xpath("//div[@id='startpage_products']/table/tbody/tr[2]/td[3]")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.linkText("Logout")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
