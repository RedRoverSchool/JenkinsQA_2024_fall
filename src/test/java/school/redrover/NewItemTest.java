package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.home.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewItemTest extends BaseTest {

    private final static By NEW_ITEM_BUTTON = By.xpath("//a[@href='/view/all/newJob']");
    private final static String MESSAGE = "» This field cannot be empty, please enter a valid name";
    private final static String NEW_ITEM_NAME = "New Project";
    private final static String NEW_ITEM = "New Item";

    private List<String> getTextList(List<WebElement> listOfElements) {

        List<String> textList = new ArrayList<>(listOfElements.size());

        for (WebElement element : listOfElements) {
            textList.add(element.getText());
        }

        return textList;
    }

    @Test
    public void testPossibilityOfCreatingNewItemFromBreadcrumbBar() {
        String newItemButton = new HomePage(getDriver())
                .selectBreadcrumbBarMenu()
                .getBreadcrumbBarMenuList().get(0).getText();

        Assert.assertEquals(newItemButton, NEW_ITEM);
    }

    @Test
    public void testCountItemTypes() {
        getDriver().findElement(NEW_ITEM_BUTTON).click();

        List<WebElement> itemsTypesList = getDriver().findElements(By.xpath(   "//div[@id='items']//li//label/span"));

        Assert.assertEquals(getTextList(itemsTypesList).size(), 6);
    }

    @Test
    public void testItemTypesNames() {
        getDriver().findElement(NEW_ITEM_BUTTON).click();

        List<String> expectedItemTypes = Arrays.asList("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder");

        List<WebElement> itemsTypesList = getDriver().findElements(By.xpath(   "//div[@id='items']//li//label/span"));

        List<String> actualItemTypes = getTextList(itemsTypesList);

        for (int i = 0; i < getTextList(itemsTypesList).size(); i++) {
            Assert.assertEquals(actualItemTypes.get(i), (expectedItemTypes.get(i)));
        }
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
