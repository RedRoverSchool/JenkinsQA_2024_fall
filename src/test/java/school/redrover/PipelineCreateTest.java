package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineCreateTest extends BaseTest {

    @Test
    public void testCreatePipeline() {

        final String ProjectName = "new Pipeline project";

        getDriver().findElement(By.xpath("//*[@id='main-panel']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ProjectName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']")).click();


        Assert.assertEquals(ProjectName, "new Pipeline project");
    }


    @Test
    public void testAddDescriptionPipeline() {

        getDriver().findElement(By.xpath("//*[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//*[@id='description']/form/div[1]/div[1]/textarea")).sendKeys("New Pipeline work");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        String PipelineStr = getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText();


        Assert.assertEquals(PipelineStr, "New Pipeline work");
    }
}



