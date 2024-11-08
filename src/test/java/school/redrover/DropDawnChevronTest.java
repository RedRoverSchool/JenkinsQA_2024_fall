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

public class DropDawnChevronTest extends BaseTest {

    @Test
    public void testDropDawnNewItem () {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.id("name")).sendKeys("TestDropDawnChevron");
        getDriver().findElement(By.cssSelector("#jenkins")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("description")).sendKeys("TestDropDawnChevron");


        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 2000);");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        Actions actions = new Actions(getDriver());

        WebElement mainButton = getDriver().findElement(By.xpath("//*[@id='job_TestDropDawnChevron']/td[3]/a/span"));
        actions.moveToElement(mainButton).perform();

        WebElement hiddenButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='job_TestDropDawnChevron']/td[3]/a/button")));
        actions.moveToElement(hiddenButton)
                .pause(java.time.Duration.ofSeconds(1)).click().perform();

        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='breadcrumbs']/li[1]/a")));
        actions.moveToElement(dropdown)
                .pause(java.time.Duration.ofSeconds(1)).perform();

        getDriver().findElement(By.xpath("//*[@id='tippy-6']/div/div/div/button[2]")).click();
        getDriver().findElement(By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]")).click();

        boolean isElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='tippy-6']/div/div/div/button[2]")));
        Assert.assertTrue(isElementInvisible, "Элемент все еще присутствует после клика, хотя должен был исчезнуть");
    }
}



