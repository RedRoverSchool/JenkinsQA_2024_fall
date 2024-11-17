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
    public void createProject()  {

        getDriver().findElement(By.xpath("//span[text() ='Create a job']")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#name"))).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='GitHub project']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("(//input[@name='scm'])[1]")).getAttribute("checked"),"true");
    }
}
