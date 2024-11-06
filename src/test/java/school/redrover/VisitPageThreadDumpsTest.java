package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class VisitPageThreadDumpsTest extends BaseTest {

    @Test
    public void testVisitPageThreadDumps (){
        getDriver().findElement(By.xpath("//a[@href ='/manage']")).click();

        JavascriptExecutor scrollPage = (JavascriptExecutor) getDriver();
        scrollPage.executeScript("window.scrollBy(0,5000)");

        getDriver().findElement(By.xpath("//a[@href ='systemInfo']")).click();

        getDriver().findElement(By.xpath("//*[contains(text(),'Thread Dumps')]")).click();

        getDriver().findElement(By.xpath("//a[@href ='threadDump']")).click();

        Assert.assertEquals(getDriver().findElement
                (By.xpath("//div/h1")).getText(), "Thread Dump");
    }
}
