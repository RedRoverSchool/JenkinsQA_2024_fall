package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class WorkPipelineTest extends BaseTest {

    @Test
    public void testCreateItem() {

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getWait2();

        getDriver().findElement(By.className("jenkins-input")).sendKeys("First");
        getWait5();

        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[2]")).click();
        getWait2();

        getDriver().findElement(By.xpath("//*[@id=\"ok-button\"]")).click();
        getWait5();

        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();
        String nameProject = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[1]/div[1]/h1")).getText();

        Assert.assertEquals(nameProject, "First");
    }

    @Test(dependsOnMethods = "testCreateItem")
    public void testDeleteItem() {

        getDriver().findElement(By.xpath("//*[@id=\"job_First\"]/td[3]/a/span")).click();

        getDriver().findElement(By.xpath("//*[@id=\"tippy-6\"]/div/div/div/button[2]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"jenkins\"]/dialog/div[3]/button[1]")).click();

        String resultDelete = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/h1")).getText();

        Assert.assertEquals(resultDelete, "Добро пожаловать в Jenkins!");
    }
}
