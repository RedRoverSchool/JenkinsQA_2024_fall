package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
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

    private enum DataFile {
        VALID_PIPELINE_SCRIPT("ValidPipelineScript.txt"), INVALID_PIPELINE_SCRIPT("InvalidPipelineScript.txt");

        private final String fileName;

        DataFile(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    private static final String PROJECT_NAME = "MyPipelineProject";
    private static final String NEW_PROJECT_NAME = "NewMyPipelineProject";
    private static final String FOLDER_NAME = "MyFolder";

    private static final String ITEM_NAME_LOCATOR = "//span[text()='%s']";

    @DataProvider
    public Object[][] providerUnsafeCharacters() {

        return new Object[][]{
                {"\\"}, {"]"}, {":"}, {"#"}, {"&"}, {"?"}, {"!"}, {"@"},
                {"$"}, {"%"}, {"^"}, {"*"}, {"|"}, {"/"}, {"<"}, {">"},
                {"["}, {";"}
        };
    }

    private void createItem(ItemTypes itemType, String itemName) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.xpath(ITEM_NAME_LOCATOR.formatted(itemType.getHtmlTest()))).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void goToMainPage() {
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void createItemAndGoToMainPage(ItemTypes itemType, String itemName) {
        createItem(itemType, itemName);
        goToMainPage();
    }

    private void createDisableItemAndGoToMainPage(ItemTypes itemType, String itemName) {
        createItem(itemType, itemName);
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@id='toggle-switch-enable-disable-project']"))).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        goToMainPage();
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
                getDriver().findElement(By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME))).getText(),
                PROJECT_NAME);
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

    @Test(dataProvider = "providerUnsafeCharacters")
    public void testCreateWithUnsafeCharactersInName(String unsafeCharacter) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys(unsafeCharacter);

        Assert.assertEquals(
                getDriver().findElement(By.id("itemname-invalid")).getText(),
                "» ‘%s’ is an unsafe character".formatted(unsafeCharacter));
    }

    @Test
    public void testRenameViaSidePanel() {
        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME)))).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'confirm-rename')]")).click();

        WebElement itemName = getDriver().findElement(By.xpath("//input[@name='newName']"));
        itemName.clear();
        itemName.sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        goToMainPage();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//span[text()='%s']".formatted(NEW_PROJECT_NAME)))
                .getText(), NEW_PROJECT_NAME);
    }

    @Test
    public void testRenameViaChevron() {
        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        WebElement projectItem = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME))));
        new Actions(getDriver()).moveToElement(projectItem).perform();

        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='%s']/following-sibling::button".formatted(PROJECT_NAME))));
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronButton);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@href, 'confirm-rename')]"))).click();

        WebElement itemName = getDriver().findElement(By.xpath("//input[@name='newName']"));
        itemName.clear();
        itemName.sendKeys(NEW_PROJECT_NAME);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        goToMainPage();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//span[text()='%s']".formatted(NEW_PROJECT_NAME)))
                .getText(), NEW_PROJECT_NAME);
    }


    @Test
    public void testDisabledProjectViaSidePanel() {
        createItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME)))).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        new Actions(getDriver())
                .moveToElement(getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@id='enable-disable-project']")))).click()
                .moveToElement(getDriver().findElement(By.xpath("//button[@name='Submit']"))).click()
                .perform();

        goToMainPage();

        Assert.assertTrue(getWait5().until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//tr[contains(@id,'%s')]//a[@tooltip='Schedule a Build for %s']".formatted(PROJECT_NAME, PROJECT_NAME)))));
    }

    @Test
    public void testEnableProjectAfterDisabledViaChevron() {
        createDisableItemAndGoToMainPage(ItemTypes.PIPELINE, PROJECT_NAME);

        WebElement projectItem = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(ITEM_NAME_LOCATOR.formatted(PROJECT_NAME))));
        new Actions(getDriver()).moveToElement(projectItem).perform();

        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='%s']/following-sibling::button".formatted(PROJECT_NAME))));
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronButton);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@href, 'configure')]"))).click();

        new Actions(getDriver())
                .moveToElement(getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@id='enable-disable-project']")))).click()
                .moveToElement(getDriver().findElement(By.xpath("//button[@name='Submit']"))).click()
                .perform();

        goToMainPage();

        Assert.assertTrue(getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[contains(@id,'%s')]//a[@tooltip='Schedule a Build for %s']".formatted(PROJECT_NAME, PROJECT_NAME)))).isDisplayed());
    }

    @Test
    public void testBuildWithValidPipelineScriptFromFile() {
        createItem(ItemTypes.PIPELINE, PROJECT_NAME);

        TestUtils.scrollToBottom(getDriver());

        String pipelineScript = TestUtils.readFileAndRefactoringAutoComplete(DataFile.VALID_PIPELINE_SCRIPT.getFileName());

        WebElement textArea = getDriver().findElement(By.xpath("//textarea[@class='ace_text-input']"));

        TestUtils.pasteTextWithJavaScript(getDriver(), textArea, pipelineScript);

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        goToMainPage();

        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='%s']/following-sibling::button".formatted(PROJECT_NAME))));
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronButton);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@href, 'build')]"))).click();

        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//a[contains(@class,'app-progress-bar')]")));

        getDriver().navigate().refresh();

        Assert.assertTrue(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr[@id='job_%s']//*[name()='svg'][@tooltip='Success']".formatted(PROJECT_NAME)))).isDisplayed());
    }

    @Test
    public void testBuildWithInvalidPipelineScriptFromFile() {
        createItem(ItemTypes.PIPELINE, PROJECT_NAME);

        TestUtils.scrollToBottom(getDriver());

        String pipelineScript = TestUtils.readFileAndRefactoringAutoComplete(DataFile.INVALID_PIPELINE_SCRIPT.getFileName());

        WebElement textArea = getWait10().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//textarea[@class='ace_text-input']")));

        TestUtils.pasteTextWithJavaScript(getDriver(), textArea, pipelineScript);;

        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        goToMainPage();

        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='%s']/following-sibling::button".formatted(PROJECT_NAME))));
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronButton);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@href, 'build')]"))).click();

        getWait10().until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//a[contains(@class,'app-progress-bar')]")));

        getDriver().navigate().refresh();

        Assert.assertTrue(getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr[@id='job_%s']//*[name()='svg'][@tooltip='Failed']".formatted(PROJECT_NAME)))).isDisplayed());
    }

}
