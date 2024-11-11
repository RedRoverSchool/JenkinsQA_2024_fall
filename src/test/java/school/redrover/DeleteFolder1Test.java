package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.function.Function;

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

        JavascriptExecutor js = (JavascriptExecutor)getDriver();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));

        createFolder(
                "Some folder name",
                ".com_cloudbees_hudson_plugins_folder_Folder"
        );

        WebElement folderName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"job_Some folder name\"]/td[3]/a/span")
                )
        );

        new Actions(getDriver())
                .moveToElement(folderName)
                .perform();

        WebElement chevronMenu = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"job_Some folder name\"]/td[3]/a/button")
                )
        );

        js.executeScript("arguments[0].dispatchEvent(new Event('moveCursor'));", chevronMenu);
        js.executeScript("arguments[0].dispatchEvent(new Event('click'));", chevronMenu);

        WebElement deleteFolderButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[@href='/job/Some%20folder%20name/doDelete']")
                )
        );
        deleteFolderButton.click();

        WebElement yesButton = wait
                .until(new Function<WebDriver, WebElement>() {
                           @Override
                           public WebElement apply(WebDriver driver) {
                               return driver.findElement(
                                       By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]"));
                           }
                       }
                );
        yesButton.click();

        String welcomeStr = getDriver()
                .findElement(By.cssSelector(".empty-state-block > h1"))
                .getText();

        Assert.assertEquals(welcomeStr, "Welcome to Jenkins!");
    }
}