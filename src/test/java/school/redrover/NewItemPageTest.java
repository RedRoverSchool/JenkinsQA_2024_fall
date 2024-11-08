package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewItemPageTest extends BaseTest {

    private final static By NEW_ITEM_BUTTON = By.xpath("//a[@href='/view/all/newJob']");
    private final static By PIPELINE_BUTTON = By.xpath("//span[text()='Pipeline']");
    private final static By OK_BUTTON = By.id("ok-button");
    private final static By MESSAGE = By.id("itemname-required");
    final String newItemName = "New Project";

    private List<String> getItemTypesList() {

        List<WebElement> newItems = getDriver().findElements(
                By.xpath("//div[@id='items']//li//label/span"));

        List<String> itemTypesNames = new ArrayList<>(newItems.size());

        for (WebElement item : newItems) {
            itemTypesNames.add(item.getText());
        }

        return itemTypesNames;
    }

    @Test
    public void testCountItemTypes() {

        getDriver().findElement(NEW_ITEM_BUTTON).click();

        Assert.assertEquals(getItemTypesList().size(), 6);
    }

    @Test
    public void testItemTypesNames() {

        getDriver().findElement(NEW_ITEM_BUTTON).click();

        List<String> expectedItemTypes = Arrays.asList("Freestyle project", "Pipeline", "Multi-configuration project",
                "Folder", "Multibranch Pipeline", "Organization Folder");

        List<String> actualItemTypes = getItemTypesList();

        for (int i = 0; i < getItemTypesList().size(); i++) {
            Assert.assertEquals(actualItemTypes.get(i), (expectedItemTypes.get(i)));
        }
    }

    @Test
    public void testWarningMessageWhenClickingAnywhereOnPageWithNoItemNameInserted() {

        getDriver().findElement(NEW_ITEM_BUTTON).click();

        final By randomClickPoint = By.id("add-item-panel");
        getDriver().findElement(randomClickPoint).click();

        String warningMessage = getDriver().findElement(MESSAGE).getText();

        Assert.assertEquals(warningMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testWarningMessageWhenSelectingItemTypeWithNoItemName() {

        getDriver().findElement(NEW_ITEM_BUTTON).click();
        getDriver().findElement(PIPELINE_BUTTON).click();
        String warningMessage = getDriver().findElement(MESSAGE).getText();

        Assert.assertEquals(warningMessage, "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testOKButtonDisabledWhenNoItemNameInserted() {

        getDriver().findElement(NEW_ITEM_BUTTON).click();

        Assert.assertFalse(getDriver().findElement(OK_BUTTON).isEnabled());
    }

    @Test
    public void testOKButtonDisabledWhenNoItemTypeSelected() {

        getDriver().findElement(NEW_ITEM_BUTTON).click();
        getDriver().findElement(By.id("name")).sendKeys(newItemName);

        Assert.assertFalse(getDriver().findElement(OK_BUTTON).isEnabled());
    }

    @Test
    public void testOKButtonEnabledWhenItemNameInsertedAndItemTypeSelected() {

        getDriver().findElement(NEW_ITEM_BUTTON).click();
        getDriver().findElement(By.id("name")).sendKeys(newItemName);
        getDriver().findElement(PIPELINE_BUTTON).click();

        Assert.assertTrue(getDriver().findElement(OK_BUTTON).isEnabled());
    }
}
