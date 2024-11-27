package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import java.util.List;

import static school.redrover.runner.TestUtils.newItemsData;

public class FoldersMovingTest extends BaseTest {

    private static final String FOLDER_XPATH = "//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div";

    private void clickOnSave () {
        getDriver().findElement(By.name("Submit")).click();
    }

    private String getFolderExtension () {
        return getDriver().findElement(By.id("breadcrumbBar")).getText();
    }

    private void setDestinationFolder (String selectPath) {
        WebElement folderDestination = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("destination")));
        Select select = new Select(folderDestination);
        select.selectByVisibleText(selectPath);
        getDriver().findElement(By.name("Submit")).click();
    }

    private void clickOnMove () {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div[6]/span/a"))).click();
    }

    private void goToTheMainPage () {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='breadcrumbs']/li[1]/a"))).click();
    }

    @Test
    public void testMoveFromFoldersPage () {
        newItemsData(this, "FolderOne", FOLDER_XPATH);
        clickOnSave();
        goToTheMainPage();

        newItemsData(this, "FolderTwo", FOLDER_XPATH);
        clickOnSave();
        clickOnMove();
        setDestinationFolder("Jenkins » FolderOne");

        Assert.assertEquals(getFolderExtension(), "Dashboard\n" +
                "FolderOne\n" +
                "FolderTwo");
    }

    @Test(dependsOnMethods = "testMoveFromFoldersPage")
    public void testMoveFromTheSameLevel () {
        newItemsData(this, "FolderThree", FOLDER_XPATH);
        clickOnSave();
        clickOnMove();
        setDestinationFolder("Jenkins » FolderOne");

        Assert.assertEquals(getFolderExtension(), "Dashboard\n" +
                "FolderOne\n" +
                "FolderThree");
    }

    @Test(dependsOnMethods = "testMoveFromTheSameLevel")
    public void testTryToMoveInTheSamePlace () {
       getWait10().until(ExpectedConditions.visibilityOfElementLocated(
               By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr/td[3]/a/span"))).click();
       getWait10().until(ExpectedConditions.visibilityOfElementLocated(
               By.xpath("/html/body/div[2]/div[2]/div[3]/div[2]/table/tbody/tr[1]/td[3]/a/span"))).click();

        clickOnMove();
        setDestinationFolder("Jenkins » FolderOne");

        Assert.assertEquals(getFolderExtension(), "Dashboard\n" +
                "FolderOne\n" +
                "FolderThree\n" +
                "Move");
    }

    @Test(dependsOnMethods = "testTryToMoveInTheSamePlace")
    public void testMoveOnTheHigherLevel () {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr/td[3]/a/span"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[3]/div[2]/table/tbody/tr[1]/td[3]/a/span"))).click();

        clickOnMove();
        setDestinationFolder("Jenkins");

        Assert.assertEquals(getFolderExtension(), "Dashboard\n" +
                "FolderThree");
    }

    @Test(dependsOnMethods = "testMoveOnTheHigherLevel")
    public void testNoOptionsToMoveParentIntoChild () {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr/td[3]/a/span"))).click();
        clickOnMove();

        List<String> available = getDriver().findElements(By.name("destination")).stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertFalse(available.toString().contains("FolderTwo"));
    }
}
