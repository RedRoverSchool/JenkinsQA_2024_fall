package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.CreateNewItemPage;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NewItemFromOtherExistingTest extends BaseTest {

    private final static String FIRST_ITEM_NAME = "My_First_Project";
    private final static String SECOND_ITEM_NAME = "My_Second_Project";

    private CreateNewItemPage goToNewItemPageAndEnterName(String projectName) {
        return new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(projectName);
    }

    private HomePage createPipelineProject(String projectName) {
        return goToNewItemPageAndEnterName(projectName)
                .selectPipelineAndClickOk()
                .gotoHomePage();
    }

    private HomePage createFreestyleProject(String projectName) {
        return goToNewItemPageAndEnterName(projectName)
                .selectFreestyleProjectAndClickOk()
                .gotoHomePage();
    }

    @Test
    public void testCreatePipelineFromExistingOne () {
        List<String> itemNameList = createPipelineProject(FIRST_ITEM_NAME)
                .clickNewItem()
                .enterItemName(SECOND_ITEM_NAME)
                .scrollToCopyFromFieldAndEnterName(FIRST_ITEM_NAME)
                .clickOkAndGoToPipelineConfigPage()
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(itemNameList.contains(SECOND_ITEM_NAME));
    }

    @Test
    public void testCreateFreestyleProjectFromExistingOne () {
        List<String> itemNameList = createFreestyleProject(FIRST_ITEM_NAME)
                .clickNewItem()
                .enterItemName(SECOND_ITEM_NAME)
                .scrollToCopyFromFieldAndEnterName(FIRST_ITEM_NAME)
                .clickOkButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(itemNameList.contains(SECOND_ITEM_NAME));
    }
}







