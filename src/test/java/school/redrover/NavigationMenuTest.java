package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class NavigationMenuTest extends BaseTest {

    @Test
    public void testNewItemPointExists () {
        boolean isElementPresent = !getDriver().findElements(By.xpath("//span[contains(text(), 'New Item')]")).isEmpty();
        Assert.assertTrue(isElementPresent);
    }
    @Test
    public void testNewItemPointEnabled () {
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        String actualTitle = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(actualTitle,"New Item");
    }
    @Test
    public void testBuildHistoryExists () {
        boolean isElementPresent = !getDriver().findElements(By.cssSelector("#tasks > div:nth-child(2) > span > a")).isEmpty();
        Assert.assertTrue(isElementPresent);
    }
    @Test
    public void testBuildHistoryEnabled () {
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(2) > span > a")).click();
        String actualEndUrl = getDriver().getCurrentUrl().substring(getDriver().getCurrentUrl().length() - 16);
        Assert.assertEquals(actualEndUrl,"/view/all/builds");
    }
    @Test
    public void testMyViewExists () {
        boolean isElementPresent = !getDriver().findElements(By.xpath("//a[@href ='/me/my-views']")).isEmpty();
        Assert.assertTrue(isElementPresent);
    }
    @Test
    public void testMyViewEnabled () {
        getDriver().findElement(By.xpath("//a[@href ='/me/my-views']")).click();
        String actualEndUrl = getDriver().getCurrentUrl().substring(getDriver().getCurrentUrl().length() - 22);
        Assert.assertEquals(actualEndUrl,"/me/my-views/view/all/");
    }
    @Test
    public void testManageJenkinsExists () {
        boolean isElementPresent = !getDriver().findElements(By.xpath("//span[contains(text(), 'Manage Jenkins')]")).isEmpty();
        Assert.assertTrue(isElementPresent);
    }
    @Test
    public void testManageJenkinsEnabled () {
        getDriver().findElement(By.xpath("//a[@href ='/manage']")).click();
        String actualEndUrl = getDriver().getCurrentUrl().substring(getDriver().getCurrentUrl().length() - 8);
        Assert.assertEquals(actualEndUrl,"/manage/");
    }
}
