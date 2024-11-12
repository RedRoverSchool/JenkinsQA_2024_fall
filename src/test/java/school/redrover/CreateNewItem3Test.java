package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateNewItem3Test extends BaseTest {

    @Test
    public void testCreateNewItemErrorMessage () {


        getDriver().findElement(By.xpath("//a[@it= 'hudson.model.Hudson@ff12418']")).click();
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        WebElement inputField = getDriver().findElement(By.className("jenkins-input"));
        WebElement errorMessageWebElement = getDriver().findElement(By.className("input-validation-message"));

        int inputFieldY = inputField.getLocation().getY();
        int errorMessageY = errorMessageWebElement.getLocation().getY();

        String errorMessage = errorMessageWebElement.getText();

        Assert.assertEquals(errorMessage, "» This field cannot be empty, please enter a valid name");
        Assert.assertTrue(errorMessageY > inputFieldY);

    }

}
