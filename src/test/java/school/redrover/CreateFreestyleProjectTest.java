package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateFreestyleProjectTest extends BaseTest {
    @Test
    public void testCreateFreestyleProject() {
        final String expectedProjectName = "new Freestyle project";

        getDriver().findElement(By.xpath("//*[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).clear();
        getDriver().findElement(By.id("name")).sendKeys(expectedProjectName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        String newProjectName = getDriver().findElement(By.tagName("h1")).getText();

        Assert.assertEquals(newProjectName, expectedProjectName);

    }

    @Test
    public void testVerifyFreeStyleProjectInMyViewsPage () {
        final String expectedFreeStyleProjectName = "My First Free Style Project";

        getDriver().findElement(By.cssSelector(".task-link-wrapper")).click();

        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(expectedFreeStyleProjectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys("Lorem ipsum dolor sit amet");
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        getWait2().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".jenkins-breadcrumbs__list-item"))).click();
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']"));

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("a[href='job/My%20First%20Free%20Style%20Project/']")).getText(),
                expectedFreeStyleProjectName);
    }
}
