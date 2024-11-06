package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

public class CreateNewViewTest extends BaseTest {

    @Test
    public void testCreateNewView(){
        final String expectedNewViewName = "NewView";
        final String expectedNewViewUrl = ProjectUtils.getUrl() + "view/" + expectedNewViewName + "/";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("Project");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();

        getDriver().findElement(By.id("name")).sendKeys(expectedNewViewName);

        getDriver().findElement(By.cssSelector("[for='hudson.model.ListView']")).click();

        getDriver().findElement(By.cssSelector("button[id='ok']")).click();

        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        String actualNewViewUrl = getDriver().getCurrentUrl();
        String actualNewViewName = getDriver().findElement(By.xpath("//div/a[@href='/view/" + expectedNewViewName + "/']")).getText();

        Assert.assertEquals(actualNewViewName, expectedNewViewName);
        Assert.assertEquals(actualNewViewUrl, expectedNewViewUrl);
    }

    @Test
    public void testCreateNewViewForm(){
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("Project");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();

        String inputNameField = getDriver().findElement(By.id("name")).getAttribute("value");
        WebElement radioButtonListView = getDriver().findElement(By.cssSelector("[for='hudson.model.ListView']"));
        WebElement radioButtonMyView = getDriver().findElement(By.cssSelector("[for='hudson.model.MyView']"));
        WebElement createButton = getDriver().findElement(By.cssSelector("button[id='ok']"));

        Assert.assertTrue(inputNameField.isEmpty(), "Input field should be empty.");
        Assert.assertFalse(radioButtonListView.isSelected(), "ListView radio button should not be selected.");
        Assert.assertFalse(radioButtonMyView.isSelected(), "MyView radio button should not be selected.");
        Assert.assertFalse(createButton.isEnabled(), "Create button should be disabled.");
    }

    @Test
    public void testCreateNewViewInvalidInput() throws InterruptedException {
        final String expectedErrorMessage = "is an unsafe character";
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();

        getDriver().findElement(By.id("name")).sendKeys("Project");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        getDriver().findElement(By.id("jenkins-home-link")).click();

        getDriver().findElement(By.xpath("//a[@href='/newView']")).click();

        getDriver().findElement(By.id("name")).sendKeys("$%^");
        getDriver().findElement(By.id("name")).sendKeys(Keys.ENTER);

        Thread.sleep(1000);
        String actualErrorMessage = getDriver().findElement(By.xpath("//div[@class='error']")).getText().substring(4);

        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }
}