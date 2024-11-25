package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateNodeTest extends BaseTest {

    @Test
    public void testCreateNewAgent() {
        getDriver().findElement(By.xpath("//a[contains(.,'Manage Jenkins')]")).click();

        getDriver().findElement(By.xpath("//dt[.='Nodes']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-button--primary")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys("NewAgent");
        getDriver().findElement(By.tagName("label")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement newAgent = getDriver().findElement(By.xpath("//a[.='NewAgent']"));

        Assert.assertTrue(newAgent.isDisplayed());
    }
}


