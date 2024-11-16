package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class WelcomePageTest extends BaseTest {

    @Test
    public void testCreateAJob() {
        getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(3) > div > section:nth-child(3) > ul > li > a")).click();
        getDriver().findElement(By.id("name")).sendKeys("Freestyle project 1");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#j-add-item-type-standalone-projects > ul > li.hudson_model_FreeStyleProject"))).click();

        getDriver().findElement(By.cssSelector("#ok-button")).click();
        getDriver().findElement(
                By.cssSelector("#bottom-sticker > div > button.jenkins-button.jenkins-submit-button.jenkins-button--primary")).click();

        String freestyleProject1 = getDriver().findElement(
                By.cssSelector("#main-panel > div.jenkins-app-bar > div.jenkins-app-bar__content.jenkins-build-caption > h1")).getText();
        Assert.assertEquals(freestyleProject1, "Freestyle project 1");
    }
}
