package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FreestyleProject9Test extends BaseTest {

    private static final String FREESTYLE_NAME = "FREESTYLE_NAME";

    @Test
    public void test() {
        createFreestyleProject(FREESTYLE_NAME);
    }

    @Test
    public void testSortFreestyleProjects() {
        final List<String> nameOfFreestyleJob = List.of("8", "0", "3");
        final List<String> expectedNameOfFreestyleJobSort = new ArrayList<>(nameOfFreestyleJob);
        expectedNameOfFreestyleJobSort.sort(Comparator.naturalOrder());
        System.out.println(expectedNameOfFreestyleJobSort);

        nameOfFreestyleJob.forEach(jobName -> createFreestyleProject(jobName));

        List<String> actualJobsNames = getDriver()
                .findElements(By.xpath("//*[@class='jenkins-table  sortable']/tbody/tr/td/a"))
                .stream()
                .map(WebElement::getText).toList();
        System.out.println(actualJobsNames);

        Assert.assertEquals(actualJobsNames, expectedNameOfFreestyleJobSort);

    }

    private void createFreestyleProject(String jobName) {

        WebElement newItem = getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']"));
        newItem.click();

        getDriver().findElement(By.className("jenkins-input")).sendKeys(jobName);
        WebElement freeStyleProject = getDriver().findElement(By.className("hudson_model_FreeStyleProject"));
        freeStyleProject.click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.cssSelector("[name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }
}
