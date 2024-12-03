package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class CreateNewItem1Test extends BaseTest {

    private static final String ITEM_NAME = "CreateNewItem";
    private static final String FREESTYLE_PROJECT = "Freestyle project";
    private static final String PIPELINE = "Pipeline";

    @Test
    public void testWithButton() {
        List<String> items = new HomePage(getDriver())
                .clickCreateJob()
                .enterItemName(ITEM_NAME)
                .selectTypeOfProject(FREESTYLE_PROJECT)
                .clickOkButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(items.size(), 1);
        Assert.assertEquals(items.get(0), ITEM_NAME);
    }

    @Test
    public void testWithLinkInSidebar() {
        List<String> items = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ITEM_NAME)
                .selectTypeProject(PIPELINE)
                .clickOkButton()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(items.size(), 1);
        Assert.assertEquals(items.get(0), ITEM_NAME);
    }

    @Ignore
    @Test(dependsOnMethods = "testWithLinkInSidebar")
    public void testCheckUniqueItemName() {
        String error = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ITEM_NAME)
                .selectFreestyleProject()
                .getInvalidNameMessage();

        Assert.assertEquals(error, "» A job already exists with the name ‘%s’".formatted(ITEM_NAME));
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
