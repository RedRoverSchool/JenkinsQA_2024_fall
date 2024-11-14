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

    private static final String PROJECT_NAME = "New Freestyle Project";
    private static final String MY_VIEW_NAME = "My View";

    @Test
    public void testAddNewMyView() {

        addProjectOnDashboard(PROJECT_NAME);
        goToDashboard();
        addView(MY_VIEW_NAME);

        List<WebElement> listOfViews = getDriver().findElements(By.xpath("//div[@class = 'tabBar']//a"));
        Assert.assertTrue(listOfViews.stream().anyMatch(item -> MY_VIEW_NAME.equals(item.getText())));
    }

    @Test
    public void testDeleteView() {
        addProjectOnDashboard(PROJECT_NAME);
        goToDashboard();
        addView(MY_VIEW_NAME);

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(5000));

        getDriver().findElement(By.xpath("//a[@class= 'task-link  confirmation-link']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//dialog[@class = 'jenkins-dialog']")));
        getDriver().findElement(By.xpath("//button[@data-id = 'ok']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'tabBar']")));
        List<WebElement> listOfViews = getDriver().findElements(By.xpath("//div[@class = 'tabBar']//a"));

        Assert.assertTrue(listOfViews.stream().anyMatch(item -> !MY_VIEW_NAME.equals(item.getText())));

    }

    private void addProjectOnDashboard(String nameProject) {
        List<WebElement> sideBarElements = getDriver().findElements(By.xpath("//span[@class = 'task-link-wrapper ']//a"));
        for (WebElement element : sideBarElements) {
            if(element.getText().contains("New Item")) {
                element.click();
                break;
            }
        }
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(nameProject);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMillis(6000));
        WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit")));
        submitButton.click();
    }

    private void goToDashboard() {
        getDriver().findElement(By.xpath("//a[text() = 'Dashboard']")).click();
    }

    private void addView(String nameView) {
        getDriver().findElement(By.linkText("My Views")).click();
        getDriver().findElement(By.xpath("//a[@class = 'addTab']")).click();

        getDriver().findElement(By.id("name")).sendKeys(nameView);

        WebElement button  = getDriver().findElement(By.xpath("//input[@id = 'hudson.model.MyView']"));
        new Actions(getDriver()).moveToElement(button).click().build().perform();

        getDriver().findElement(By.xpath("//button[@id = 'ok']")).click();
    }

}
