package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class FreestyleProject4Test extends BaseTest {

    private static final String PROJECT_NAME = "NewFreestyleProject";
    private static final String PROJECT_DESCRIPTION = "About my new freestyle project";
    private static final String TYPE_FREESTYLE_PROJECT = "Freestyle project";

    @Test
    public void testCheckProjectName() {

        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectTypeProject(TYPE_FREESTYLE_PROJECT)
                .clickOkButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertTrue(itemList.contains(PROJECT_NAME));
    }

    @Test (dependsOnMethods = "testCheckProjectName")
    public void testAddDescription() {

        String textDescription = new HomePage(getDriver())
                .openFreestyleProject(PROJECT_NAME)
                .editDescription(PROJECT_DESCRIPTION)
                .getDescription();

        Assert.assertEquals(textDescription, PROJECT_DESCRIPTION);
    }
}



