package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ViewTest extends BaseTest {

    private static final String PIPELINE_NAME = "Pipeline_name";
    private static final String VIEW_NAME = "View_name";

    private void createListViewForJob(String viewName, String jobName) {
        getDriver().findElement(By.id("jenkins-home-link")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='New View']"))).click();

        WebElement inputNameField = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));
        inputNameField.sendKeys(viewName);

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='hudson.model.ListView']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@title='%s']".formatted(jobName)))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
    }

    private void returnToHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
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
        returnToHomePage();

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
    public void testDeleteViewColumnForSpecificProject() {
        final String columnName = "Weather";

        TestUtils.createPipeline(this, PIPELINE_NAME);
        createListViewForJob(VIEW_NAME, PIPELINE_NAME);
        returnToHomePage();

        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view/%s/']".formatted(VIEW_NAME)))).click();
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
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();

        List<String> columnList = getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//thead//th")))
                .stream()
                .map(WebElement::getText)
                .toList();

        Assert.assertEquals(columnList.size(), 6);
        Assert.assertListNotContainsObject(columnList, columnName, "Deleted column is not displayed");
    }
}
