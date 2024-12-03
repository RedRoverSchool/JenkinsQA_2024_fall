package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NewItemFromOtherExistingTest extends BaseTest {

    private final static String FIRST_ITEM_NAME = "My_First_Project";
    private final static String SECOND_ITEM_NAME = "My_Second_Project";

    private HomePage createProject(String projectName, String projectType){
        return new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName)
                .selectTypeOfProject(projectType)
                .clickOkAndGoToConfigPage(projectType)
                .gotoHomePage();
    }

    @Test
    public void testCreatePipelineFromExistingOne() {
        List<String> itemNameList = createProject(FIRST_ITEM_NAME, "Pipeline")
                .clickNewItem()
                .enterItemName(SECOND_ITEM_NAME)
                .scrollToCopyFromFieldAndEnterName(FIRST_ITEM_NAME)
                .clickOkAndGoToConfigPage("Pipeline")
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(itemNameList.contains(SECOND_ITEM_NAME));
    }

    @Test
    public void testCreateFreestyleProjectFromExistingOne() {
        List<String> itemNameList = createProject(FIRST_ITEM_NAME, "Freestyle project")
                .clickNewItem()
                .enterItemName(SECOND_ITEM_NAME)
                .scrollToCopyFromFieldAndEnterName(FIRST_ITEM_NAME)
                .clickOkAndGoToConfigPage("Freestyle project")
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(itemNameList.contains(SECOND_ITEM_NAME));
    }
}





