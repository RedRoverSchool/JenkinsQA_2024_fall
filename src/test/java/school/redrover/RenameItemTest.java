package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class RenameItemTest extends BaseTest {

    private void createItem(){

        getDriver().findElement(By.xpath("//a[@href=\"/view/all/newJob\"]")).click();
        getDriver().findElement(By.id("name")).sendKeys("first");
        getDriver().findElement(By.xpath("//li[@class=\"hudson_model_FreeStyleProject\"]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement saveButton = getDriver().findElement(By.xpath("//button[@name='Submit']"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(saveButton).click().perform();
    }

    @Test
    public void testRenameItem() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        final String enteredName = "MyNewName";
        createItem();

        WebElement renameButton = wait.until
                (ExpectedConditions.elementToBeClickable((By.xpath("//div[7]"))));
        renameButton.click();

        WebElement newNameField = getDriver()
                .findElement(By.xpath("//input[@checkdependson='newName']"));
        newNameField.clear();
        newNameField.sendKeys(enteredName);

        WebElement renameSubmitButton = wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[normalize-space()='Rename']")));
        renameSubmitButton.click();

        String changedNameArea = getDriver().findElement(By
                .xpath("//div[@class='jenkins-app-bar__content jenkins-build-caption']")).getText();

        Assert.assertEquals(enteredName, changedNameArea);


    }

}
