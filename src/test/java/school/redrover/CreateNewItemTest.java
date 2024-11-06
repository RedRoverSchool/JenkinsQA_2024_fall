package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.lang.reflect.Array;

public class CreateNewItemTest extends BaseTest {

    @Test
    public void testCreateNewItemFSProject() {
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        final String nameNewItem = "Test freestyle project item";
        final String description = "Test Item Description";
        WebElement name = getDriver().findElement(By.xpath("//*[@id='name']"));
        name.sendKeys(nameNewItem);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,1000)");
        getDriver().findElement(By.id("ok-button")).click();
        WebElement descriptionItem = getDriver().findElement(By.id("jenkins"));
        descriptionItem.sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(),nameNewItem);

    }

    @Test (description = "Проверка предупреждающего сообщения при попытке ввести имя уже существующего джоба")
    public void testCreateExistingItem() {
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        final String nameNewItem = "Test freestyle project item";
        final String description = "Test Item Description";
        WebElement name = getDriver().findElement(By.xpath("//*[@id='name']"));
        name.sendKeys(nameNewItem);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,1000)");
        getDriver().findElement(By.id("ok-button")).click();
        WebElement descriptionItem = getDriver().findElement(By.id("jenkins"));
        descriptionItem.sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        name = getDriver().findElement(By.xpath("//*[@id='name']"));
        name.sendKeys(nameNewItem);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        Assert.assertEquals(getDriver().findElement(By.id("itemname-invalid")).getText(), "» A job already exists with the name ‘"+nameNewItem+"’");
    }

    @Test (description = "Проверка, что при попытке создания джоба с сущесвующем именем, отображается ошибка")
    public void testCreateExistingItemErrorPageDisplayed() {
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        final String nameNewItem = "Test freestyle project item";
        final String description = "Test Item Description";
        WebElement name = getDriver().findElement(By.xpath("//*[@id='name']"));
        name.sendKeys(nameNewItem);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0,1000)");
        getDriver().findElement(By.id("ok-button")).click();
        WebElement descriptionItem = getDriver().findElement(By.id("jenkins"));
        descriptionItem.sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();
        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        name = getDriver().findElement(By.xpath("//*[@id='name']"));
        name.sendKeys(nameNewItem);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[1]")).click();
        //js = (JavascriptExecutor) getDriver();
        //js.executeScript("window.scrollBy(0,1000)");
        getDriver().findElement(By.id("ok-button")).click();
        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Error");
    }

    @Test
    public void testUseUnsafeCharacter() {
        final char[] unsafeCharacter = {'!', '@', '#', '$', ';', ':', '%', '^', '&', '*'}; // сверить по документации, возможно список не полный

        getDriver().findElement(By.xpath("//a[@href ='/view/all/newJob']")).click();
        WebElement name = getDriver().findElement(By.xpath("//*[@id='name']"));
        for (char i : unsafeCharacter) {
            name.sendKeys(Character.toString(i));
            Assert.assertEquals(getDriver().findElement(By.id("itemname-invalid")).getText(), "» ‘"+Character.toString(i)+"’ is an unsafe character");
            name.clear();
        }
    }

}

