package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Node2Test extends BaseTest {

    @Test
    public void testName() {
        final String nodeName = "My name of node";
        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.xpath("//dt[.='Nodes']")).click();

        getDriver().findElement(By.xpath("//a[@href='new']")).click();

        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(nodeName);
        getDriver().findElement(By.xpath("//*[.='Permanent Agent']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[.='My name of node']")).getText(),
                nodeName);
    }
}
