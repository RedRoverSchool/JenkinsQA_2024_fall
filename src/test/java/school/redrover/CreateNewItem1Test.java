package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;

public class CreateNewItem1Test extends BaseTest {

    private final String ITEM_NAME = "CreateNewItem";

    @Test
    public void testWithButton() {
        String name = new HomePage(getDriver())
                .createNewFolder(ITEM_NAME)
                .getItemName();

        Assert.assertEquals(name, ITEM_NAME);
    }

    @Test
    public void testWithLinkInSidebar() {
        HomePage hp = new HomePage(getDriver());
        hp.createFreestyleProject(ITEM_NAME);
        String name = hp.getItemName();

        Assert.assertEquals(name, ITEM_NAME);
    }

    @Test(dependsOnMethods = "testWithLinkInSidebar")
    public void testCheckUniqueItemName() {
        String error = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ITEM_NAME)
                .getErrorMessage();

        Assert.assertEquals(error, "» A job already exists with the name ‘%s’".formatted(ITEM_NAME));
    }

    @Test
    public void testCheckInvalidName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName("<{]_  -&").getErrorMessage();

        Assert.assertTrue(errorMessage.contains("is an unsafe character"));
    }
}
