package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class WelcomePageTest extends BaseTest {

    @Test
    public void createAJobTest() throws InterruptedException {

        WebElement createAJobField =
                getDriver().findElement(By.cssSelector(
                        "#main-panel > div:nth-child(3) > div > section:nth-child(3) > ul > li > a"));
        createAJobField.click();

        WebElement imputField = getDriver().findElement(By.id("name"));
        imputField.sendKeys("Freestyle project 1");

        Thread.sleep(1000);

        WebElement freestyleProjectField = getDriver().findElement(By.cssSelector("#j-add-item-type-standalone-projects > ul > li.hudson_model_FreeStyleProject"));
        freestyleProjectField.click();

        WebElement okButton = getDriver().findElement(By.cssSelector("#ok-button"));
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.cssSelector(
                "#bottom-sticker > div > button.jenkins-button.jenkins-submit-button.jenkins-button--primary"));
        saveButton.click();

        String freestyleProject1 = getDriver().findElement(By.cssSelector("#main-panel > div.jenkins-app-bar > div.jenkins-app-bar__content.jenkins-build-caption > h1")).getText();
        Assert.assertEquals(freestyleProject1, "Freestyle project 1");

    }
}
