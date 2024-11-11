package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;

import java.time.Duration;

public class DeleteFolder1Test extends BaseTest {

    public void createFolder(String name, String locator) {

        getDriver()
                .findElement(By.xpath("//a[@href='newJob']"))
                .click();

        getDriver()
                .findElement(By.xpath("//*[@id='name']"))
                .sendKeys(name);

        getDriver()
                .findElement(By.cssSelector(locator))
                .click();

        getDriver()
                .findElement(By.cssSelector("#ok-button"))
                .click();

        getDriver()
                .findElement(By.cssSelector("input.jenkins-input"))
                .sendKeys(name);

        getDriver()
                .findElement(By.cssSelector("textarea.jenkins-input"))
                .sendKeys("Advertising on websites from $5/month.\nCall as 555-55-55");

        getDriver()
                .findElement(By.cssSelector(".jenkins-submit-button"))
                .click();

        getDriver()
                .findElement(By.cssSelector("li.jenkins-breadcrumbs__list-item:nth-child(1) > a:nth-child(1)"))
                .click();
    }

    @Test
    public void testDeleteFolderInChevronMenu() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createFolder(
                "Some folder name",
                ".com_cloudbees_hudson_plugins_folder_Folder"
        );

        WebElement folderName = getDriver()
                .findElement(By.xpath("//*[@id=\"job_Some folder name\"]/td[3]/a/span"));

        new Actions(getDriver())
                .moveToElement(folderName)
                .perform();

        WebElement chevronMenu = getDriver()
                .findElement(By.xpath("//*[@id=\"job_Some folder name\"]/td[3]/a/button"));

        new Actions(getDriver())
                .moveToElement(chevronMenu)
                .click(chevronMenu)
                .perform();

        chevronMenu = wait
                .until(driver -> driver.findElement(By.xpath("//*[@id='tippy-6']/div/div/div/button")));

        chevronMenu.click();

        WebElement yesButton = wait
                .until(driver -> driver.findElement(By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]")));
        yesButton.click();

        String welcomeStr = getDriver()
                .findElement(By.cssSelector(".empty-state-block > h1"))
                .getText();

        Assert.assertEquals(welcomeStr, "Welcome to Jenkins!");
    }
}