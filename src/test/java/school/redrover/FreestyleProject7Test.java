package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class FreestyleProject7Test extends BaseTest {

    @Test
    public void testCreateNewProject() {

        final String newJobName = "Project";

        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@name = 'name']")).sendKeys(newJobName);
        getDriver().findElement(By.xpath("//span[contains(text(), 'Freestyle project')]")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,250)", "");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(1000));
        wait.until(ExpectedConditions
                .elementToBeClickable(getDriver().findElement(By.cssSelector("#ok-button")))).click();

        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();
        getDriver().findElement(By.cssSelector("#jenkins-name-icon")).click();

        List<WebElement> jobsList = getDriver().findElements(By
                .xpath("//table[@id = 'projectstatus']/tbody/tr/td/a[@href]"));

        List<String> jobsString = jobsList
                .stream()
                .map(i -> i.getAttribute("href").split("/")[4])
                .toList();

        Assert.assertTrue(jobsString.contains(newJobName));
    }
}
