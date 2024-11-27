package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class GroupWnFTest extends BaseTest {

    @Test
    public void welcomeMessage() {
        WebElement welcomeMessage = getDriver().findElement(By.xpath("//h1"));
        Assert.assertEquals(welcomeMessage.getText(), "Welcome to Jenkins!");
    }

    @Test
    public void manageJenkinsButtonTest() {
        WebElement manageJenkinsButton = getDriver().findElement(By.linkText("Manage Jenkins"));
        Assert.assertTrue(manageJenkinsButton.isDisplayed(), "Manage Jenkins button should be displayed on the home page");
    }
}