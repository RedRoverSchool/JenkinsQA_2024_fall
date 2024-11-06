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
        String urlBeforeClick = getDriver().getCurrentUrl();
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(2) > span > a")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(),urlBeforeClick+"view/all/builds");
    }
    @Test
    public void testMyViewExists () {
        boolean isElementPresent = !getDriver().findElements(By.xpath("//a[@href ='/me/my-views']")).isEmpty();
        Assert.assertTrue(isElementPresent);
    }
    @Test
    public void testMyViewEnabled () {
        String urlBeforeClick = getDriver().getCurrentUrl();
        getDriver().findElement(By.xpath("//a[@href ='/me/my-views']")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(), urlBeforeClick+"me/my-views/view/all/");
    }
    @Test
    public void testManageJenkinsExists () {
        boolean isElementPresent = !getDriver().findElements(By.xpath("//span[contains(text(), 'Manage Jenkins')]")).isEmpty();
        Assert.assertTrue(isElementPresent);
    }
    @Test
    public void testManageJenkinsEnabled () {
        String urlBeforeClick = getDriver().getCurrentUrl();
        getDriver().findElement(By.xpath("//a[@href ='/manage']")).click();
        Assert.assertEquals(getDriver().getCurrentUrl(),urlBeforeClick+"manage/");
    }
}
