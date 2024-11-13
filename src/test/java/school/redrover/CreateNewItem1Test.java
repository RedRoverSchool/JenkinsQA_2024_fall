package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateNewItem1Test extends BaseTest {

    private static final String ITEM_NAME = "CreateNewItem";
    private static final String INVALID_NAME = "<{]_  -&";

    @Test
    public void testCreateWithButton() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class,'hudson_model_FreeStyleProject')]")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']/div/button")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[contains(@class,'jenkins-table__link')]")).getText(), ITEM_NAME);
    }

    @Test
    public void testCreateWithLinkInSidebar() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class,'hudson_model_FreeStyleProject')]")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']/div/button")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[contains(@class,'jenkins-table__link')]")).getText(), ITEM_NAME);
    }

    @Test
    public void testCheckUniqueItemName() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.xpath("//li[contains(@class,'hudson_model_FreeStyleProject')]")).click();
        getDriver().findElement(By.xpath("//div[@id='bottom-sticker']/div/button")).click();

        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(ITEM_NAME);

        String itemNameInvalid = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();

        Assert.assertEquals(itemNameInvalid, "» A job already exists with the name ‘%s’".formatted(ITEM_NAME));
    }

    @Test
    public void testCheckInvalidName() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//input[@name='name']")).sendKeys(INVALID_NAME);

        String itemNameInvalid = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("itemname-invalid"))).getText();

        Assert.assertEquals(itemNameInvalid, "» ‘%s’ is an unsafe character".formatted(INVALID_NAME.charAt(0)));

    }
}
