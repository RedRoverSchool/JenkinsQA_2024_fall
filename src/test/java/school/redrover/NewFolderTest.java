package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewFolderTest extends BaseTest {

    private static final String FIRST_FOLDER_NAME = "FirstFolder";
    private static final String FOLDER_NAME = "admin";

    private void createFolder(String name) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        WebElement textInput = getDriver().findElement(By.xpath("//input[@class='jenkins-input']"));
        textInput.click();
        textInput.sendKeys(FIRST_FOLDER_NAME);

        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-nested-projects']/ul/li[1]/div[2]/div")).click();
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
    }


    @Test
    public void testNewItemFolder() throws InterruptedException {

        createFolder(FIRST_FOLDER_NAME);

        getDriver().findElement(By.xpath("//input[@class='jenkins-input validated  ']")).sendKeys("admin");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String folderName = getDriver().findElement(By.xpath("//*[@id='main-panel']/h1")).getText();
        getWait10();
        Thread.sleep(5000);
        Assert.assertEquals(folderName, FOLDER_NAME);

    }

}

