package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import org.openqa.selenium.interactions.Actions;

public class GetAllBuildsTest extends BaseTest {

    @Test
    public void testButtonBuilds() {
        getDriver().findElement(By.xpath("//div[@class='task '][2]/span/a")).click();

        String title = getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']")).getText();

        Assert.assertEquals(title, "Build History of Jenkins");
    }

    @Test
    public void testConfigurePage() throws InterruptedException {

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//div[@class='login page-header__hyperlinks']/a/button"))).click().perform();
        Thread.sleep(500);

        getDriver().findElement(By.xpath("//div[@class='jenkins-dropdown']/a[2]")).click();
        Thread.sleep(500);

        String str = getDriver().findElement(By.xpath("//div[@class='jenkins-breadcrumbs']/ol/li[5]")).getText();

        Assert.assertEquals(str, "Configure");
    }
}