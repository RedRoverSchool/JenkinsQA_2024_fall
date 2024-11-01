package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreatingNewItemTest extends BaseTest {

    @Test
    public void testCreateFreestyleProject() throws InterruptedException {
        WebElement newItemButton = getDriver().findElement(By.cssSelector("[href='/view/all/newJob']"));
        newItemButton.click();

        WebElement inputItemNameField = getDriver().findElement(By.className("jenkins-input"));
        inputItemNameField.sendKeys("Some name of Freestyle project");

        WebElement freestyleProjectButton = getDriver().findElement(By.cssSelector(".hudson_model_FreeStyleProject"));
        freestyleProjectButton.click();

        WebElement okButton = getDriver().findElement(By.cssSelector("#ok-button"));
        okButton.click();

        WebElement dashboardButtonInBreadcrumbs = getDriver().findElement(By.xpath("//a[@class='model-link' and text()='Dashboard']"));
        dashboardButtonInBreadcrumbs.click();

        String jobNameText = getDriver().findElement(By.xpath("//tr/td/a/span")).getText();

        Assert.assertEquals(jobNameText, "Some name of Freestyle project");
    }
}
