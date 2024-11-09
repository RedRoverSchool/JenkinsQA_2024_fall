package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject4Test extends BaseTest {
    private static final String PROJECT_NAME = "New_Freestyle_Project";
    private static final String PROJECT_DESCRIPTION = "About my new freestyle project";

    public void createNewFreestyleProject() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//input[@class='jenkins-input']")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//li[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    @Test
    public void testCheckProjectName() {
        createNewFreestyleProject();

        String actualName = getDriver()
                .findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText();

        Assert.assertEquals(actualName, PROJECT_NAME);
    }

    @Test
    public void testAddDescription() {
        createNewFreestyleProject();

        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name='description']"))
                .sendKeys(PROJECT_DESCRIPTION);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String actualDescription = getDriver()
                .findElement(By.xpath("//*[@id='description']/div"))
                .getText();

        Assert.assertEquals(actualDescription, PROJECT_DESCRIPTION);
    }
}



