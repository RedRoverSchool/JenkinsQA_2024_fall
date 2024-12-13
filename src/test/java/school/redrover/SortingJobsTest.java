package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.Comparator;
import java.util.List;

public class SortingJobsTest extends BaseTest {

    final private static List<String> PROJECT_NAMES = List.of("ProjectC", "ProjectA", "ProjectB");

    @Test
    public void testDefaultSortJobsOnHomePage() {
        PROJECT_NAMES.forEach(jobName -> TestUtils.createFreestyleProject(getDriver(), jobName));
        final List<String> expectedResult = PROJECT_NAMES.stream().sorted(Comparator.naturalOrder()).toList();

        List<String> actualJobsNames = new HomePage(getDriver())
                .getItemList();

        Assert.assertEquals(actualJobsNames, expectedResult);
    }

    @Test(dependsOnMethods = "testDefaultSortJobsOnHomePage")
    public void testDescendingSortJobsOnHomePage() {
        final List<String> expectedJobsNamesSortedDesc = PROJECT_NAMES.stream().sorted(Comparator.reverseOrder()).toList();

        List<String> actualJobsNames = new HomePage(getDriver())
                .clickSortByName()
                .getItemList();

        Assert.assertEquals(actualJobsNames, expectedJobsNamesSortedDesc);
    }

    @Test
    public void testStatusSortOnHomePage() {
        PROJECT_NAMES.forEach(jobName -> TestUtils.createFreestyleProject(getDriver(), jobName));

        List<String> actualJobsStatuses = new HomePage(getDriver())
                .openFreestyleProject("ProjectB")
                .clickSidebarConfigButton()
                .addBuildStep("Invoke Ant")
                .clickSaveButton()
                .gotoHomePage()

                .clickScheduleBuild("ProjectA")
                .clickScheduleBuild("ProjectB")
                .clickScheduleBuild("ProjectC")
                .refreshAfterBuild()
                .getSortedStatusesListBuild();

        Assert.assertEquals(actualJobsStatuses, List.of("Success","Success", "Failed"));
    }
}