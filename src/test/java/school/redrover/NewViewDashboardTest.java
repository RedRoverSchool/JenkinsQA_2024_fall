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
    private enum ViewType {
        ListView("ListView"),
        MyView("MyView");
        private final String viewName;
        ViewType(String viewName) {
            this.viewName = viewName;
        }
        public String getViewName() {
            return viewName;
        }
    }

    private static final String PROJECT_NAME = "New Freestyle Project";

    @Test
    public void testAddNewMyView() {
        addProjectOnDashboard(PROJECT_NAME);
        goToDashboard();
        addView(ViewType.MyView);

        List<WebElement> listOfViews = getDriver().findElements(By.xpath("//div[@class = 'tabBar']//a"));
        Assert.assertTrue(listOfViews.stream().anyMatch(item -> ViewType.MyView.getViewName().equals(item.getText())));
    }

    @Test
    public void testDeleteView() {
        addProjectOnDashboard(PROJECT_NAME);
        goToDashboard();
        addView(ViewType.MyView);


        getDriver().findElement(By.xpath("//a[@class= 'task-link  confirmation-link']")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//dialog[@class = 'jenkins-dialog']")));
        getDriver().findElement(By.xpath("//button[@data-id = 'ok']")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'tabBar']")));
        List<WebElement> listOfViews = getDriver().findElements(By.xpath("//div[@class = 'tabBar']//a"));

        Assert.assertTrue(listOfViews.stream().anyMatch(item -> !ViewType.MyView.getViewName().equals(item.getText())));
    }

    private void addProjectOnDashboard(String nameProject) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(nameProject);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement submitButton = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit")));
        submitButton.click();
    }

    private void goToDashboard() {
        getDriver().findElement(By.xpath("//a[text() = 'Dashboard']")).click();
    }

    private void addView(ViewType nameView) {
        getDriver().findElement(By.linkText("My Views")).click();
        getDriver().findElement(By.xpath("//a[@class = 'addTab']")).click();

        getDriver().findElement(By.id("name")).sendKeys(nameView.getViewName());

        selectViewType(nameView);

        getDriver().findElement(By.xpath("//button[@id = 'ok']")).click();
    }

    private void selectViewType(ViewType viewType) {
        WebElement button  = getDriver().findElement(By.xpath("//input[@id = 'hudson.model." + viewType + "']"));
        boolean selectState = button.isSelected();
        if(selectState == false) {
            new Actions(getDriver()).moveToElement(button).click().build().perform();
        }
    }
}
