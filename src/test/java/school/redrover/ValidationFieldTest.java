package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ValidationFieldTest extends BaseTest {

    private static final By CREATE_BUTTON = By.xpath("//div[@class = 'task '] [1]");
    private static final By SUBMIT_BUTTON = By.xpath("//button[@type = 'submit']");
    private static final By INPUT_FIELD = By.xpath("//input[@class = 'jenkins-input']");
    private static final By SET_FOLDER_TYPE = By.xpath("//div/ul/li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']");

    @Test
    public void testFieldIsEmpty() {

        getDriver().findElement((CREATE_BUTTON)).click();

        getDriver().findElement((SUBMIT_BUTTON)).click();

        WebElement validationMessage = getDriver().findElement(By.xpath("//div[@class = \"input-validation-message\"]"));

        Assert.assertEquals(validationMessage.getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testInvalidCharacters() {

        getDriver().findElement((CREATE_BUTTON)).click();

        getDriver().findElement((INPUT_FIELD)).sendKeys("#!@$!%");

        WebElement invalidMessage = getDriver().findElement(By.xpath("//div[@id = \"itemname-invalid\"]"));

        Assert.assertEquals(invalidMessage.getText(), "» ‘#’ is an unsafe character");
    }

    @Test
    public void testMaxCharacters() {

        getDriver().findElement((CREATE_BUTTON)).click();

        getDriver().findElement((INPUT_FIELD))
                .sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis auctor nisl eu est viverra, " +
                        "quis molestie nibh blandit. In lectus felis, iaculis mauris Lorem ipsum dolor sit amet, consectetur adipiscing elit " +
                        "est Lorem ipsum dolor sit amet, consectetur adipiscing elit est");

        getDriver().findElement((SET_FOLDER_TYPE)).click();

        getDriver().findElement((SUBMIT_BUTTON)).click();

        WebElement errorDescription = getDriver().findElement(By.xpath("//div/h2"));

        Assert.assertEquals(errorDescription.getText(), "A problem occurred while processing the request");
    }

    @Ignore
    @Test
    public void testExistedProject() {

        getDriver().findElement((CREATE_BUTTON)).click();

        getDriver().findElement((INPUT_FIELD)).sendKeys("Folder");

        getDriver().findElement((SET_FOLDER_TYPE)).click();

        getDriver().findElement((SUBMIT_BUTTON)).click();

        WebElement applyButton = getDriver().findElement(By.xpath("//button[@name = 'Apply']"));
        applyButton.click();

        WebElement jenkinsLogo = getDriver().findElement(By.xpath("//div/a[@id = 'jenkins-home-link']"));
        jenkinsLogo.click();

        getDriver().findElement((CREATE_BUTTON)).click();

        getDriver().findElement((INPUT_FIELD)).sendKeys("Folder");

        WebElement validationMessage = getDriver().findElement(By.xpath("//div[@id = 'itemname-invalid']"));

        Assert.assertEquals(validationMessage.getText(), "» A job already exists with the name ‘Folder’");
    }
}
