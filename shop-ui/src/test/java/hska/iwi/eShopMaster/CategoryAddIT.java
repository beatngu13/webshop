package hska.iwi.eShopMaster;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CategoryAddIT {
    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Test
    public void testCategoryAdd() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(IntegrationTestsConfig.IMPLICIT_WAIT, TimeUnit.SECONDS);
        driver.get(IntegrationTestsConfig.BASE_URL);
        driver.findElement(By.id("LoginAction_username")).clear();
        driver.findElement(By.id("LoginAction_username")).sendKeys("admin");
        driver.findElement(By.id("LoginAction_password")).clear();
        driver.findElement(By.id("LoginAction_password")).sendKeys("admin");
        driver.findElement(By.id("LoginAction__execute")).click();
        
        if (Locale.getDefault().toString().equalsIgnoreCase("en_US")) {
        	driver.findElement(By.linkText("Edit categories")).click();
        } else {
        	driver.findElement(By.linkText("Kategorien bearbeiten")).click();
        }
        
        driver.findElement(By.id("AddCategoryAction_newCatName")).clear();
        driver.findElement(By.id("AddCategoryAction_newCatName")).sendKeys("Alkohol");
        driver.findElement(By.id("AddCategoryAction_category_submit")).click();
        try {
            assertEquals("Alkohol", driver.findElement(By.xpath("//div[@id='categories']/table/tbody/tr[3]/td[2]")).getText());
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
