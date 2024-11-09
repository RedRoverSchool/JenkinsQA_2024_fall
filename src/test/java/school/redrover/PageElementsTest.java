package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class PageElementsTest extends BaseTest {

    private void newItemsData(String itemName, String itemXpath){
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.xpath(itemXpath)).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testNewItem (){
        String newItemElement = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).getText();
        Assert.assertEquals(newItemElement, "New Item");
    }

    @Test
    public void testBuildHistory (){
        String buildHistory = getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).getText();
        Assert.assertEquals(buildHistory, "Build History");
    }

    @Test
    public void testManageJenkins (){
        String manageJenkins = getDriver().findElement(By.xpath("//*[@id='tasks']/div[3]/span/a")).getText();
        Assert.assertEquals(manageJenkins, "Manage Jenkins");
    }

    @Test
    public void testMyView (){
        String myView = getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a")).getText();
        Assert.assertEquals(myView, "My Views");
    }

    @Test
    public void testAddDescription (){
        String addDescription = getDriver().findElement(By.xpath("//*[@id='description-link']")).getText();
        Assert.assertEquals(addDescription, "Add description");
    }

    @Test
    public void testVerifyVersion (){
        String version = getDriver().findElement(By.xpath("//*[@id='jenkins']/footer/div/div[2]/button")).getText();
        Assert.assertEquals(version, "Jenkins 2.462.3");
    }

    @Test
    public void testWriteDescription (){
        getDriver().findElement(By.xpath("//*[@id='description-link']")).click();
        getDriver().findElement(By.name("description")).sendKeys("The test");
        getDriver().findElement(By.name("Submit")).click();

        String getDescription = getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText();
        Assert.assertEquals(getDescription, "The test");
    }

    @Test
    public void testNewItemValidationName (){
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]/div[2]/div")).click();

        String validationText = getDriver().findElement(By.id("itemname-required")).getText();
        Assert.assertEquals(validationText, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testNewItemValidationOKButton (){
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        boolean state = getDriver().findElement(By.id("ok-button")).isEnabled();

        Assert.assertFalse(state);
    }

    @Test
    @Ignore
    public void testNewPipeline (){
        newItemsData("PipeTest", "//*[@id='j-add-item-type-standalone-projects']/ul/li[2]/div[2]/label");

        getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[3]/div[1]/div/span/label")).click();
        getDriver().findElement(By.name("_.daysToKeepStr")).sendKeys("5");

        getDriver().findElement(By.name("Submit")).click();

        String result = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1")).getText();

        Assert.assertEquals(result, "PipeTest");
    }

    @Test
    @Ignore
    public void testNewFreeStyleProject (){
        newItemsData("FreeStyleProjectTest", "//*[@id='j-add-item-type-standalone-projects']/ul/li[1]/div[2]/label");

        getDriver().findElement(By.name("Submit")).click();

        String result = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1")).getText();

        Assert.assertEquals(result, "FreeStyleProjectTest");
    }

    @Test
    public void testNewMultiConfigurationProject(){
        newItemsData("MultiConfigurationProjectTest", "//*[@id='j-add-item-type-standalone-projects']/ul/li[3]/div[2]/label");

        getDriver().findElement(By.name("Submit")).click();

        String result = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();

        Assert.assertEquals(result, "Project MultiConfigurationProjectTest");
    }

    @Test
    public void testNewFolder(){
        newItemsData("newFolderTest", "//*[@id='j-add-item-type-standalone-projects']/ul/li[3]/div[2]/label");

        getDriver().findElement(By.id("jenkins")).sendKeys("Testing Folder");
        getDriver().findElement(By.name("Submit")).click();

        String result = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();

        Assert.assertEquals(result, "Project newFolderTest");
    }

    @Ignore
    @Test
    public void testNewMultibranchPipeline(){
        newItemsData("newMultibranchPipelineTest", "//*[@id='j-add-item-type-nested-projects']/ul/li[2]/div[2]/label");

        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys("Testing MultibranchPipeline");
        getDriver().findElement(By.name("Submit")).click();

        String result = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();

        Assert.assertEquals(result, "Testing MultibranchPipeline");
    }

    @Ignore
    @Test
    public void testNewOrganizationFolder(){
        newItemsData("newOrganizationFolderTest", "//*[@id='j-add-item-type-nested-projects']/ul/li[3]/div[2]/label/span");

        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys("Testing OrganizationFolder");
        getDriver().findElement(By.name("Submit")).click();

        String result = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();

        Assert.assertEquals(result, "Testing OrganizationFolder");
    }

    @Ignore
    @Test
    public void testFreeStyleProjectsHovers() {
        newItemsData("FreeStyleProjectTestHovers", "//*[@id='j-add-item-type-standalone-projects']/ul/li[1]/div[2]/label");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement oldBuilds = getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[3]/div[1]/div/a"));
        Actions hover1 = new Actions(getDriver());
        hover1.moveToElement(oldBuilds).perform();
        String result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tippy-15"))).getText();

        Assert.assertEquals(result, "Help for feature: Discard old builds");

    }

}
