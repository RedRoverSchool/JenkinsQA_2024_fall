package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

public class NewItemTest extends BaseTest {

    private final static String MESSAGE = "» This field cannot be empty, please enter a valid name";
    private final static String NEW_ITEM_NAME = "New Project";
    private final static String NEW_ITEM = "New Item";

    @Test
    public void testPossibilityOfCreatingNewItemFromBreadcrumbBar() {
        String newItemButton = new HomePage(getDriver())
                .selectBreadcrumbBarMenu()
                .getBreadcrumbBarMenuList().get(0).getText();

        Assert.assertEquals(newItemButton, NEW_ITEM);
    }

    @Test
    public void testCountItemTypes() {

        Integer sizeItemsTypesList = new HomePage(getDriver())
                .clickNewItem()
                .getTextList().size();

        Assert.assertEquals(sizeItemsTypesList, 6);
    }

    @Test
    public void testItemTypesNames() {

        List<String> itemTypes = new HomePage(getDriver())
                .clickNewItem()
                .getItemList();

        Assert.assertEquals(itemTypes, List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder"));
    }

    @Test
    public void testWarningMessageWhenClickingAnywhereOnPageWithNoItemNameInserted() {
        String warningMessage = new HomePage(getDriver())
                .clickNewItem()
                .clickSomewhere()
                .getWarningMessageText();

        Assert.assertEquals(warningMessage, MESSAGE);
    }

    @Test
    public void testOKButtonDisabledWhenNoItemNameInserted() {
        boolean okButton = new HomePage(getDriver())
                .clickNewItem()
                .getOkButton();

        Assert.assertFalse(okButton);
    }

    @Test
    public void testOKButtonDisabledWhenNoItemTypeSelected() {
        boolean okButton = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(NEW_ITEM_NAME)
                .getOkButton();

        Assert.assertFalse(okButton);
    }

    @Test
    public void testCreateNewItemWithEmptyNameField() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectFreestyleProject()
                .selectPipeline()
                .selectMultibranchPipelineProject()
                .selectFolderType()
                .selectMultibranchPipelineProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage,MESSAGE);
    }
}
