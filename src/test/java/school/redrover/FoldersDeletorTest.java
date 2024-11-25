package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import static school.redrover.runner.TestUtils.newItemsData;

public class FoldersDeletorTest extends BaseTest {

    private void clickOnSave () {
        getDriver().findElement(By.name("Submit")).click();
    }
    @Ignore
    @Test
    public void testViaMainPageChevron () {
        newItemsData(this,"FolderToRemove",
                "//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div");
        clickOnSave();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/ol/li[1]/a"))).click();

        WebElement projectName = getWait10().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[3]/a/span")));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(projectName).perform();

        WebElement chooseAction = getWait10().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='job_FolderToRemove']/td[3]/a/button")));
        actions.moveToElement(chooseAction).click().perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[3]/div/div/div/button"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/dialog/div[3]/button[1]"))).click();

        Assert.assertEquals(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[2]/div/p"))).getText(),
                "This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
    }

    @Test
    public void testFromProjectPage () {
        newItemsData(this,"FolderToRemove",
                "//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div");
        clickOnSave();

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div[4]/span/a"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/dialog/div[3]/button[1]"))).click();

        Assert.assertEquals(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/div[2]/div[2]/div[2]/div/p"))).getText(),
                "This page is where your Jenkins jobs will be displayed. To get started, you can set up distributed builds or start building a software project.");
    }
    @Ignore
    @Test
    public void testViaMyViewChevron () {
        newItemsData(this,"FolderToRemove",
                "//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div");
        clickOnSave();

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[1]/ol/li[1]/a"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div[4]/span/a"))).click();

        WebElement projectName = getWait10().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr[1]/td[3]/a/span")));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(projectName).perform();

        WebElement chooseAction = getWait10().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='job_FolderToRemove']/td[3]/a/button")));
        actions.moveToElement(chooseAction).click().perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[3]/div/div/div/button"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/dialog/div[3]/button[1]"))).click();

        Assert.assertEquals(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/div[2]/div[2]/div[2]/div/section/h2"))).getText(),
                "This folder is empty");
    }

    @Test
    public void testCancelDeleting () {
        newItemsData(this,"FolderToRemove",
                "//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div");
        clickOnSave();

        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div[4]/span/a"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/dialog/div[3]/button[2]"))).click();

        Assert.assertEquals(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("/html/body/div[2]/div[2]/h1"))).getText(),
                "FolderToRemove");
    }
}
