package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class Folder2Test extends BaseTest {

    @Test
    public void testCreateFolder() {

        new HomePage(getDriver()).createNewFolder("SomeFolderName");

        String folder = getDriver()
                .findElement(By.xpath("//span[text()='SomeFolderName']"))
                .getText();

        Assert.assertEquals(folder, "SomeFolderName");
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testDeleteFolder() {

        getDriver().findElement(By.xpath("//span[text()='SomeFolderName']")).click();

        getDriver().findElement(By.xpath("//span[text()='Delete Folder']")).click();

        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        String welcomeStr = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();

        Assert.assertEquals(welcomeStr, "Welcome to Jenkins!");
    }

    @Test
    public void testDeleteFolderViaChevron() {

        new HomePage(getDriver()).createNewFolder("SomeFolderName");

        new Actions(getDriver())
                .moveToElement(getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id='job_SomeFolderName']/td[3]/a/span"))))
                .perform();

        WebElement chevronMenu = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='job_SomeFolderName']/td[3]/a/button")));

        new Actions(getDriver())
                .moveToElement(chevronMenu)
                .pause(500)
                .doubleClick(chevronMenu)
                .perform();

        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronMenu);

        //Yap, here's some fu***g xpath, and only it worked in this case
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("""
                        //button[text() = '
                                  Delete Folder
                                           \s
                                 \s
                              ']"""))).click();

        getWait10().until(driver -> driver.findElement(
                By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]"))).click();

        String welcomeStr = getDriver()
                .findElement(By.cssSelector(".empty-state-block > h1"))
                .getText();

        Assert.assertEquals(welcomeStr, "Welcome to Jenkins!");
    }
}