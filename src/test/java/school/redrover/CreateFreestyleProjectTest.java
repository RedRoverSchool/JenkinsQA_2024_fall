package school.redrover;

import org.openqa.selenium.By;
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
    public void testVerifyFreeStyleProjectInMyViewsPage () throws InterruptedException {
        final String EXPECTED_FREE_STYLE_PROJECT_NAME ="My First Free Style Project";

        getDriver().findElement(By.cssSelector(".task-link-wrapper")).click();

        getDriver().findElement(By.cssSelector(".jenkins-input")).sendKeys(EXPECTED_FREE_STYLE_PROJECT_NAME);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys("Lorem ipsum dolor sit amet");
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();

        Thread.sleep(2000);

        getDriver().findElement(By.cssSelector(".jenkins-breadcrumbs__list-item")).click();
        getDriver().findElement(By.xpath("//a[@href='/me/my-views']"));

        String actualFreeStyleProjectName = getDriver().findElement(By.xpath("//span[text()='My First Free Style Project']")).getText();

        Assert.assertEquals(EXPECTED_FREE_STYLE_PROJECT_NAME, actualFreeStyleProjectName);
    }
}
