package school.redrover;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import school.redrover.runner.BaseTest;

public class WorkspaceTest extends BaseTest {

    @Ignore
    @BeforeMethod
    public void testCreateWorkspace() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();

        WebElement jobNameField = getDriver().findElement(By.xpath("//*[@id='name']"));
        jobNameField.sendKeys("TestJobWorkspace");

        WebElement freestyleProjectOption = getDriver().findElement(
                By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]"));
        freestyleProjectOption.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(
                By.xpath("//*[@id='bottom-sticker']/div/button[1]"));
        saveButton.click();

        WebElement build1 = getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a"));
        build1.click();
        Thread.sleep(3000);

        WebElement build2 = getDriver().findElement(By.xpath("//*[@id='tasks']/div[4]/span/a"));
        build2.click();
        Thread.sleep(3000);

        WebElement workspace = getDriver().findElement(By.xpath("//*[@id='tasks']/div[3]/span/a"));
        workspace.click();

        WebElement workspaceTitle = getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]"));
        assert (workspaceTitle.isDisplayed());

        System.out.println("Workspace of TestJobWorkspace on Built-In Node");

    }

    @Ignore
    @Test
    public void testWorkspaceViewable() {

        List<WebElement> directories = getDriver().findElements(By.cssSelector("#breadcrumbs > li:nth-child(5)"));
        List<WebElement> files = getDriver().findElements(
                By.cssSelector("#tasks > div:nth-child(3) > span > a > span.task-link-text"));

        Assert.assertTrue(directories.size() > 0, "Directories are displayed in the workspace.");
        Assert.assertTrue(files.size() > 0, "Files are displayed in the workspace.");
    }

    @Ignore
    @Test
    public void testBuildNavigation() {

        WebElement buildTwo = getDriver().findElement(By.cssSelector("#buildHistory tr:nth-child(2) a"));
        buildTwo.click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#breadcrumbs > li:nth-child(5)")).getText(), "#2");
    }

    @Ignore
    @Test
    public void testWorkspaceNavigation() {

        WebElement buildTwo = getDriver().findElement(By.cssSelector("#buildHistory tr:nth-child(2) a"));
        buildTwo.click();
        WebElement breadCrumbs = getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[3]/a"));
        breadCrumbs.click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@id='breadcrumbs']/li[3]/a")).getText(), "TestJobWorkspace");
    }

    @Ignore
    @AfterMethod
    public void tearDown() {

        getDriver().quit();
    }
}