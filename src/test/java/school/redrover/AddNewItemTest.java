package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AddNewItemTest extends BaseTest {

    @Test
    public void testAddNewItem() {

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();

        WebElement textBox = getDriver().findElement(By.xpath("//input[@name='name']"));
        textBox.sendKeys("My New Projects");

        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();

        getDriver().findElement(By.xpath("//button[@id='ok-button']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        String Str = getDriver().findElement(By.xpath("//h1[@class='job-index-headline page-headline']")).getText();

        Assert.assertEquals(Str, "My New Projects");

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        String String = getDriver().findElement(By.xpath("//*[@id='job_My New Projects']/td[3]/a/span")).getText();

        Assert.assertEquals(String, "My New Projects");

    }

}

