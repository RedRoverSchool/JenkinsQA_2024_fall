package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class DeleteFolder1Test extends BaseTest {

    public void createFolder(String name) {

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(name);

        getDriver().findElement(By.cssSelector(".com_cloudbees_hudson_plugins_folder_Folder")).click();

        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getDriver().findElement(By.cssSelector("input.jenkins-input")).sendKeys(name);

//        getDriver().findElement(By.cssSelector("textarea.jenkins-input")).sendKeys(
//                "Advertising on websites from $5/month.\nCall as 555-55-55");

        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();

        getDriver().findElement(By.cssSelector("li.jenkins-breadcrumbs__list-item:nth-child(1) > a:nth-child(1)")).click();
    }

    @Test
    public void testDeleteFolderInChevronMenu() {

        createFolder("SomeFolderName");

        new Actions(getDriver())
                .moveToElement(getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='job_SomeFolderName']/td[3]/a/span"))))
                .perform();

        WebElement chevronMenu = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='job_SomeFolderName']/td[3]/a/button")));

        new Actions(getDriver())
                .moveToElement(chevronMenu)
                .pause(500)
                .click(chevronMenu)
                .perform();

        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronMenu);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@href='/job/SomeFolderName/doDelete']"))).click();

        getWait10().until(driver -> driver.findElement(
                By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]"))).click();

        String welcomeStr = getDriver()
                .findElement(By.cssSelector(".empty-state-block > h1"))
                .getText();

        Assert.assertEquals(welcomeStr, "Welcome to Jenkins!");
    }
}