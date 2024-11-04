package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class SimpleAppereanceTest extends BaseTest {

    private void grtToAppereanceSection() {
        getDriver().findElement(By.xpath("//a[span[text()='Manage Jenkins']]")).click();
        getDriver().findElement(By.xpath("//dt[text()='Appearance']")).click();
    }

    @Test
    public void darkThemeIsPresenceTest(){
        grtToAppereanceSection();
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='Dark']")).isDisplayed());
    }

    @Test
    public void darkSystemThemeIsPresenceTest(){
        grtToAppereanceSection();
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='Dark (System)']")).isDisplayed());
    }

    @Test
    public void defaultSystemThemeIsPresenceTest(){
        grtToAppereanceSection();
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='Default']")).isDisplayed());
    }

}
