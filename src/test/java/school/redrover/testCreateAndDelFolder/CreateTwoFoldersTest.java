package school.redrover.testCreateAndDelFolder;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

public class CreateTwoFoldersTest extends BaseTest {

    @Test
    public void createTwoFolders() {

        ProjectUtils.log("Create first folder");
        createNewItem();
        enterName("Some folder name");
        clickToFolder();
        clickOkButton();
        takeDisplayName("Your advertisement could be in this field");
        clickSaveButton();
        clickDashBoardButton();


        ProjectUtils.log("Create second folder");
        createNewItem();
        enterName("some two folder");
        clickToFolder();
        clickOkButton();
        takeDisplayName("Do you still remember that your advertisement could be in this field");

        getDriver()
                .findElement(By.cssSelector("textarea.jenkins-input"))
                .sendKeys("Advertising on websites from $5/month.\nCall as 555-55-55");

        clickSaveButton();
        clickDashBoardButton();

        String firstFolder = getDriver()
                .findElement(By.xpath("//a[@href='job/Some%20folder%20name/']"))
                .getText();

        String secondFolder = getDriver()
                .findElement(By.xpath("//a[@href='job/some%20two%20folder/']"))
                .getText();

        Assert.assertEquals(firstFolder, "Your advertisement could be in this field");
        Assert.assertEquals(secondFolder, "Do you still remember that your advertisement could be in this field");
    }

    public void clickDashBoardButton() {
        getDriver()
                .findElement(By.cssSelector("li.jenkins-breadcrumbs__list-item:nth-child(1) > a:nth-child(1)"))
                .click();
    }

    public void clickSaveButton() {
        getDriver()
                .findElement(By.cssSelector(".jenkins-submit-button"))
                .click();
    }

    public void takeDisplayName(String displayName) {
        getDriver()
                .findElement(By.cssSelector("input.jenkins-input"))
                .sendKeys(displayName);
    }

    public void clickOkButton() {
        getDriver()
                .findElement(By.cssSelector("#ok-button"))
                .click();
    }

    public void clickToFolder() {
        getDriver()
                .findElement(By.cssSelector(".com_cloudbees_hudson_plugins_folder_Folder"))
                .click();
    }

    public void enterName(String name) {
        getDriver()
                .findElement(By.xpath("//*[@id='name']"))
                .sendKeys(name);
    }

    public void createNewItem() {
        getDriver()
                .findElement(By.xpath("//*[@id=\"tasks\"]/div[1]"))
                .click();
    }
}