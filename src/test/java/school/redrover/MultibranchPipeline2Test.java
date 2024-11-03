package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class MultibranchPipeline2Test extends BaseTest {

    @Test
    public void testCreateMultibranchPipeline() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']"))
                .sendKeys("First Multibranch Pipeline project");
        getDriver().findElement(By.xpath("//div[@id='j-add-item-type-standalone-projects']/ul/li[3]"))
                .click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        WebElement actualMultibranchPipelineName = getDriver().
                findElement(By.xpath("//div[@id='breadcrumbBar']//li[3]"));

        Assert.assertEquals(actualMultibranchPipelineName.
                getText(),"First Multibranch Pipeline project");
    }
}
