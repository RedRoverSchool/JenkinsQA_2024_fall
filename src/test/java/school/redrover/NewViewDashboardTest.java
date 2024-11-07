package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class NewViewDashboardTest extends BaseTest {
    @Test
    public void testAddNewMyView() throws InterruptedException {
        final String freestyleProjectName = "New Freestyle Project";
        final String myView = "My View";

        List<WebElement> sideBarElements = getDriver().findElements(By.xpath("//span[@class = 'task-link-wrapper ']//a"));
        for (WebElement element : sideBarElements) {
            if(element.getText().contains("New Item")) {
                element.click();
                break;
            }
        }
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(freestyleProjectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(2000));
        WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit")));
        submitButton.click();
        getDriver().findElement(By.xpath("//a[text() = 'Dashboard']")).click();

        getDriver().findElement(By.linkText("My Views")).click();
        getDriver().findElement(By.xpath("//a[@class = 'addTab']")).click();

        getDriver().findElement(By.id("name")).sendKeys(myView);

        WebElement button  = getDriver().findElement(By.xpath("//input[@id = 'hudson.model.MyView']"));
        new Actions(getDriver()).moveToElement(button).click().build().perform();

        getDriver().findElement(By.xpath("//button[@id = 'ok']")).click();

        List<WebElement> listOfViews = getDriver().findElements(By.xpath("//div[@class = 'tabBar']//a"));
        Assert.assertTrue(listOfViews.stream().anyMatch(item -> myView.equals(item.getText())));
    }
}
