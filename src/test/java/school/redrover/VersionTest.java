package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
@Ignore
public class VersionTest extends BaseTest {
    @Test
    public void CheckVersionTest() {

        WebElement version = getDriver().findElement(By.xpath("//button[@type='button']"));
        new Actions(getDriver())
                .scrollToElement(version)
                .perform();
        version.click();

        getDriver().findElement(By.xpath("//a[normalize-space()='About Jenkins']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.className("app-about-version"))
                .getText(), "Version 2.462.3");


    }

}
