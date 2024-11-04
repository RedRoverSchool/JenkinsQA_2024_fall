package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItemIsDisabledTest extends BaseTest {

    @Test
    public void testNewItemIsDisabledMessageOnJobPage() {

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("NewJob");
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.className("jenkins-toggle-switch__label")).click();
        getDriver().findElement(By.name("Submit")).click();

        String actualText = getDriver().findElement(By.className("warning")).getText().split("\n")[0];

        Assert.assertEquals(actualText, "This project is currently disabled");
    }

    @Test
    public void testNewItemIsDisabledOnMainPage() {
        final String newJobName = "NewJob";

        getDriver().findElement(By.cssSelector("[href$='/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(newJobName);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.className("jenkins-toggle-switch__label")).click();
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//a[text()='Dashboard']")).click();

        String actualTooltipValue = getDriver().findElement(By.cssSelector("#job_" + newJobName + "> td:nth-of-type(1) > div > svg")).getAttribute("tooltip");

        Assert.assertEquals(actualTooltipValue, "Disabled");
    }
}
