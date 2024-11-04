package school.redrover;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class FreestyleProject5Test extends BaseTest {
    @Test
    public void testCreateFreestyleProjectValidName() {
        getDriver().findElement(By.xpath("//div[@id='tasks']//div[1]//span[1]//a[1]")).click();
        WebElement name = getDriver().findElement(By.xpath("//input[@id='name']"));
        name.click();
        name.sendKeys("First project");

        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, 400);");
        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='description']")).sendKeys("First project");
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//h1[@class='job-index-headline page-headline']"))
                .getText(), "First project");
    }
}
