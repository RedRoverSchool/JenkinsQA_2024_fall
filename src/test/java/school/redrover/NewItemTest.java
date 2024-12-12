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

    private List<String> getTextList(List<WebElement> listOfElements) {

        List<String> textList = new ArrayList<>(listOfElements.size());

        for (WebElement element : listOfElements) {
            textList.add(element.getText());
        }

        return textList;
    }

    @Test
    public void testPossibilityOfCreatingNewItemFromBreadcrumbBar() {
        WebElement breadcrumbBar = getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//a"));

        new Actions(getDriver()).moveToElement(breadcrumbBar).perform();

        WebElement breadCrumbChevron = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='breadcrumbBar']//a/button")));

        TestUtils.moveAndClickWithJS(getDriver(), breadCrumbChevron);

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
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        List<WebElement> items = getDriver().findElements(By.xpath("//div[@class='desc']"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 500);");

        String expectedMessage = "This field cannot be empty, please enter a valid name";

        for (WebElement item : items) {
            item.click();

            WebElement errorMessage = getDriver().findElement(By.xpath("//div[@id='itemname-required']"));
            String actualErrorMessage = errorMessage.getText();

            WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
            webDriverWait.until(ExpectedConditions.elementToBeClickable(item));

            Assert.assertTrue(actualErrorMessage.contains(expectedMessage), "This field cannot be empty, please enter a valid name");
        }
    }
}
