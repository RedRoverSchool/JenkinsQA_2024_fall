package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.FolderProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class UpdatesBuildHistoryTest extends BaseTest {
    private final String PROJECT_NAME = "TestName";

    @Test
    public void testBuildHistoryIsEmpty() {
        new HomePage(getDriver()).createFreestyleProject(PROJECT_NAME);
        new HomePage(getDriver()).openBuildHistoryPage();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//td/a/span"));
        List<String> historyList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(historyList.size(),0);
    }

    @Test(dependsOnMethods = "testBuildHistoryIsEmpty")
    public void testUpdateAfterExecutingBuild() {
        new HomePage(getDriver())
                .scheduleBuild(PROJECT_NAME)
                .openBuildHistoryPage();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//*[@id='projectStatus']/tbody/tr/td[4]"));
        List<String> historyList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(historyList.get(0), "stable");
    }

    @Test(dependsOnMethods = "testUpdateAfterExecutingBuild")
    public void testUpdateAfterChangingConfig() {
        new HomePage(getDriver()).selectConfigureFromItemMenu(PROJECT_NAME);

        TestUtils.scrollToBottom(getDriver());
        getDriver().findElement(By.xpath("//button[normalize-space()='Add build step']")).click();
        getWait2().until(ExpectedConditions.
                elementToBeClickable(By.xpath("//button[normalize-space()='Run with timeout']"))).click();
        getDriver().findElement(By.xpath("//button[contains(@name,'Submit')]")).click();

        new FolderProjectPage(getDriver())
                .gotoHomePage()
                .scheduleBuild(PROJECT_NAME)
                .openBuildHistoryPage();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//*[@id='projectStatus']/tbody/tr/td[4]"));
        List<String> historyList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(historyList.get(0), "broken since this build");
        Assert.assertEquals(historyList.size(),2);
    }
}
