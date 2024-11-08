package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


import java.time.Duration;

public class NewItemFromOtherExistingTest extends BaseTest {

    private final static String FIRST_ITEM_NAME = "My_First_Project";
    private final static String SECOND_ITEM_NAME = "My_Second_Project";

    private void createNewItem(String itemTypeXpath) {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement newItemButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@it='hudson.model.Hudson@32028caf']")));
        newItemButton.click();

        WebElement inputItemNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        inputItemNameField.sendKeys(FIRST_ITEM_NAME);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        WebElement organizationFolder = getDriver().findElement(By.xpath("//span[text()='Organization Folder']"));
        js.executeScript("arguments[0].scrollIntoView(true);", organizationFolder);

        getDriver().findElement(By.xpath(itemTypeXpath)).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/view/all/newJob']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
    }

    private WebElement createNewItemFromExistingOne(String itemTypeXpath) {
        createNewItem(itemTypeXpath);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement inputToFindItemByName = getDriver().findElement(By.xpath("//input[@placeholder='Type to autocomplete']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", inputToFindItemByName);
        inputToFindItemByName.sendKeys(FIRST_ITEM_NAME);

        WebElement inputItemNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        inputItemNameField.sendKeys(SECOND_ITEM_NAME);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Dashboard']"))).click();

        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='job/" + SECOND_ITEM_NAME + "/']")));
    }

    @Test
    public void testCreateFreeStyleProjectFromExisting() {
        WebElement secondFreestyleProject = createNewItemFromExistingOne("//span[text()='Freestyle project']");
        Assert.assertEquals(secondFreestyleProject.getText(), SECOND_ITEM_NAME);
    }

    @Test
    public void testCreatePipelineProjectFromExisting() {
        WebElement secondPipelineProject = createNewItemFromExistingOne("//span[text()='Pipeline']");
        Assert.assertEquals(secondPipelineProject.getText(), SECOND_ITEM_NAME);
    }

    @Test
    public void testCreateMultiConfigurationProjectFromExisting() {
        WebElement secondMultiConfigurationProject = createNewItemFromExistingOne("//span[text()='Multi-configuration project']");
        Assert.assertEquals(secondMultiConfigurationProject.getText(), SECOND_ITEM_NAME);
    }

    @Test
    public void testCreateFolderProjectFromExisting() {
        WebElement secondFolderProject = createNewItemFromExistingOne("//span[text()='Folder']");
        Assert.assertEquals(secondFolderProject.getText(), SECOND_ITEM_NAME);
    }

    @Test
    public void testCreateMultibranchPipelineProjectFromExisting() {
        WebElement secondMultibranchPipelineProject = createNewItemFromExistingOne("//span[text()='Multibranch Pipeline']");
        Assert.assertEquals(secondMultibranchPipelineProject.getText(), SECOND_ITEM_NAME);
    }

    @Test
    public void testOrganizationFolderProjectFromExisting() {
        WebElement secondOrganizationFolderProject = createNewItemFromExistingOne("//span[text()='Organization Folder']");
        Assert.assertEquals(secondOrganizationFolderProject.getText(), SECOND_ITEM_NAME);
    }
}





