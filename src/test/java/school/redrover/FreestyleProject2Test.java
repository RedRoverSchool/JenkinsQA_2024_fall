package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject2Test extends BaseTest {

    public void createFreestyleProject(String nameFreestyleProject) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(nameFreestyleProject);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testEditDescription(){
        final String PROJECT_DESCRIPTION = "my new build";
        final String editDescribe = "Create one more build apps";

        String PROJECT_NAME = "Freestyle_first";
        createFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea")).sendKeys(PROJECT_DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins-home-link']")).click();

        getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']")).click();
        getDriver().findElement(By.xpath("//*[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//*[@name='description']")).clear();
        getDriver().findElement(By.xpath("//*[@name='description']")).sendKeys(editDescribe);
        getDriver().findElement(By.xpath("//*[@id='description']/form/div[2]/button")).click();

        Assert.assertTrue(getDriver().findElement(By.id("description")).getText().contains(editDescribe));
    }
}
