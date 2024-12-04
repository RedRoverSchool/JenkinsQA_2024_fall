package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
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
}

