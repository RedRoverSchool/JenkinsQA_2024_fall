package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class AddDescriptionTest extends BaseTest {

    @Test
    public void testAddDescription(){

    getDriver().findElement(By.id("description-link")).click();

    WebElement textBox = getDriver().findElement(By.className("jenkins-input"));
    textBox.click();
    textBox.sendKeys("Hi!");


    getDriver().findElement(By.xpath("//*[@id='description']/form/div[2]/button")).click();

    String hiStr = getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText();

        Assert.assertEquals(hiStr, "Hi!");
}
}
