package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.NewViewPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ViewTest extends BaseTest {

    private static final String PIPELINE_NAME = "Pipeline_name";
    private static final String VIEW_NAME = "View_name";
    private static final By OK_BUTTON = By.xpath("//button[@name='Submit']");

    private void createListViewForJob(String viewName, String jobName) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='New View']"))).click();

        WebElement inputNameField = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));
        inputNameField.sendKeys(viewName);

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='hudson.model.ListView']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@title='%s']".formatted(jobName)))).click();
        getDriver().findElement(OK_BUTTON).click();
    }

    private List<String> getColumnList() {

        return getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//thead//th")))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    private void clickViewByName(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view/%s/']".formatted(name)))).click();
    }

    @Test
    public void testCreateListViewForSpecificJob() {
        TestUtils.createPipeline(this, PIPELINE_NAME);
        createListViewForJob(VIEW_NAME, PIPELINE_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//td/a[@href='job/%s/']".formatted(PIPELINE_NAME))))).click();

        Assert.assertEquals(getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//li[@class='jenkins-breadcrumbs__list-item'][2]"))).getText(),
                VIEW_NAME);
    }

    @Test
    public void testVerifyDescriptionsForViewTypes() {
        final Map<String, String> expectedDescriptions = Map.of(
                "List View",
                "Shows items in a simple list format. You can choose which jobs are to be displayed in which view.",
                "My View",
                "This view automatically displays all the jobs that the current user has an access to."
        );

        TestUtils.createPipeline(this, PIPELINE_NAME);

        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='New View']"))).click();

        List<WebElement> typeOptionList = getWait2().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='jenkins-radio']"))
        );

        Map<String, String> typeNameToDescriptionMap = new HashMap<>();
        for (WebElement option : typeOptionList) {
            String optionText = option.findElement(By.xpath("./label")).getText();
            String descriptionText = option.findElement(By.xpath("./div")).getText();

            typeNameToDescriptionMap.put(optionText, descriptionText);
        }

        expectedDescriptions.forEach((expectedType, expectedDescription) -> {
            String actualDescription = typeNameToDescriptionMap.get(expectedType);

            Assert.assertNotNull(actualDescription,
                    "Description is missing for type: " + expectedType);
            Assert.assertEquals(actualDescription, expectedDescription,
                    "Description is not matching for type: " + expectedType);
        });
    }

    @Test
    public void testDeleteViewColumnForSpecificProjectByXButton() {
        final String columnName = "Weather";

        TestUtils.createPipeline(this, PIPELINE_NAME);
        createListViewForJob(VIEW_NAME, PIPELINE_NAME);
        clickViewByName(VIEW_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view/%s/configure']".formatted(VIEW_NAME)))).click();

        TestUtils.scrollToBottom(getDriver());

        WebElement columnOption = getDriver().findElements(By.xpath("//div[@class='repeated-chunk__header']"))
                .stream()
                .filter(column -> column.getText().trim().equals(columnName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(columnName + " is not listed in column options"));

        WebElement closeButton = columnOption.findElement(By.xpath(".//button"));
        closeButton.click();

        getWait10().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@class='repeated-chunk__header']"), 6));
        getWait2().until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();


        Assert.assertEquals(getColumnList().size(), 6);
        Assert.assertListNotContainsObject(getColumnList(), columnName, "Deleted column is not displayed");
    }

    @Test
    public void testAddColumn() {
        final String columnName = "Git Branches";

        TestUtils.createPipeline(this, PIPELINE_NAME);
        createListViewForJob(VIEW_NAME, PIPELINE_NAME);
        clickViewByName(VIEW_NAME);

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view/%s/configure']".formatted(VIEW_NAME)))).click();

        TestUtils.scrollToBottom(getDriver());

        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@suffix='columns']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'%s')]".formatted(columnName)))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();

        Assert.assertEquals(getColumnList().size(), 8);
        Assert.assertTrue(getColumnList().contains(columnName));
    }

    @Test
    public void testCreateNewViewForm() {
        NewViewPage newViewPage = new HomePage(getDriver())
                                .createNewFolder("NewFolder")
                                .gotoHomePage()
                                .clickCreateNewViewButton();

        Assert.assertTrue(newViewPage.getInputFromNameField().isEmpty(), "Input field should be empty.");
        Assert.assertFalse(newViewPage.isRadioButtonListViewSelected(), "ListView radio button should not be selected.");
        Assert.assertFalse(newViewPage.isRadioButtonMyViewSelected(), "MyView radio button should not be selected.");
        Assert.assertFalse(newViewPage.isCreateButtonEnabled(), "Create button should be disabled.");
    }
}