package school.redrover;

import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class PipelineRenameTest extends BaseTest {

    private final static String NAME_OF_PROJECT = RandomString.make(10);
    private final static String NEW_NAME_OF_PROJECT = RandomString.make(5);

    private void createPipelineProject(String name) {

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.xpath("//span[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();
    }

    @Test
    public void testRenamePipelineViaChevron() {
        createPipelineProject(NAME_OF_PROJECT);

        WebElement chevronButton = getDriver().findElement(By.xpath("//td//button[@aria-expanded='false']"));
        WebElement pipelineProject = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='job/%s/']".formatted(NAME_OF_PROJECT))));

        new Actions(getDriver()).moveToElement(pipelineProject)
                .pause(500)
                .moveToElement(chevronButton)
                .perform();

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));",
                chevronButton);

        new Actions(getDriver()).moveByOffset(0, -10).click().perform();

        new Actions(getDriver()).moveToElement(pipelineProject)
                .pause(500)
                .moveToElement(chevronButton)
                .perform();

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));",
                chevronButton);

        getWait5().until(ExpectedConditions.attributeToBe(chevronButton, "aria-expanded", "true"));

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/job/%s/confirm-rename']".formatted(NAME_OF_PROJECT)))).click();

        WebElement renameField = getDriver().findElement(By.xpath("//input[@checkdependson='newName']"));
        renameField.clear();
        renameField.sendKeys(NEW_NAME_OF_PROJECT);

        getDriver().findElement(By.name("Submit")).click();
        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String actualPipeLineName = getDriver().findElement(By.xpath("//span[text()='%s']".formatted(NEW_NAME_OF_PROJECT))).getText();

        Assert.assertEquals(actualPipeLineName, NEW_NAME_OF_PROJECT);
    }

}
