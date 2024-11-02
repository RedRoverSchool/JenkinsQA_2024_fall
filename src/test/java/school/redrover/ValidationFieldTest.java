package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ValidationFieldTest extends BaseTest {

    private static final String CREATE_BUTTON = "//a[@it = 'hudson.model.Hudson@185982f1']";
    private static final String SUBMIT_BUTTON = "//button[@type = 'submit']";
    private static final String INPUT_FIELD = "//input[@class = 'jenkins-input']";
    private static final String SET_FOLDER_TYPE = "//div/ul/li[@class = 'com_cloudbees_hudson_plugins_folder_Folder']";

    @Test
    public void testFieldIsEmpty() {

        getDriver().findElement(By.xpath(CREATE_BUTTON))
                .click();

        getDriver().findElement(By.xpath(SUBMIT_BUTTON))
                .click();

        WebElement validationMessage = getDriver().findElement(By.xpath("//div[@class = \"input-validation-message\"]"));

        Assert.assertEquals(validationMessage.getText(), "» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testInvalidCharacters() {

        getDriver().findElement(By.xpath(CREATE_BUTTON))
                .click();

        getDriver().findElement(By.xpath(INPUT_FIELD))
                .sendKeys("#!@$!%");

        WebElement invalidMessage = getDriver().findElement(By.xpath("//div[@id = \"itemname-invalid\"]"));

        Assert.assertEquals(invalidMessage.getText(), "» ‘#’ is an unsafe character");
    }

    @Test
    public void testMaxCharacters() {

        getDriver().findElement(By.xpath(CREATE_BUTTON))
                .click();

        getDriver().findElement(By.xpath(INPUT_FIELD))
                .sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis auctor nisl eu est viverra, " +
                        "quis molestie nibh blandit. In lectus felis, iaculis mauris Lorem ipsum dolor sit amet, consectetur adipiscing elit " +
                        "est Lorem ipsum dolor sit amet, consectetur adipiscing elit est");

        getDriver().findElement(By.xpath(SET_FOLDER_TYPE))
                .click();

        getDriver().findElement(By.xpath(SUBMIT_BUTTON))
                .click();

        WebElement errorDescription = getDriver().findElement(By.xpath("//div/h2"));

        Assert.assertEquals(errorDescription.getText(), "A problem occurred while processing the request");
    }

    @Test
    public void testExistedProject() {

        getDriver().findElement(By.xpath(CREATE_BUTTON))
                .click();

        getDriver().findElement(By.xpath(INPUT_FIELD))
                .sendKeys("Folder");

        getDriver().findElement(By.xpath(SET_FOLDER_TYPE))
                .click();

        getDriver().findElement(By.xpath(SUBMIT_BUTTON))
                .click();

        WebElement applyButton = getDriver().findElement(By.xpath("//button[@name = 'Apply']"));
        applyButton.click();

        WebElement jenkinsLogo = getDriver().findElement(By.xpath("//div/a[@id = 'jenkins-home-link']"));
        jenkinsLogo.click();

        getDriver().findElement(By.xpath(CREATE_BUTTON))
                .click();

        getDriver().findElement(By.xpath(INPUT_FIELD))
                .sendKeys("Folder");

        WebElement validationMessage = getDriver().findElement(By.xpath("//div[@id = 'itemname-invalid']"));

        Assert.assertEquals(validationMessage.getText(), "» A job already exists with the name ‘Folder’");
    }
}
