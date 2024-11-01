package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class ValidationFieldTest extends BaseTest {

    @Test
    public void testFieldIsEmpty() {

        getDriver().findElement(By.xpath("//div[@class = \"task \"] [1]"))
                .click();

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type = \"submit\"]"));
        submitButton.click();

        WebElement validationMessage = getDriver().findElement(By.xpath("//div[@class = \"input-validation-message\"]"));

        Assert.assertEquals(validationMessage.getText(),"» This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testInvalidCharacters() {

       getDriver().findElement(By.xpath("//section[1]/ul/li[1]"))
               .click();

       WebElement inputField = getDriver().findElement(By.xpath("//input[@class = \"jenkins-input\"]"));
       inputField.sendKeys("#!@$!%");

       WebElement invalidMessage = getDriver().findElement(By.xpath("//div[@id = \"itemname-invalid\"]"));

       Assert.assertEquals(invalidMessage.getText(),"» ‘#’ is an unsafe character");
    }

    @Test
    public void testMaxCharacters() {

        getDriver().findElement(By.xpath("//a/span [text() = \"Create a job\"]"))
                .click();

        WebElement inputField = getDriver().findElement(By.xpath("//input[@class = \"jenkins-input\"]"));
        inputField.sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis auctor nisl eu est viverra, " +
                "quis molestie nibh blandit. In lectus felis, iaculis mauris Lorem ipsum dolor sit amet, consectetur adipiscing elit " +
                "est Lorem ipsum dolor sit amet, consectetur adipiscing elit est");

        WebElement folderButton = getDriver().findElement(By.xpath("//ul/li[@class = \"com_cloudbees_hudson_plugins_folder_Folder\"]"));
        folderButton.click();

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type = \"submit\"]"));
        submitButton.click();

        WebElement errorDescription = getDriver().findElement(By.xpath("//div/h2"));

        Assert.assertEquals(errorDescription.getText(),"A problem occurred while processing the request");
    }
}
