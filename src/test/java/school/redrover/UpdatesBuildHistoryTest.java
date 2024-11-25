package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.ProjectPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class UpdatesBuildHistoryTest extends BaseTest {

    public void openBuildHistoryPage() {
        getDriver().findElement(By.xpath("//a[@href = '/view/all/builds']")).click();
    }

    public void scheduleBuild() {
        getDriver().findElement(By.xpath("//td[@class = 'jenkins-table__cell--tight']")).click();
    }

    @Test
    public void testBuildHistoryIsEmpty() {
        final String projectName = "TestName";

        new HomePage(getDriver())
                .createFreestyleProject(projectName);

        openBuildHistoryPage();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//td/a/span"));
        List<String> historyList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(historyList.size(),0);
    }

    @Test(dependsOnMethods = "testBuildHistoryIsEmpty")
    public void testUpdateAfterExecutingBuild() {
        scheduleBuild();
        openBuildHistoryPage();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//*[@id='projectStatus']/tbody/tr/td[4]"));
        List<String> historyList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(historyList.get(0), "stable");
    }

    @Test(dependsOnMethods = "testUpdateAfterExecutingBuild")
    public void testUpdateAfterChangingConfig() {
         new HomePage(getDriver())
                .openDropdownViaChevron("TestName");

        getDriver().findElement(By.xpath("//a[normalize-space()='Configure']")).click();
        TestUtils.scrollToBottom(getDriver());
        getDriver().findElement(By.xpath("//button[normalize-space()='Add build step']")).click();
        getWait2().until(ExpectedConditions.
                elementToBeClickable(By.xpath("//button[normalize-space()='Run with timeout']"))).click();
        getDriver().findElement(By.xpath("//button[contains(@name,'Submit')]")).click();

        new ProjectPage(getDriver())
                .goToDashboard();

        scheduleBuild();

        new ProjectPage(getDriver())
                .goToDashboard();

        openBuildHistoryPage();

        List<WebElement> elementList = getDriver().findElements(By.xpath("//*[@id='projectStatus']/tbody/tr/td[4]"));
        List<String> historyList = elementList.stream().map(WebElement::getText).toList();

        Assert.assertEquals(historyList.get(0), "broken since this build");
        Assert.assertEquals(historyList.size(),2);
    }
}
