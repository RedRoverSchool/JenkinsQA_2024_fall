package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProject2Test extends BaseTest {

    @Test
    public void testCreateWithSpecialSymbols() {
        String[] specialCharacters = {"!", "%", "&", "#", "@", "*", "$", "?", "^", "|", "/", "]", "["};

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        WebElement nameField = getDriver().findElement(By.xpath("//input[@name='name']"));

        for (String specChar: specialCharacters) {
            nameField.clear();
            nameField.sendKeys("Free" + specChar + "styles");

            WebElement actualMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.
                    xpath("//div[@id='itemname-invalid']")));

            String expectMessage = "» ‘" + specChar + "’ is an unsafe character";
            Assert.assertEquals(actualMessage.getText(), expectMessage, "Message is not displayed");
        }
    }

}
