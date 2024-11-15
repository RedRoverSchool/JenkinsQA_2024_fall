package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineProject4Test extends BaseTest {

    @Test
    public void testCreate() {
        final String projectName = "NewPipelineProject";

        WebElement createButton = getDriver().findElement(
                By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a"));
        createButton.click();

        WebElement inputField = getDriver().findElement(By.id("name"));
        inputField.sendKeys(projectName);

        getDriver().findElement(
                By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[2]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();

        String actualProjectName = getDriver().findElement(
                By.xpath("//*[@id=\"main-panel\"]/div[1]/div[1]/h1")).getText();
        Assert.assertEquals(actualProjectName, projectName);
    }
}

