package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class EmptyFieldTest extends BaseTest {

    @Test
    public void testFieldIsEmpty() {

        getDriver().findElement(By.xpath("//div[@class = \"task \"] [1]")).click();

        WebElement submitButton = getDriver().findElement(By.xpath("//button[@type = \"submit\"]"));
        submitButton.click();

        WebElement validationMessage = getDriver().findElement(By.xpath("//div[@class = \"input-validation-message\"]"));

        Assert.assertEquals(validationMessage.getText(),"» This field cannot be empty, please enter a valid name");
    }
}
