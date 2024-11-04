package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class FreestyleProject26Test extends BaseTest {

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        final String freestyleProjectName = "New Freestyle Project";

        List<WebElement> sideBarElements = getDriver().findElements(By.xpath("//span[@class = 'task-link-wrapper ']//a"));
        for (WebElement element : sideBarElements) {
            if(element.getText().contains("New Item")) {
                element.click();
                break;
            }
        }
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Thread.sleep(2000);
        getDriver().findElement(By.name("Submit")).click();

        Thread.sleep(2000);
        String getTitle = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(getTitle, freestyleProjectName);
    }

    @Test
    public void testDeleteFromDashboard() throws InterruptedException{
        final String freestyleProjectName = "New Freestyle Project";

        List<WebElement> sideBarElements = getDriver().findElements(By.xpath("//span[@class = 'task-link-wrapper ']//a"));
        for (WebElement element : sideBarElements) {
            if(element.getText().contains("New Item")) {
                element.click();
                break;
            }
        }
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Thread.sleep(2000);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[text() = 'Dashboard']")).click();

        WebElement linkByName = getDriver().findElement(By.linkText(freestyleProjectName));
        Actions action = new Actions(getDriver());
        action.moveToElement(linkByName).build().perform();

        WebElement dropDownButton = linkByName.findElement(By.cssSelector("button"));
        new WebDriverWait(getDriver(), Duration.ofMillis(300L)).until(ExpectedConditions.elementToBeClickable(dropDownButton)).click();

        List<WebElement> jobItems = getDriver().findElements(By.xpath("//button[@class = 'jenkins-dropdown__item ']"));
        for (WebElement item : jobItems) {
            if(item.getText().contains("Delete Project")) {
                item.click();
                break;
            }
        }
        getDriver().findElement(By.xpath("//button[text() =  'Yes']")).click();

        List<WebElement> remainingItems = getDriver().findElements(By.xpath("//a[@class = 'jenkins-table__link model-link inside']/span"));
        for (WebElement item : remainingItems) {
            Assert.assertNotEquals(item.getText(), freestyleProjectName);
        }
    }
}
