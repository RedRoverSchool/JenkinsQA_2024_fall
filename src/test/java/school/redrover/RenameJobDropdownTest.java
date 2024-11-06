package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.security.Key;
import java.time.Duration;

public class RenameJobDropdownTest extends BaseTest {

    @Test
    public void tesRenameJobDropdown() {

        final String jobName = "TestBuild";
        final String jobNewName = "TestBuild_NewName";

        createJob(jobName);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        Actions actionJobName = new Actions(getDriver());
        actionJobName
                .moveToElement(getDriver().findElement(By.xpath("//*[@id='job_TestBuild']/td[3]/a/span")));

        Actions actionDropdownChevron = new Actions(getDriver());
        actionDropdownChevron
                .moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='job_TestBuild']/td[3]/a/button"))))
                .click()
                .perform();

        Actions actionRenameLink = new Actions(getDriver());
        actionRenameLink
                .moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[3]/div/div/div/a[4]"))))
                .click()
                .perform();

        WebElement inputFieldNewName = getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/div[1]/div[2]/input"));
        inputFieldNewName.clear();
        inputFieldNewName.sendKeys(jobNewName);

        Actions actionsRenameButton = new Actions(getDriver());
        actionsRenameButton.moveToElement(getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button")))
                .click()
                .perform();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(getDriver().findElement(By
                        .xpath("//*[@class='jenkins-table__link model-link inside']"))
                        .getText(), "TestBuild_NewName");

    }

    private void createJob( String projectName) {

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

    }

}