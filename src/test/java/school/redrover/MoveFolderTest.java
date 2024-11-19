package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;


public class MoveFolderTest extends BaseTest {

    private static final String PARENT_FOLDER = "ParentFolder";
    private static final String CHILD_FOLDER = "ChildFolder";
    private static final String FOLDER_LOCATOR = "//a[@href='job/%s/']";
    private static final String MOVE_FOLDER_FROM_MAIN_MENU = "//a[@href='/job/%s/move']";

    private void createFolder(String folderName) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath(
                "//li[@class='com_cloudbees_hudson_plugins_folder_Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));

        JavascriptExecutor jsExecutor = (JavascriptExecutor) getDriver();
        jsExecutor.executeScript("arguments[0].click();", saveButton);
    }

    private void backToMainPage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testMoveFolderFromDropdownMenu() {

        createFolder(PARENT_FOLDER);
        backToMainPage();
        createFolder(CHILD_FOLDER);
        backToMainPage();

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath(
                "//a[contains(@href, 'job/%s') and contains(@class, 'model-link')]".formatted(CHILD_FOLDER)
                ))).perform();

        WebElement chevron = getDriver().findElement(By.xpath(
                "//span[text()='%s']/following-sibling::button".formatted(CHILD_FOLDER)));

        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@href, 'move')]"
                ))).click();

        new Select(getDriver().findElement(By.name("destination"))).selectByValue("/ParentFolder");

       getDriver().findElement(
                By.xpath("//button[normalize-space(text())='Move']")).click();

       String currentUrl = getDriver().getCurrentUrl();

        assert currentUrl != null;
        Assert.assertTrue(currentUrl.contains("/job/ParentFolder/job/ChildFolder/"),
                "The URL does not contain the expected path: /job/ParentFolder/job/ChildFolder/");

    }

    @Test
    public void testMoveFolderFromFoldersPage() {

        createFolder(PARENT_FOLDER);
        backToMainPage();
        createFolder(CHILD_FOLDER);
        backToMainPage();

        getDriver().findElement(By.xpath(FOLDER_LOCATOR.formatted(CHILD_FOLDER))).click();

        getDriver().findElement(By.xpath(MOVE_FOLDER_FROM_MAIN_MENU.formatted(CHILD_FOLDER))).click();

        new Select(getDriver().findElement(By.name("destination"))).selectByValue("/" + PARENT_FOLDER);

        getDriver().findElement(
                By.xpath("//button[normalize-space(text())='Move']")).click();;

        String getActualUrl = getDriver().getCurrentUrl();

        assert getActualUrl != null;
        Assert.assertTrue(getActualUrl.contains("/job/ParentFolder/job/ChildFolder/"),
                "The URL does not contain the expected path: /job/ParentFolder/job/ChildFolder/");





    }

}
