package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class FreestyleProject1Test extends BaseTest {
    private static final String NEW_FREESTYLE_PROJECT_NAME = "New freestyle project";

    private void createFreestyleProject() throws InterruptedException {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.name("name")).sendKeys(NEW_FREESTYLE_PROJECT_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

    }

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        createFreestyleProject();

        //Getting to the main page to ensure that newly created project is there
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'New freestyle project')]"));
        Assert.assertTrue(element.isDisplayed());
    }

    @Test
    public void testDeleteFreestyleProject() throws InterruptedException {
        createFreestyleProject();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
        Actions actions = new Actions(getDriver());
        //hover over project title to activate menu dropdown
        WebElement element = getDriver().findElement(By
                .xpath("//span[contains(text(),'New freestyle project')]"));
        actions.moveToElement(element).perform();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement chevron = wait.until(ExpectedConditions.elementToBeClickable(By
                .xpath("//*[@id='job_New freestyle project']/td[3]/a/button")));
        actions.moveToElement(chevron).perform();
        //using js to click the middle of the element
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new MouseEvent('click', " +
                        "{bubbles: true, cancelable: true, view: window, clientX: " +
                        "arguments[0].getBoundingClientRect().x + 5, " +
                        "clientY: arguments[0].getBoundingClientRect().y + 5}));", chevron);
        WebElement deleteOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(@href,'doDelete')]")));
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new MouseEvent('click', " +
                        "{bubbles: true, cancelable: true, view: window, clientX: " +
                        "arguments[0].getBoundingClientRect().x + 5, " +
                        "clientY: arguments[0].getBoundingClientRect().y + 5}));", deleteOption);
        getDriver().findElement(By.xpath("//button[contains(text(),'Yes')]")).click();
        String emptyDashboardHeader = getDriver().findElement(By.cssSelector(".empty-state-block > h1")).getText();
        Assert.assertEquals(emptyDashboardHeader, "Welcome to Jenkins!");
    }
}