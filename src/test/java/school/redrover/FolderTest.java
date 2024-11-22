package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.NewItemPage;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class FolderTest extends BaseTest {

    private enum ItemType {

        FOLDER("Folder"),
        FREESTYLE_PROJECT("Freestyle project");

        private final String itemName;

        ItemType(String itemName) {
            this.itemName = itemName;
        }

        public String getItemName() {
            return itemName;

        }
    }

    private enum FolderMenu {

        CONFIGURE("1"),
        NEW_ITEM("2"),
        BUILD_HISTORY("4");

        private final String menuNumber;

        FolderMenu(String menuNumber) {
            this.menuNumber = menuNumber;
        }

        public String getMenuNumber() {
            return menuNumber;

        }
    }

    private static final String ITEM_LOCATOR_BY_NAME = "//span[text()='%s']";
    private static final String FIRST_FOLDER_NAME = "Freestyle projects";
    private static final String FREESTYLE_PROJECT_NAME = "First freestyle project job";
    private static final String ITEM_NAME_MAX_LENGTH = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";
    private void createAndNameNewItem(String name) {

        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::a")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);

    }

    private void nameItemType(String name) {

        getDriver().findElement(By.id("name")).sendKeys(name);

    }

    private void selectItemTypeAndSave(ItemType itemType) {

        getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(itemType.getItemName()))).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    private void goToDashboard() {

        getDriver().findElement(By.xpath("//a[contains(text(),'Dashboard')]")).click();

    }

    private void selectFolderMenuByChevron(String folderName, FolderMenu folderMenu) {

        WebElement chevron = getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']//button[@class='jenkins-menu-dropdown-chevron']"));

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME
                .formatted(folderName)))).perform();
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@class='jenkins-dropdown__item '][%s]"
                        .formatted(folderMenu.getMenuNumber())))).click();

    }

    private void submit() {

        getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]")).click();

    }

    private void addDescription(String description) {

        getDriver().findElement(By.xpath("//div[contains(text(),'Description')]/following-sibling::div[1]/textarea"))
                .sendKeys(description);

    }

    private void clickButtonByName(String buttonName) {

        getDriver().findElement(By.xpath("//button[contains(text(),'%s')]".formatted(buttonName)))
                .click();

    }

    private void openFolderByName(String folderName) {

        getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(folderName))).click();

    }

    private void runJob(String projectName) {

        getDriver().findElement(By.xpath("//td//a[@title='Schedule a Build for %s']".formatted(projectName))).click();

    }

    @Test
    public void testCreateWithMaxNameLength() {

        new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(ITEM_NAME_MAX_LENGTH)
                .selectProjectTypeAndSave(NewItemPage.ItemType.FOLDER)
                .goToDashboard();

        Assert.assertTrue(
                getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(ItemType.
                        FOLDER.getItemName()))).isDisplayed());
    }

    @Test()
    public void testCreateWithoutConfiguration() {

        createAndNameNewItem(FIRST_FOLDER_NAME);
        TestUtils.scrollToBottom(getDriver());
        selectItemTypeAndSave(ItemType.FOLDER);

        submit();

        goToDashboard();

        Assert.assertTrue(
                getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(ItemType.
                        FOLDER.getItemName()))).isDisplayed());
    }

    @Test
    public void configureNameByChevron() {

        createAndNameNewItem(FIRST_FOLDER_NAME);
        TestUtils.scrollToBottom(getDriver());
        selectItemTypeAndSave(ItemType.FOLDER);

        submit();
        goToDashboard();

        selectFolderMenuByChevron(FIRST_FOLDER_NAME, FolderMenu.CONFIGURE);

        getDriver().findElement(By.xpath("//div[contains(text(),'Display Name')]/following-sibling::div[1]/input"))
                .sendKeys(FIRST_FOLDER_NAME + 1);

        getDriver().findElement(By.name("Submit")).click();
        goToDashboard();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(),
                FIRST_FOLDER_NAME + 1);

    }

    @Test
    public void configureDescriptionByChevron() {

        createAndNameNewItem(FIRST_FOLDER_NAME);
        TestUtils.scrollToBottom(getDriver());
        selectItemTypeAndSave(ItemType.FOLDER);

        submit();
        goToDashboard();

        selectFolderMenuByChevron(FIRST_FOLDER_NAME, FolderMenu.CONFIGURE);
        addDescription("This is folder description");
        submit();
        goToDashboard();

        getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(FIRST_FOLDER_NAME))).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(),
                "This is folder description");

    }

    @Test
    public void createNewItemByChevron() {

        createAndNameNewItem(FIRST_FOLDER_NAME);
        TestUtils.scrollToBottom(getDriver());
        selectItemTypeAndSave(ItemType.FOLDER);
        submit();
        goToDashboard();

        selectFolderMenuByChevron(FIRST_FOLDER_NAME, FolderMenu.NEW_ITEM);
        nameItemType(FREESTYLE_PROJECT_NAME);
        selectItemTypeAndSave(ItemType.FREESTYLE_PROJECT);

        addDescription("This is project description");
        TestUtils.scrollToBottom(getDriver());
        clickButtonByName("Add build step");
        clickButtonByName("Execute Windows batch command");
        getDriver().findElement(By.xpath("//textarea[@name='command']"))
                .sendKeys("echo 'Hello world!'");
        submit();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar']//div[1]")).getText(),
                FREESTYLE_PROJECT_NAME);

        Assert.assertEquals(getDriver().findElement(By.id("description")).getText(),
                "This is project description");

    }

    @Ignore
    @Test
    public void openBuildHistoryByChevron() {

        createAndNameNewItem(FIRST_FOLDER_NAME);
        TestUtils.scrollToBottom(getDriver());
        selectItemTypeAndSave(ItemType.FOLDER);
        submit();
        goToDashboard();

        selectFolderMenuByChevron(FIRST_FOLDER_NAME, FolderMenu.NEW_ITEM);
        nameItemType(FREESTYLE_PROJECT_NAME);
        selectItemTypeAndSave(ItemType.FREESTYLE_PROJECT);

        TestUtils.scrollToBottom(getDriver());
        clickButtonByName("Add build step");
        clickButtonByName("Execute Windows batch command");
        getDriver().findElement(By.xpath("//textarea[@name='command']"))
                .sendKeys("echo 'Hello world!'");
        submit();

        goToDashboard();
        openFolderByName(FIRST_FOLDER_NAME);
        runJob(FREESTYLE_PROJECT_NAME);

        goToDashboard();
        selectFolderMenuByChevron(FIRST_FOLDER_NAME, FolderMenu.BUILD_HISTORY);

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link']/span")).getText(),
                "%s » %s".formatted(FIRST_FOLDER_NAME, FREESTYLE_PROJECT_NAME));

    }
}
