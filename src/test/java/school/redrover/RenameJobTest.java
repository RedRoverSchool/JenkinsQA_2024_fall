package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RenameJobTest extends BaseTest {

    @Test
    public void tesRenameJob(){

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("TestBuild");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
        getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span")).getText();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span")).getText(),"TestBuild");

        WebElement dropdownChevron = getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/button"));
        JavascriptExecutor jsDropdownChevron = (JavascriptExecutor) getDriver();
        jsDropdownChevron.executeScript("arguments[0].click();", dropdownChevron);
       // getDriver().findElement(By.xpath("//*[@id='tippy-6']/div/div/div/a[4]")).click();

        WebElement renameButton = getDriver().findElement(By.xpath("//*[@id='tippy-6']/div/div/div"));
        JavascriptExecutor jsRenameButton = (JavascriptExecutor) getDriver();
        jsRenameButton.executeScript("arguments[0].click();", renameButton);




    }

}
