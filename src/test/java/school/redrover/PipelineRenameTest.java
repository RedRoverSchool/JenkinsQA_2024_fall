package school.redrover;

import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

public class PipelineRenameTest extends BaseTest {

    private final static String NAME_OF_PROJECT = RandomString.make(10);
    private final static String NEW_NAME_OF_PROJECT = RandomString.make(5);

    private void createPipelineProjectAndBackToHomePage(String name) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    private void goToRenamePageAndInputNewProjectName(String currentProjectName, String newProjectName) {

        WebElement chevronButton = getDriver().findElement(By.xpath("//td//button[@aria-expanded='false']"));
        WebElement pipelineProject = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='job/%s/']".formatted(currentProjectName))));

        new Actions(getDriver()).moveToElement(pipelineProject)
                .pause(500)
                .perform();

        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronButton);

        getWait5().until(ExpectedConditions.attributeToBe(chevronButton, "aria-expanded", "true"));
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/job/%s/confirm-rename']".formatted(currentProjectName)))).click();

        WebElement renameField = getDriver().findElement(By.xpath("//input[@checkdependson='newName']"));
        renameField.clear();
        renameField.sendKeys(newProjectName);
        getDriver().findElement(By.name("Submit")).click();

    }

    @Test
    public void testRenamePipelineProjectViaChevron() {

        createPipelineProjectAndBackToHomePage(NAME_OF_PROJECT);

        goToRenamePageAndInputNewProjectName(NAME_OF_PROJECT, NEW_NAME_OF_PROJECT);

        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String actualPipeLineName = getDriver().findElement(
                By.xpath("//span[text()='%s']".formatted(NEW_NAME_OF_PROJECT))).getText();

        Assert.assertEquals(actualPipeLineName, NEW_NAME_OF_PROJECT);
    }

    @Test
    public void testShouldNotRenamePipelineProjectWithDuplicateName() {

    createPipelineProjectAndBackToHomePage(NAME_OF_PROJECT);

    goToRenamePageAndInputNewProjectName(NAME_OF_PROJECT, NAME_OF_PROJECT);

    String actualErrorMessage = getDriver().findElement(
            By.xpath("//p[contains(text(), 'The new name is the same as the current name')]")).getText();

    Assert.assertEquals(actualErrorMessage, "The new name is the same as the current name.");

    }
}
