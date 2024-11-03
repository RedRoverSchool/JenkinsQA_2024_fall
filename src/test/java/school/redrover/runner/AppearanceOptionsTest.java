package school.redrover.runner;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppearanceOptionsTest extends BaseTest{

    @Test
    public void selectOptionsOfThemesArePresentedTest(){

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        getDriver().findElement(By.xpath("//div[@class='jenkins-section__item']//a[@href='appearance']")).click();
        String color = getDriver().findElement(By.xpath("//span[text()='Dark']")).getText();
        Assert.assertEquals(color, "Dark");
        color = getDriver().findElement(By.xpath("//span[text()='Dark (System)']")).getText();
        Assert.assertEquals(color, "Dark (System)");
        color = getDriver().findElement(By.xpath("//span[text()='Default']")).getText();
        Assert.assertEquals(color, "Default");
    }


}
