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

    private static final String ITEM_LOCATOR_BY_NAME = "//span[text()='%s']";
    private static final String FIRST_FOLDER_NANE = "Freestyle projects";
    private static final String LINK_LOCATOR = "//a[contains(text(),'%s')]";

    private WebElement getLinkElementByName(String elementName){
        return getDriver().findElement(By.xpath("//a[contains(text(),'%s')]".formatted(elementName)));
    }

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
    public void configureFolder() throws InterruptedException {

        nameItemType(FIRST_FOLDER_NANE);
        TestUtils.scrollToBottom(getDriver());
        selectItemType(ItemType.FOLDER);

        getDriver().findElement(By.xpath("//button[contains(@name, 'Submit')]")).click();

        goToDashboard();

        WebElement chevron = getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']//button[@class='jenkins-menu-dropdown-chevron']"));

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME.formatted(FIRST_FOLDER_NANE))))
                .moveToElement(chevron).click().perform();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@class='jenkins-dropdown__item '][1]"))).click();

        getDriver().findElement(By.xpath("//div[contains(text(),'Display Name')]/following-sibling::div[1]/input"))
                .sendKeys(FIRST_FOLDER_NANE);

        getDriver().findElement(By.xpath("//div[contains(text(),'Description')]/following-sibling::div[1]/textarea"))
                .sendKeys("description");

        getDriver().findElement(By.name("Submit")).click();
        goToDashboard();

        Thread.sleep(5000);
    }



}
