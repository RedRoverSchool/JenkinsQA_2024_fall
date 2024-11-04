package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    @Test
    public void testManageJenkinsTab() throws InterruptedException {

        List<WebElement> tasks = getDriver().findElements(
                By.xpath("//div[@id='tasks']//a"));
        Assert.assertEquals(tasks.size(), 4);

        Assert.assertEquals(tasks.get(0).getText(), "New Item");
        Assert.assertEquals(tasks.get(1).getText(), "Build History");
        Assert.assertEquals(tasks.get(2).getText(), "Manage Jenkins");
        Assert.assertEquals(tasks.get(3).getText(), "My Views");

        Thread.sleep(2000);

        WebElement manageJenkinsTask = getDriver().findElement(
                By.xpath("//a[span[text()='Manage Jenkins']]"));
        manageJenkinsTask.click();
    }

    @Test
    public void testManageJenkinsTabSections() throws InterruptedException {

        WebElement manageJenkinsTab = getDriver().findElement(
                By.xpath("//a[span[text()='Manage Jenkins']]"));
        manageJenkinsTab.click();

        Thread.sleep(1000);

        List<WebElement> sections = getDriver().findElements(
                By.xpath("//h2[@class='jenkins-section__title']"));
        Assert.assertEquals(sections.size(), 5);

        Assert.assertEquals(sections.get(0).getText(), "System Configuration");
        Assert.assertEquals(sections.get(1).getText(), "Security");
        Assert.assertEquals(sections.get(2). getText(), "Status Information");
        Assert.assertEquals(sections.get(3).getText(), "Troubleshooting");
        Assert.assertEquals(sections.get(4).getText(), "Tools and Actions");

    }

    @Test
    public void testManageJenkinsSections(){
        final List <String> expectedSectionsTitles=List.of("System Configuration", "Security", "Status Information",
            "Troubleshooting", "Tools and Actions");

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        List<WebElement> sections = getDriver().findElements(By.xpath("//h2[@class='jenkins-section__title']"));
        List<String> actualSectionsTitles = sections.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualSectionsTitles, expectedSectionsTitles);
    }

    @Test
    public void testManageJenkinsSystemConfigurationItems(){
        final List <String> expectedItemsNames=List.of("System", "Tools", "Plugins",
             "Nodes", "Clouds", "Appearance");

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        List<WebElement> systemConfigItems = getDriver()
                                                 .findElements(By.xpath("(//div[@class='jenkins-section__items'])[1]//dt"));
        List<String> actualItemsNames = systemConfigItems.stream().map(WebElement::getText).toList();

        Assert.assertEquals(actualItemsNames, expectedItemsNames);
    }

    @Test
    public void testManageJenkinsSystemConfigureBreadcrumbs(){
        final String expectedBreadCrumbs = "Dashboard\nManage Jenkins\nSystem";

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();

        getDriver().findElement(By.cssSelector("a[href='configure']")).click();

        String actualBreadCrumbs = getDriver().findElement(By.id("breadcrumbs")).getText();

        Assert.assertEquals(actualBreadCrumbs, expectedBreadCrumbs);
    }
}
