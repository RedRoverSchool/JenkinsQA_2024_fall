package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class NewItemTest extends BaseTest {

    @Test
    public void testCreateNewItem() {

        WebElement newItem = getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a"));
        newItem.click();

        WebElement enterName = getDriver().findElement(By.xpath("//*[@id='name']"));
        enterName.sendKeys("Ivan");

        WebElement selectItemType = getDriver().findElement(By.xpath("//*[@id='j-add-item-type-standalone-projects']/ul/li[2]"));
        selectItemType.click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 500);");

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        okButton.click();

        WebElement description = getDriver().findElement(By.name("description"));
        description.sendKeys("Interesting");

        WebElement planText = getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[3]/div[1]/div/span/label"));
        planText.click();

        JavascriptExecutor jss = (JavascriptExecutor) getDriver();
        jss.executeScript("window.scrollBy(0, 1000);");

        WebElement saveButton = getDriver().findElement(By.name("Submit"));
        saveButton.click();

        String title = getDriver().findElement(By.tagName("h1")).getText();
        Assert.assertEquals(title, "Ivan");

    }
    @Test
    public void buildHistory(){
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[2]/span/a")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Build History of Jenkins");

    }



}