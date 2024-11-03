package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject1Test extends BaseTest {

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.xpath("//*[@class='hudson_model_FreeStyleProject']")).click();
        getDriver().findElement(By.name("name")).sendKeys("New freestyle project");
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 500);");
        getDriver().findElement(By.id("ok-button")).click();
        Thread.sleep(1000);
        js.executeScript("window.scrollBy(0, 1000);");
        getDriver().findElement(By.name("Submit")).click();
        Thread.sleep(1000);
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        WebElement element = getDriver().findElement(By.xpath("//span[contains(text(),'New freestyle project')]"));
        Assert.assertTrue(element.isDisplayed());
    }
}
