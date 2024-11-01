package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class BuildExecutorStatusTest extends BaseTest {

    @Test
    public void testBuildExecutorStatusPanel (){
        WebElement buildExecutorStatusPanel = getDriver().findElement(By.id("executors"));
        Assert.assertTrue(buildExecutorStatusPanel.isDisplayed(), "Panel 'executors' is not displayed on the page.");
    }

    @Test
    public void testBuildExecutorStatusClick (){
        WebElement buildExecutorStatusClick = getDriver().findElement(By.xpath("//*[@id='executors']/div[1]/span/a"));
        buildExecutorStatusClick.click();

        String NodesTitle = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1")).getText();
        Assert.assertEquals(NodesTitle, "Nodes");
    }

}
