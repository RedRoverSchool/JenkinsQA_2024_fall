package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class FreestyleProject8Test extends BaseTest {
    private static final String PROJECT_NAME = "newProject";
    @Test
    public void testCreateProject()  {

        getDriver().findElement(By.xpath("//span[text() ='Create a job']")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#name"))).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='GitHub project']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("(//input[@name='scm'])[1]")).getAttribute("checked"),"true");
    }

    @Test
    public void testRenameProject() {
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[2]/div/section[1]/ul/li/a")).click();
        getDriver().findElement(By.id("name")).sendKeys("fuigdjghf");
        getDriver().findElement(By.xpath("//*[@id=\"j-add-item-type-standalone-projects\"]/ul/li[1]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[7]/span/a")).click();
        getDriver().findElement(By.name("newName")).clear();
        getDriver().findElement(By.name("newName")).sendKeys("NewFreestyleProject");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/div[1]/div[1]/h1")).getText(), "NewFreestyleProject");
    }
}
