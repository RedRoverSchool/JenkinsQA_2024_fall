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

public class FolderTest extends BaseTest {

    private enum ItemType {

        FOLDER("Folder"),
        FREESTYLE_PROJECT("Freestyle project");

        private final String itemName;

        ItemType(String itemName){
            this.itemName = itemName;
        }

        public String getItemName(){
            return itemName;
        }
    }

    private enum FolderMenu {

        CONFIGURE("1");

        private final String menuNumber;

        FolderMenu(String menuNumber){
            this.menuNumber = menuNumber;
        }

        public String getMenuNumber(){
            return menuNumber;
        }
    }

    private static final String ITEM_LOCATOR_BY_NAME = "//span[text()='%s']";
    private static final String FIRST_FOLDER_NANE = "Freestyle projects";

    private void nameItemType(String name){

        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::a")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);

    }

    private void selectItemType(ItemType itemType){

        getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(itemType.getItemName()))).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    private void goToDashboard(){
        getDriver().findElement(By.xpath("//a[contains(text(),'Dashboard')]")).click();
    }

    private void selectFolderMenuByChevron(String folderName, FolderMenu folderMenuName){

        WebElement chevron = getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']//button[@class='jenkins-menu-dropdown-chevron']"));

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME
                        .formatted(folderName)))).perform();
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@class='jenkins-dropdown__item '][%s]"
                        .formatted(FolderMenu.CONFIGURE.getMenuNumber())))).click();

    }

    @Test
    public void testCreateWithoutConfiguration(){

        nameItemType(FIRST_FOLDER_NANE);
        TestUtils.scrollToBottom(getDriver());
        selectItemType(ItemType.FOLDER);

        getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]")).click();

        goToDashboard();

        Assert.assertTrue(
                getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(ItemType.
                        FOLDER.getItemName()))).isDisplayed());
    }

    @Test
    public void configureNameByChevron() {

        nameItemType(FIRST_FOLDER_NANE);
        TestUtils.scrollToBottom(getDriver());
        selectItemType(ItemType.FOLDER);

        getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]")).click();
        goToDashboard();

        selectFolderMenuByChevron(FIRST_FOLDER_NANE, FolderMenu.CONFIGURE);

        getDriver().findElement(By.xpath("//div[contains(text(),'Display Name')]/following-sibling::div[1]/input"))
                .sendKeys(FIRST_FOLDER_NANE + 1);

        getDriver().findElement(By.name("Submit")).click();
        goToDashboard();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link inside']")).getText(),
                FIRST_FOLDER_NANE + 1);

    }

    @Test
    public void configureDescriptionByChevron() {

        nameItemType(FIRST_FOLDER_NANE);
        TestUtils.scrollToBottom(getDriver());
        selectItemType(ItemType.FOLDER);

        getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]")).click();
        goToDashboard();

        selectFolderMenuByChevron(FIRST_FOLDER_NANE, FolderMenu.CONFIGURE);

        getDriver().findElement(By.xpath("//div[contains(text(),'Description')]/following-sibling::div[1]/textarea"))
                .sendKeys("This is folder description");

        getDriver().findElement(By.name("Submit")).click();
        goToDashboard();

        getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(FIRST_FOLDER_NANE))).click();

        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(),
                "This is folder description");

    }



}
