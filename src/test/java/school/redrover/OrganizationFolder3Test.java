package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class OrganizationFolder3Test extends BaseTest {

    @Test
    public void testCreate() {
        getDriver().findElement(By.linkText("Create a job")).click();
        getDriver().findElement(By.name("name")).sendKeys("OrganizationFolder");
        getDriver().findElement(By.xpath("//li[contains(@class,'jenkins_branch_OrganizationFolder')]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[contains(@class,'jenkins-table__cell__button-wrapper')]")).getText(), "Organization Folder");

    }
}
