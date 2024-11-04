package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
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

}

