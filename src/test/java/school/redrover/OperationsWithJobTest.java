package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class OperationsWithJobTest extends BaseTest {


    @Test(description = "create and changes job")
    public void testChangesJob(){

        createJob("TestBuild");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span"))
                .getText(),"TestBuild");

        navigateToManageJob();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='no-builds']"))
                .getText(),"No builds");

    }
    @Test(description = "create and setup workspace job")
    public void testWorkspaceJob(){

        createJob("TestBuild");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span"))
                .getText(),"TestBuild");

        navigateToManageJob();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='no-builds']"))
                .getText(),"No builds");

    }
    @Test(description = "create and build job")
    public void testBuildJob(){

        createJob("TestBuild");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span"))
                .getText(),"TestBuild");

        navigateToManageJob();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='no-builds']"))
                .getText(),"No builds");

        buildJob();

        Assert.assertFalse(getDriver()
                .findElement(By.xpath("//*[@id='no-builds']"))
                .isDisplayed());

    }

    @Test(description = "create and configure job")
    public void testConfigureJob(){

        createJob("TestBuild");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span"))
                .getText(),"TestBuild");

        navigateToManageJob();
        configureJob();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1"))
                .getText(), "TestBuild");

    }


    @Test(description = "create and delete job")
    public void testDeleteJob(){
        createJob("TestBuild");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span"))
                .getText(),"TestBuild");

        navigateToManageJob();
        deleteJob();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='main-panel']/div[2]/div/h1"))
                .getText(), "Welcome to Jenkins!");

    }

    @Test(description = "create and rename job")
    public void testRenameJob() {

        createJob("TestBuild");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span"))
                .getText(),"TestBuild");

        navigateToManageJob();
        renameJob("TestBuild_NewName");

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']"))
                .getText(), "TestBuild_NewName");

    }

    private void buildJob() {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement toolTipSuccess = wait
                .until(ExpectedConditions
                        .elementToBeClickable(By
                                .xpath("//*[@id='buildHistory']/div[2]/table/tbody/tr[2]/td/div[1]/div[1]/div/a")));
        Assert.assertTrue(toolTipSuccess.isDisplayed(),"true");
    }

    private void configureJob() {

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[5]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

    }

    private void createJob( String projectName) {

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

    }

    private void deleteJob() {

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[6]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]")).click();

    }

    private void renameJob(String testBuildNewName){

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[7]/span/a")).click();
        WebElement inputField = getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input"));
        inputField.clear();
        inputField.sendKeys(testBuildNewName);
        WebElement renameButton = getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button"));
        Actions actionsRenameButton = new Actions(getDriver());
        actionsRenameButton.moveToElement(renameButton).click().perform();
        getDriver().findElement(By.id("jenkins-home-link")).click();

    }

    private void navigateToManageJob() {
        getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span")).click();
    }

}
