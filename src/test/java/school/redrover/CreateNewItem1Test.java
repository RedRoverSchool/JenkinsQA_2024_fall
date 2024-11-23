package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateNewItem1Test extends BaseTest {

    private static final String ITEM_NAME = "CreateNewItem";
    private static final String INVALID_NAME = "<{]_  -&";

    private void clickElement(By by) {
        getDriver().findElement(by).click();
    }

    private void createItem() {
        createFreeStyleProject();
        clickElement(By.xpath("//div[@id='bottom-sticker']/div/button"));

        clickElement(By.id("jenkins-home-link"));
    }

    private void createFreeStyleProject() {
        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(ITEM_NAME);
        clickElement(By.xpath("//li[contains(@class,'hudson_model_FreeStyleProject')]"));
    }

    @Test
    public void testWithButton() {
        clickElement(By.xpath("//a[@href='newJob']"));
        createItem();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[contains(@class,'jenkins-table__link')]")).getText(), ITEM_NAME);
    }

    @Test
    public void testWithLinkInSidebar() {
        clickElement(By.xpath("//a[@href='/view/all/newJob']"));
        createItem();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[contains(@class,'jenkins-table__link')]")).getText(), ITEM_NAME);
    }

    @Test(dependsOnMethods = "testWithLinkInSidebar")
    public void testCheckUniqueItemName() {
        clickElement(By.xpath("//a[@href='/view/all/newJob']"));
        createFreeStyleProject();

        String itemNameInvalid = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();

        Assert.assertEquals(itemNameInvalid, "» A job already exists with the name ‘%s’".formatted(ITEM_NAME));
    }

    @Test
    public void testCheckInvalidName() {
        clickElement(By.xpath("//a[@href='/view/all/newJob']"));

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(INVALID_NAME);

        String itemNameInvalid = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();

        Assert.assertEquals(itemNameInvalid, "» ‘%s’ is an unsafe character".formatted(INVALID_NAME.charAt(0)));

    }
}
