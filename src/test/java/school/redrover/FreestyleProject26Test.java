package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.util.List;

public class FreestyleProject26Test extends BaseTest {

    private static final String PROJECT_NAME = "New Freestyle Project";
    @Test
    public void testCreateProject() {

        addProjectOnDashboard(PROJECT_NAME);

        String getTitle = getDriver().findElement(By.xpath("//h1")).getText();
        Assert.assertEquals(getTitle, PROJECT_NAME);
    }

    @Test
    public void testDeleteWithChevron() {
        addProjectOnDashboard(PROJECT_NAME);
        goToDashboard();

        WebElement linkByName = getDriver().findElement(By.linkText(PROJECT_NAME));
        new Actions(getDriver()).moveToElement(linkByName).build().perform();

        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='%s']/following-sibling::button".formatted(PROJECT_NAME))));

        TestUtils.moveAndClickWithJavaScript(getDriver(), chevronButton);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(@href, 'doDelete')]"))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text() =  'Yes']"))).click();

        List<WebElement> remainingItems = getDriver().findElements(By.xpath("//a[@class = 'jenkins-table__link model-link inside']/span"));
        for (WebElement item : remainingItems) {
            Assert.assertNotEquals(item.getText(), PROJECT_NAME);
        }
    }
    private void addProjectOnDashboard(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.xpath("//input[@id = 'name']")).sendKeys(projectName);
        getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement submitButton = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit")));
        submitButton.click();
    }
    private void goToDashboard() {
        getDriver().findElement(By.xpath("//a[text() = 'Dashboard']")).click();
    }
}

