package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.CreateNewItemPage;
import school.redrover.page.HomePage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewItemTest extends BaseTest {

    private final static By NEW_ITEM_BUTTON = By.xpath("//a[@href='/view/all/newJob']");
    private final static By OK_BUTTON = By.id("ok-button");
    private final static By MESSAGE = By.id("itemname-required");
    private final static String NEW_ITEM_NAME = "New Project";
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

    private List<String> getTextList(List<WebElement> listOfElements) {

        List<String> textList = new ArrayList<>(listOfElements.size());

        for (WebElement element : listOfElements) {
            textList.add(element.getText());
        }

        return textList;
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

    @Test
    public void testPossibilityOfCreatingNewItemFromBreadcrumbBar() {
        WebElement breadcrumbBar = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a"));

        new Actions(getDriver()).moveToElement(breadcrumbBar).perform();

        WebElement breadCrumbChevron = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='breadcrumbBar']//a/button")));

        TestUtils.moveAndClickWithJavaScript(getDriver(), breadCrumbChevron);

        List<WebElement> breadCrumbItemsList = getDriver().findElements(By.xpath("//div[@class='tippy-box']//a"));

        Assert.assertTrue(getTextList(breadCrumbItemsList).contains("New Item"));
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
        getDriver().findElement(NEW_ITEM_BUTTON).click();

        final By randomClickPoint = By.id("add-item-panel");

        getDriver().findElement(randomClickPoint).click();

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
        getDriver().findElement(By.id("name")).sendKeys(NEW_ITEM_NAME);

        Assert.assertFalse(getDriver().findElement(OK_BUTTON).isEnabled());
    }
}
