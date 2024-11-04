package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static java.sql.DriverManager.getDriver;
import static org.testng.Assert.assertTrue;

public class VologdaTest extends BaseTest {

    @Test
    public void testJenkinsVologda() {
        WebElement logo = getDriver().findElement(By.xpath("//img[@id='jenkins-name-icon']"));
        assertTrue(logo.isDisplayed(), "Jenkins logo is not displayed in the header.");

    }

}
