package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class DeleteFreestyleProjectTest extends BaseTest {

    private final String FIRST_PROJECT = "First";
    private final String SECOND_PROJECT = "Second";

    @Test
    public void testDeleteFirstProjectViaChevron() {
        List<String> projectsList = new HomePage(getDriver())
                .createFreestyleProject(FIRST_PROJECT)
                .createFreestyleProject(SECOND_PROJECT)
                .selectDeleteFromItemMenu(FIRST_PROJECT)
                .clickYesForConfirmDelete()
                .getItemList();

        Assert.assertEquals(projectsList.size(),1);
        Assert.assertEquals(projectsList.get(0), SECOND_PROJECT);
    }

    @Test(dependsOnMethods = "testDeleteFirstProjectViaChevron")
    public void testDeleteAllProjectsViaChevron() {
        List<String> projectsList = new HomePage(getDriver())
                .selectDeleteFromItemMenu(SECOND_PROJECT)
                .clickYesForConfirmDelete()
                .getItemList();

        Assert.assertEquals(projectsList.size(), 0);
        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(),"Welcome to Jenkins!");
    }

}
