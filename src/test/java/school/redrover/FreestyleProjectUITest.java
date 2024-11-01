package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class FreestyleProjectUITest extends BaseTest {

    @Test
    public void freestyleProjectUITest()  {
        getDriver().findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/div[1]/span/a")).click();

        getDriver().findElement(By.id("name")).sendKeys("FreestyleProject");
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("description")).sendKeys(" My Sample Freestyle Project");

        getDriver().findElement(By.xpath("//*[@id='post-build-actions']"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 1000);");

        getDriver().findElement(By.xpath("//button[contains(text(),'Add build step')]")).click();
        getDriver().findElement(By.xpath("//*[@id='tippy-5']/div/div/div/div[2]/button[7]")).click();

        getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

        WebElement projectLink = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1"));
        String actualText = projectLink.getText();
        String expectedText = "FreestyleProject";
        Assert.assertEquals(expectedText, actualText);
    }
}


