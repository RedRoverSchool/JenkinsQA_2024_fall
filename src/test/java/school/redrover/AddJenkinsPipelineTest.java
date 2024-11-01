package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class AddJenkinsPipelineTest extends BaseTest {

    @Test
    public void createMultiConfigProject() {

         getDriver().findElement(By.linkText("New Item")).click();

         getDriver().findElement(By.id("name")).sendKeys("Multi project");
         getDriver().findElement(By.cssSelector("#jenkins")).click();
         getDriver().findElement(By.id("ok-button")).click();

         getDriver().findElement(By.name("description")).sendKeys("My multi project");

         getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[9]/div[1]/div/span/label")).click();
         getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[1]/div[9]/div[3]/div[1]/div[2]/input"))
                 .sendKeys("https://github.com/dema28/DenisNovicov.git");

         JavascriptExecutor js = (JavascriptExecutor) getDriver();
         js.executeScript("window.scrollBy(0, 1000);");

         getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

         WebElement nameLink = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1"));
         String actualText = nameLink.getText();
         String expectedText = "Multi project";
         Assert.assertEquals(expectedText, actualText);

         WebElement linkGitHub = getDriver().findElement(By.cssSelector("#tasks > div:nth-child(7) > span > a"));
         linkGitHub.click();
         String actualUrl = getDriver().getCurrentUrl();
         String expectedUrl = "https://github.com/dema28/DenisNovicov/";
         Assert.assertEquals(actualUrl,expectedUrl, "The link conversion did not lead to the expected URL.");
    }
}
