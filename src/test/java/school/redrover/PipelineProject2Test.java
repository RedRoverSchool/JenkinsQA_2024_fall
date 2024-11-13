package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;


public class PipelineProject2Test extends BaseTest {

    private enum ItemTypes {
        PIPELINE("Pipeline"),
        FOLDER("Folder");

        private final String htmlTest;

        ItemTypes(String htmlTest) {
            this.htmlTest = htmlTest;
        }

        public String getHtmlTest() {
            return htmlTest;
        }
    }

    private static final String PROJECT_NAME = "MyPipelineProject";
    private static final String FOLDER_NAME = "MyFolder";

    private static final String ITEM_NAME_LOCATOR = "//span[text()='%s']";

    private void createItemAndGoToMainPage(ItemTypes itemType, String itemName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.xpath(ITEM_NAME_LOCATOR.formatted(itemType.getHtmlTest()))).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void goToMainPage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testCreateWithValidName() {
        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        String pipelineProjectName = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME)))).getText();

        Assert.assertEquals(pipelineProjectName, PROJECT_NAME);
    }

    @Test
    public void testCreateWithEmptyName() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath(ITEM_NAME_LOCATOR.formatted(ItemTypes.PIPELINE.getHtmlTest()))).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("itemname-required")).getText(),
                "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateWithDuplicateName() {
        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/view/all/newJob']"))).click();

        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath(ITEM_NAME_LOCATOR.formatted(ItemTypes.PIPELINE.getHtmlTest()))).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("itemname-invalid")).getText(),
                "» A job already exists with the name ‘%s’".formatted(PROJECT_NAME));
    }

    @Test
    public void testAddDescription() {
        String description = "Description pipeline project.";

        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME)))).click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver()
                .findElement(By.xpath("//textarea[@name='description']")).sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@id='description']/div")).getText(),
                description);
    }

    @Test
    public void testMoveProjectToFolder() {
        createItemAndGoToMainPage(ItemTypes.FOLDER, FOLDER_NAME);
        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        getDriver().findElement(By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME))).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'move')]")).click();

        new Select(getDriver().findElement(By.xpath("//select[@name='destination']")))
                .selectByValue("/" + FOLDER_NAME);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        goToMainPage();

        getDriver().findElement(By.xpath(ITEM_NAME_LOCATOR.formatted(FOLDER_NAME))).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME))).getText(), PROJECT_NAME);
    }

    @Test
    public void testDeleteViaSidePanel() {
        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME)))).click();

        getDriver().findElement(By.xpath("//a[contains(@data-url, 'doDelete')]")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertTrue(getDriver().findElements(By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME))).isEmpty());
    }

    @Test
    public void testDeleteViaChevron() {
        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        WebElement projectItem = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@href, 'job/%s') and contains(@class, 'model-link')]".formatted(PROJECT_NAME))));
        new Actions(getDriver()).moveToElement(projectItem).perform();

        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='%s']/following-sibling::button".formatted(PROJECT_NAME))));

        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronButton);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@href, 'doDelete')]"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertTrue(getDriver().findElements(By.xpath(String.format(ITEM_NAME_LOCATOR, PROJECT_NAME))).isEmpty());
    }

}
