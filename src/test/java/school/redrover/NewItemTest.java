package school.redrover;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;

import java.util.List;

@Epic("00 New Item")
public class NewItemTest extends BaseTest {

    private final static String MESSAGE = "» This field cannot be empty, please enter a valid name";
    private final static String NEW_ITEM_NAME = "New Project";

    @Test
    @Story("US_00.000 Create New item")
    @Description("TC_00.000.07 Verify the number of available item types")
    public void testCountItemTypes() {
        Integer sizeItemsTypesList = new HomePage(getDriver())
                .clickNewItem()
                .getTextList().size();

        Allure.step(String.format("Expected Result: The number of available item types is %d.", 6));
        Assert.assertEquals(sizeItemsTypesList, 6);
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description("TC_00.000.08 Verify the names of available item types")
    public void testItemTypesNames() {
        List<String> itemTypes = new HomePage(getDriver())
                .clickNewItem()
                .getItemList();

        Allure.step("Expected Result: All the names of available item types are present and displayed correctly");
        Assert.assertEquals(itemTypes, List.of("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder"));
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description("TC_00.000.09 Warning message is displayed when no item name is inserted and user clicks anywhere on the page")
    public void testWarningMessageWhenClickingAnywhereOnPageWithNoItemNameInserted() {
        String warningMessage = new HomePage(getDriver())
                .clickNewItem()
                .clickSomewhere()
                .getWarningMessageText();

        Allure.step(String.format("Expected Result: Warning message '%s' is displayed under item name input field", MESSAGE));
        Assert.assertEquals(warningMessage, MESSAGE);
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description("TC_00.000.10 'Ok' button is disabled when no item name is inserted")
    public void testOKButtonDisabledWhenNoItemNameInserted() {
        boolean okButton = new HomePage(getDriver())
                .clickNewItem()
                .getOkButton();

        Allure.step("Expected Result: 'Ok' button is disabled");
        Assert.assertFalse(okButton);
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description("TC_00.000.11 'Ok' button is disabled when no item type is selected")
    public void testOKButtonDisabledWhenNoItemTypeSelected() {
        boolean okButton = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(NEW_ITEM_NAME)
                .getOkButton();

        Allure.step("Expected Result: 'Ok' button is disabled");
        Assert.assertFalse(okButton);
    }

    @Test
    @Story("US_00.000 Create New item")
    @Description("TC_00.000.12 Warning message is displayed when creating project with empty name field")
    public void testCreateNewItemWithEmptyNameField() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .selectFreestyleProject()
                .selectPipeline()
                .selectFolderType()
                .getErrorMessage();

        Allure.step(String.format("Expected Result: Warning message '%s' is displayed when creating project with empty name field", MESSAGE));
        Assert.assertEquals(errorMessage, MESSAGE);
    }
}
