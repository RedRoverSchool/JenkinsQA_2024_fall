package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingJobsTest extends BaseTest {
    private void createJobAndReturnToHomePage(String projectName) {
        getDriver().findElement(By.cssSelector("[href$='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void createFailedJobAndReturnToHomePage(String projectName) {
        getDriver().findElement(By.cssSelector("[href$='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Actions act = new Actions(getDriver());
        WebElement postBuildActionsTitle = getDriver().findElement(By.id("build-steps"));
        act.scrollToElement(postBuildActionsTitle).perform();

        getDriver().findElement(By.xpath("//label[@for='radio-block-1']")).click();

        getDriver().findElement(By.xpath("//input[@checkdependson='credentialsId']")).sendKeys("invalidUrl");

        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    @Test
    public void testDefaultSortJobsOnHomePage() {
        final List<String> jobsNames = List.of("ProjectC", "ProjectA", "ProjectB");
        final List<String> expectedJobsNamesSortedAsc = new ArrayList<>(jobsNames);
        expectedJobsNamesSortedAsc.sort(Comparator.naturalOrder());

        jobsNames.forEach(jobName -> createJobAndReturnToHomePage(jobName));

        List<String> actualJobsNames = getDriver()
                                                 .findElements(By.xpath("//tbody/tr/td/a"))
                                                 .stream()
                                                 .map(WebElement::getText).toList();

        Assert.assertEquals(actualJobsNames, expectedJobsNamesSortedAsc);
    }

    @Test
    public void testDescendingSortJobsOnHomePage() {
        final List<String> jobsNames = List.of("ProjectA", "ProjectB", "ProjectC");
        final List<String> expectedJobsNamesSortedDesc = jobsNames.stream().sorted(Comparator.reverseOrder()).toList();

        jobsNames.forEach(jobName -> createJobAndReturnToHomePage(jobName));

        getDriver().findElement(By.xpath("//th[@initialsortdir='down']//a[@class='sortheader']")).click();

        List<String> actualJobsNames = getDriver().findElements(By.xpath("//tbody/tr/td/a"))
                                           .stream()
                                           .map(WebElement::getText).toList();

        Assert.assertEquals(actualJobsNames, expectedJobsNamesSortedDesc);
    }

    @Test
    public void testStatusSortOnHomePage() {

        createJobAndReturnToHomePage("ProjectC");

        createFailedJobAndReturnToHomePage("ProjectA");

        getDriver().findElement(By.xpath("//td[@class='jenkins-table__cell--tight']//a[@title='Schedule a Build for ProjectA']")).click();

        getDriver().findElement(By.xpath("//td[@class='jenkins-table__cell--tight']//a[@title='Schedule a Build for ProjectC']")).click();

        getDriver().navigate().refresh();
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("tr[class^=' job-status']")));

        getDriver().findElement(By.xpath("//th[@tooltip='Status of the last build']//a[@class='sortheader']")).click();

        List<WebElement> jobsStatuses = getDriver().findElements(By.cssSelector("div[class='jenkins-table__cell__button-wrapper'] svg[tooltip]"));
        List<String> actualSortedStatuses = jobsStatuses.stream().map(el-> el.getAttribute("title")).toList();

        Assert.assertEquals(actualSortedStatuses, List.of("Success", "Failed"));
    }
}