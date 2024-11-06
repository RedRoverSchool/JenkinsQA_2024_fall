package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ManageJenkinsTest extends BaseTest {

    @Ignore
    @Test
    public void testManageJenkinsTab() {

        List<WebElement> tasks = getDriver().findElements(
                By.xpath("//div[@id='tasks']//a"));
        Assert.assertEquals(tasks.size(), 1);

        List<String> expectedTexts = Arrays.asList("New Item", "Build History", "Manage Jenkins", "My Views");
        for (int i = 0; i < tasks.size() && i < expectedTexts.size(); i++) {
            Assert.assertEquals(tasks.get(i).getText(), expectedTexts.get(i));
        }

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement manageJenkinsTask = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Manage Jenkins']]")));
        manageJenkinsTask.click();

    }

    @Test
    public void testManageJenkinsTabSections() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement manageJenkinsTab = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Manage Jenkins']]"))
        );
        manageJenkinsTab.click();

        List<WebElement> sections = getDriver().findElements(
                By.xpath("//h2[@class='jenkins-section__title']"));
        Assert.assertEquals(sections.size(), 5);

        List<String> expectedSectionTitles = Arrays.asList(
                "System Configuration", "Security", "Status Information", "Troubleshooting", "Tools and Actions"
        );
        for (int i = 0; i < expectedSectionTitles.size(); i++) {
            Assert.assertEquals(sections.get(i).getText(), expectedSectionTitles.get(i));

        }
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

    @Test
    public void testManageJenkinsTabSearch() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement manageJenkinsTab = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Manage Jenkins']]"))
        );
        manageJenkinsTab.click();

        WebElement searchField = getDriver().findElement(
                By.xpath("//input[@id='settings-search-bar']"));
        Assert.assertEquals(searchField.getAttribute("placeholder"), "Search settings");

    }
}
