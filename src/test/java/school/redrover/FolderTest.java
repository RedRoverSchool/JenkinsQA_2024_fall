package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FolderTest extends BaseTest {

    @Test
    public void testCreateNewFolderWithoutConfiguration(){

        WebElement newJobButton = getDriver().findElement(By.xpath("//a[@href='newJob']"));
        newJobButton.click();

        WebElement nameInput = getDriver().findElement(By.xpath("//input[@id='name']"));
        nameInput.sendKeys("First folder");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 500);");

        WebElement folderRadioButton = getDriver().findElement(By.xpath("//li[@class='com_cloudbees_hudson_plugins_folder_Folder']"));
        folderRadioButton.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]"));
        saveButton.click();

        String title = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(title, "First folder");
    }
}
