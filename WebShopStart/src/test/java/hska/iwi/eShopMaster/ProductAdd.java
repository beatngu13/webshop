package hska.iwi.eShopMaster;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductAdd {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Test
  public void testProductAdd() throws Exception {
	driver = new FirefoxDriver();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get("http://localhost:8080/EShop");
    driver.findElement(By.id("LoginAction_username")).clear();
    driver.findElement(By.id("LoginAction_username")).sendKeys("admin");
    driver.findElement(By.id("LoginAction_password")).clear();
    driver.findElement(By.id("LoginAction_password")).sendKeys("admin");
    driver.findElement(By.id("LoginAction__execute")).click();
    driver.findElement(By.linkText("Produkt hinzufügen")).click();
    driver.findElement(By.id("AddProductAction_name")).clear();
    driver.findElement(By.id("AddProductAction_name")).sendKeys("Pizza");
    driver.findElement(By.id("AddProductAction_price")).clear();
    driver.findElement(By.id("AddProductAction_price")).sendKeys("6.5");
    driver.findElement(By.id("AddProductAction_details")).clear();
    driver.findElement(By.id("AddProductAction_details")).sendKeys("Lecker!");
    driver.findElement(By.id("AddProductAction_product_submit")).click();
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
    try {
      assertEquals("Nahrungsmittel", driver.findElement(By.xpath("//div[@id='startpage_products']/table/tbody/tr[2]/td[4]")).getText());
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
