package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class PipelineProject2Test extends BaseTest {

    private static final String PROJECT_NAME = "MyPipelineProject";

    private void createItemUtils(String name, String locator) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.xpath(locator)).click();
        getDriver().findElement(By.id("ok-button")).click();

    }

    @Test
    public void testCreatePipelineProjectWithValidName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItemUtils(PROJECT_NAME, "//span[text()='Pipeline']");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String pipelineProjectName = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(String.format("//span[text()='%s']", PROJECT_NAME))))
                .getText();

        Assert.assertEquals(pipelineProjectName, PROJECT_NAME);
    }

    @Test
    public void testCreatePipelineProjectWithEmptyName() {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-required"))
                .getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreatePipelineProjectWithDuplicateName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItemUtils(PROJECT_NAME, "//span[text()='Pipeline']");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/view/all/newJob']"))
        ).click();

        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.id("itemname-invalid"))
                .getText(), String.format("» A job already exists with the name ‘%s’", PROJECT_NAME));
    }

    @Test
    public void testAddDescriptionForPipelineProject() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        String description = "Description pipeline project.";

        createItemUtils(PROJECT_NAME, "//span[text()='Pipeline']");
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(String.format("//span[text()='%s']", PROJECT_NAME))))
                .click();

        getDriver().findElement(By.id("description-link")).click();
        getDriver()
                .findElement(By.xpath("//textarea[@name='description']"))
                .sendKeys(description);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//div[@id='description']/div"))
                .getText(), description);
    }


}
