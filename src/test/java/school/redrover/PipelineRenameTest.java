package school.redrover;

import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

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

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement chevronButton = getDriver().findElement(By.xpath("//table[@id='projectstatus']//button"));
        WebElement pipelineProject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='job/" + NAME_OF_PROJECT + "/']")));

        new Actions(getDriver()).moveToElement(pipelineProject)
                .pause(4000)
                .moveToElement(chevronButton)
                .pause(3000)
                .click()
                .perform();

        wait.until(ExpectedConditions.attributeToBe(chevronButton, "aria-expanded", "true"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tippy-6\"]/div/div/div/a[4]"))).click();

        WebElement renameField = getDriver().findElement(By.xpath("//input[@checkdependson='newName']"));
        renameField.clear();
        renameField.sendKeys(NEW_NAME_OF_PROJECT);

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button")))
                        .pause(1000)
                        .click()
                        .perform();


        getDriver().findElement(By.id("jenkins-name-icon")).click();

        String actualPipeLineName = getDriver().findElement(By.xpath("//span[text()='" + NEW_NAME_OF_PROJECT + "']")).getText();

        Assert.assertEquals(actualPipeLineName, NEW_NAME_OF_PROJECT);
    }

}
