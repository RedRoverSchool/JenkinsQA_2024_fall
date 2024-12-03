package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


public class AddJenkinsPipelineTest extends BaseTest {

    @Test
    public void testCreateMultiConfigProject() {
         getDriver().findElement(By.linkText("New Item")).click();

         getDriver().findElement(By.id("name")).sendKeys("Multi project");
         getDriver().findElement(By.cssSelector("#jenkins")).click();
         getDriver().findElement(By.id("ok-button")).click();

         getDriver().findElement(By.name("description")).sendKeys("My multi project");

         getDriver().findElement(By.xpath("//span[@class='jenkins-checkbox']//label[text()= 'GitHub project'] ")).click();

         WebElement inputElement = getDriver().findElement(By.name("_.projectUrlStr"));
         Actions actions = new Actions(getDriver());
         actions.moveToElement(inputElement).click().perform();
         inputElement.sendKeys("https://github.com/dema28/DenisNovicov/");

         JavascriptExecutor js = (JavascriptExecutor) getDriver();
         js.executeScript("window.scrollBy(0, 1000);");

         getDriver().findElement(By.xpath("//*[@id='bottom-sticker']/div/button[1]")).click();

         WebElement nameLink = getDriver().findElement(By.xpath("//*[@id='main-panel']/div[1]/div[1]/h1"));
         String actualText = nameLink.getText();

         WebElement linkGitHub = getDriver().findElement(By.cssSelector("#tasks > div:nth-child(7) > span > a"));
         linkGitHub.click();
         String actualUrl = getDriver().getCurrentUrl();

         Assert.assertEquals(actualText, "Multi project");
         Assert.assertEquals(actualUrl, "https://github.com/dema28/DenisNovicov/", "The link conversion did not lead to the expected URL.");
    }
}
