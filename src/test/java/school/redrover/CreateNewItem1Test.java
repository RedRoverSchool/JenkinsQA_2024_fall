package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateNewItem1Test extends BaseTest {

    private static final String ITEM_NAME = "CreateNewItem";
    private static final String INVALID_NAME = "CreateNewItem";

    private void createItem() {
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class,'hudson_model_FreeStyleProject')]")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']/div/button")).click();
    }

    private void goToMainPage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();
    }

    private void checkTheResult() {
        Assert.assertEquals(getDriver().findElement(By.xpath("//a[contains(@class,'jenkins-table__link')]")).getText(), ITEM_NAME);
    }

    private void clickLocator(String locator) {
        getDriver().findElement(By.xpath(locator)).click();
    }

    @Test
    public void testCreateWithButton() {
        clickLocator("//a[@href='newJob']");

        createItem();
        goToMainPage();
        checkTheResult();
    }

    @Test
    public void testCreateWithLinkInSidebar() {
        clickLocator("//a[@href='/view/all/newJob']");

        createItem();
        goToMainPage();
        checkTheResult();
    }

    @Test
    public void testCheckUniqueItemName() {
        clickLocator("//a[@href='/view/all/newJob']");
        createItem();
        goToMainPage();
        clickLocator("//a[@href='/view/all/newJob']");

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(ITEM_NAME);

        String itemNameInvalid = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();

        Assert.assertEquals(itemNameInvalid, "» A job already exists with the name ‘%s’".formatted(ITEM_NAME));
    }

    @Test
    public void testCheckInvalidName() {
        clickLocator("//a[@href='/view/all/newJob']");

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(INVALID_NAME);

        String itemNameInvalid = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();

        Assert.assertEquals(itemNameInvalid, "» ‘%s’ is an unsafe character".formatted(INVALID_NAME.charAt(0)));

    }
}
