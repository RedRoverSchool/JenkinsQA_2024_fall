package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertTrue;

public class HeaderTest extends BaseTest {

    @Test
    public void testJenkinsLogoExist() {
        WebElement logo = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"));
        assertTrue(logo.isDisplayed(), "Jenkins logo is not displayed in the header.");

    }

    @Test
    public void testDemensionLogo() {
        WebElement logo = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"));
        Dimension logoSize = logo.getSize();
        assertTrue(logoSize.getWidth() > 0 && logoSize.getHeight() > 0, "Logo should have proper dimensions");

    }

    @Test
    public void testFullNameHelperText(){
        getDriver().findElement(By.xpath("//*[@href='/user/admin']")).click();
        getDriver().findElement(By.xpath("//*[@href='/user/admin/configure']")).click();
        getDriver().findElement(By.xpath("//a[@title='Help for feature: Full Name']")).click();

        String fullNameFieldHelperText = getDriver().findElement(By.xpath("//*[@class='help']/div")).getText();
        Assert.assertEquals(fullNameFieldHelperText, "Specify your name in a more human-friendly format, so that people can see your real name as opposed to your ID. For example, \"Jane Doe\" is usually easier for people to understand than IDs like \"jd513\".");
    }

    @Test
    public void testLinkLogOut() {
        WebElement linkLogOut = getDriver().findElement(By.cssSelector("a[href^='/logout']"));

        Assert.assertNotNull(linkLogOut);
    }

    @Test
    public void testGetStatusPage() {
        Actions actions = new Actions(getDriver());

        WebElement dropdown = getDriver().findElement(By.xpath("(//button[@class='jenkins-menu-dropdown-chevron'])[1]"));
        actions.moveToElement(dropdown).click().perform();

        getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[contains(@href,'/user/admin/configure')]")
                )).click();

        getDriver().findElement(By.xpath("//span[contains(@class,'task-link-wrapper')]/a[@href='/user/admin/']")).click();

        String status = getDriver().findElement(By.xpath("//div[@id='main-panel']/div[3]")).getText();

        Assert.assertEquals(status, "Jenkins User ID: admin");
    }
}

