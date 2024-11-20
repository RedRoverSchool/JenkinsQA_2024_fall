package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.Random;
import java.util.stream.IntStream;

public class CreateFreestyleProjectNTest extends BaseTest {

    @Test
    public void testCreateFreestyleProject() {

        String projectName = generateRandomString(5);
        String descriptionText = generateRandomString(10);

        clickElement("//a[@href='/view/all/newJob']");
        clickElement("//input[@class='jenkins-input']");
        enterText("//input[@class='jenkins-input']", projectName);
        clickElement("//li[@class='hudson_model_FreeStyleProject']");
        clickElement("//button[@id='ok-button']");
        clickElement("//textarea[@name='description']");
        enterText("//textarea[@name='description']", descriptionText);
        clickElement("//button[@name='Submit']");

        WebElement resultName = getDriver()
                .findElement(By.xpath("//h1[@class='job-index-headline page-headline']"));

        Assert.assertEquals(resultName.getText(), projectName, "Project name does not match");

    }
    private void clickElement(String xpath) {
        getDriver().findElement(By.xpath(xpath)).click();
    }

    private void enterText(String xpath, String text) {
        getDriver().findElement(By.xpath(xpath)).sendKeys(text);
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(length);
        IntStream.rangeClosed(0, length).forEach(i-> {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        });
        return randomString.toString();
    }
}
