package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingJobsTest extends BaseTest {
    private void createJobAndReturnToHomePage(String name) {
        getDriver().findElement(By.cssSelector("[href$='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
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
}