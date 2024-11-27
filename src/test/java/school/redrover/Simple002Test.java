package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class Simple002Test extends BaseTest {

    @Test
    public void testLinkLogOut() {
        WebElement linkLogOut = getDriver().findElement(By.cssSelector("a[href^='/logout']"));

        Assert.assertNotNull(linkLogOut);
    }
    @Test
    public void testLinkSetUpDistriBuildComp() {

        WebElement linkSetUpDistriBuildComp = getDriver().findElement(By.cssSelector("a[href$='computer/new']"));

        Assert.assertNotNull(linkSetUpDistriBuildComp);
    }

    @Test
    public void testLinkSetUpDistriBuildCloud() {

        WebElement linkSetUpDistriBuildCloud= getDriver().findElement(By.cssSelector("a[href$='cloud/']"));

        Assert.assertNotNull(linkSetUpDistriBuildCloud);
    }

    @Test
    public void testLinkSetUpDistriBuildLearnMore() {

        WebElement linkSetUpDistriBuildLearnMore = getDriver().findElement(By.cssSelector("a[href$='distributed-builds']"));

        Assert.assertNotNull(linkSetUpDistriBuildLearnMore);
    }

}
