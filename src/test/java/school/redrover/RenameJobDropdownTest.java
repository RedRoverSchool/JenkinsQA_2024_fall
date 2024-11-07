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

import java.util.List;

import java.security.Key;
import java.time.Duration;

public class RenameJobDropdownTest extends BaseTest {

    @Test
    public void tesRenameJobDropdown() {

        final String jobName = "TestBuild";
        final String jobNewName = "TestBuild_NewName";

        createJob(jobName);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));

        Actions actionDropdownChevron = new Actions(getDriver());
        actionDropdownChevron
                .moveToElement(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='job_" + jobName + "']/td[3]/a/button"))))
                .click()
                .perform();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //  List<WebElement> items = getDriver().findElements(By.xpath("//div[@class='jenkins-dropdown']"));
        //  List<WebElement> items1 = getDriver().findElements(By.xpath("//div[@class='tippy-content']"));
        List<WebElement> items2 = getDriver().findElements(By.xpath("//div[@class='tippy-content']//a"));
        List<WebElement> items3 = getDriver().findElements(By.xpath("//div[@class='tippy-content']//button"));

        for (WebElement item : items2) {
            if (item.getText().equals("Rename")) {
                item.click();
            }
        }

//        Actions actionRenameLink = new Actions(getDriver());
//        actionRenameLink
//                .moveToElement(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[3]/div/div/div/a[4]"))))
//                .click()
//                .perform();

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

    private void createJob(String projectName) {

        getDriver().findElement(By.xpath("//*[@id='main-panel']/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

    }

}