package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class CreateNewItemTest extends BaseTest {

    private static final String ITEM_NAME = "CreateNewItem";
    private static final String PIPELINE = "Pipeline";
    private static final String PROJECT_NAME = "MyFreestyleProject";


    @Test
    public void testCreateFromMyViews() {
        List<String> projectName = new HomePage(getDriver())
                .clickMyViewsButton()
                .clickNewItem()
                .enterItemName(PROJECT_NAME)
                .selectFreestyleProjectAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(projectName.size(), 1);
        Assert.assertEquals(projectName.get(0), PROJECT_NAME);
    }
    @Test
    public void testWithButton() {
        List<String> items = new HomePage(getDriver())
                .clickNewItemContentBlock()
                .enterItemName(ITEM_NAME)
                .selectFreestyleProjectAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(items.size(), 1);
        Assert.assertEquals(items.get(0), ITEM_NAME);
    }

    @Test
    public void testWithLinkInSidebar() {
        List<String> items = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(items.size(), 1);
        Assert.assertEquals(items.get(0), PIPELINE);
    }

    @Test(dependsOnMethods = "testWithLinkInSidebar")
    public void testCheckUniqueItemName() {
        String error = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(PIPELINE)
                .selectFreestyleProject()
                .getInvalidNameMessage();

        Assert.assertEquals(error, "» A job already exists with the name ‘%s’".formatted(PIPELINE));
    }

    @Test
    public void testCheckInvalidName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("<{]_  -&")
                .selectFreestyleProject()
                .getErrorMessage();

        Assert.assertTrue(errorMessage.contains("is an unsafe character"));
    }
}
