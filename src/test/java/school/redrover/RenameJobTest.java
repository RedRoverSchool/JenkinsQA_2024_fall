package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class RenameJobTest extends BaseTest {

    @Test
    public void tesRenameJob() {

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys("TestBuild");
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
        getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span")).getText();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']")).getText(),"TestBuild");

        WebElement dropdownChevron = getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/button"));
        Actions actionsDropdownChevron = new Actions(getDriver());
        actionsDropdownChevron.moveToElement(dropdownChevron).click().perform();

        WebElement renameLink = getDriver().findElement(By.xpath("//a[@class='jenkins-dropdown__item ' and @href='/job/TestBuild/confirm-rename']"));
        Actions actionsRenameLink = new Actions(getDriver());
        actionsRenameLink.moveToElement(renameLink).click().perform();

        Assert.assertEquals(getDriver().getCurrentUrl(),"http://localhost:8080/job/TestBuild/confirm-rename");

        WebElement inputField = getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input"));
        inputField.clear();
        inputField.sendKeys("TestBuild_NewName");

        WebElement renameButton = getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button"));
        Actions actionsRenameButton = new Actions(getDriver());
        actionsRenameButton.moveToElement(renameButton).click().perform();

        getDriver().findElement(By.id("jenkins-home-link")).click();
        getDriver().findElement(By.xpath("//*[@id='job_TestBuild_NewName']/td[3]/a/span")).getText();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='jenkins-table__link model-link inside']"))
                .getText(),"TestBuild_NewName");

    }

}
