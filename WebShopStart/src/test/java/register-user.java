package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class RegisterUser {
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
  public void testRegisterUser() throws Exception {
    driver.get(baseUrl + "/webshop/LogoutAction.action");
    driver.findElement(By.linkText("Noch nicht registriert?")).click();
    driver.findElement(By.id("RegisterAction_firstname")).clear();
    driver.findElement(By.id("RegisterAction_firstname")).sendKeys("John");
    driver.findElement(By.id("RegisterAction_lastname")).clear();
    driver.findElement(By.id("RegisterAction_lastname")).sendKeys("Done");
    driver.findElement(By.id("RegisterAction_username")).clear();
    driver.findElement(By.id("RegisterAction_username")).sendKeys("jdoe");
    driver.findElement(By.id("RegisterAction_password1")).clear();
    driver.findElement(By.id("RegisterAction_password1")).sendKeys("1234");
    driver.findElement(By.id("RegisterAction_password2")).clear();
    driver.findElement(By.id("RegisterAction_password2")).sendKeys("1234");
    driver.findElement(By.id("RegisterAction_register_submit")).click();
    driver.findElement(By.id("LoginAction_username")).clear();
    driver.findElement(By.id("LoginAction_username")).sendKeys("jdoe");
    driver.findElement(By.id("LoginAction_password")).clear();
    driver.findElement(By.id("LoginAction_password")).sendKeys("1234");
    driver.findElement(By.id("LoginAction__execute")).click();
    try {
      assertEquals("Sie sind eingeloggt als John Done", driver.findElement(By.cssSelector("div.row")).getText());
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
