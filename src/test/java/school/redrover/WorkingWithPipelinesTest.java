package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class WorkingWithPipelinesTest extends BaseTest {

    private static final String PIPELINE_NAME = "Regression";
    private static final String PIPELINE_RENAME = "NewRegression";

    @Test
    public void testRenameViaChevron() {

        List<String> ListOfProjects = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .renameViaChevron(PIPELINE_NAME,PIPELINE_RENAME)
                .gotoHomePage()
                .getItemList();

        Assert.assertListContainsObject(ListOfProjects,
                PIPELINE_RENAME, "Пайплайн не найден на главной странице или текст не совпадает");
    }

    @Test(dependsOnMethods = "testRenameViaChevron")
    public void testDeleteViaChevron() {

        List<String> ListOfProjects = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .deleteItemViaChevron(PIPELINE_RENAME)
                .getItemList();

        Assert.assertListNotContainsObject(ListOfProjects, PIPELINE_RENAME,
                "Project небыл удален");
    }
}