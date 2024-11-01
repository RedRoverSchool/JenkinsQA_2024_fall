package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class CreateMultiConfigurationProjectTest extends BaseTest {

    @Test
    public void testCreateMultiConfigurationProject() {
        WebElement newItemButton = getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a"));
        Assert.assertNotNull(newItemButton, "new item didn't found");
        newItemButton.click();

        WebElement newItemText = getDriver().findElement(By.cssSelector("#add-item-panel > h1:nth-child(1)"));
        Assert.assertEquals(newItemText.getText(), "New Item", "New item page didn't open");

        WebElement inputPanel = getDriver().findElement(By.id("name"));
        Assert.assertNotNull(inputPanel, "Input panel didn't found");
        inputPanel.sendKeys("Multi Configuration Test Project");

        WebElement multiConfigurationProjectRadioButton = getDriver().findElement(By.cssSelector(".hudson_matrix_MatrixProject"));
        Assert.assertNotNull(multiConfigurationProjectRadioButton, "Not found");
        multiConfigurationProjectRadioButton.click();

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        Assert.assertNotNull(okButton, "Not found");
        okButton.click();

        WebElement saveButton = getDriver().findElement(By.cssSelector(".jenkins-submit-button"));
        Assert.assertNotNull(saveButton, "Not found");
        saveButton.click();

        WebElement jenkinsLogoButton = getDriver().findElement(By.id("jenkins-name-icon"));
        Assert.assertNotNull(jenkinsLogoButton, "Not found");
        jenkinsLogoButton.click();

        String dashbordItemText = getDriver().findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/table/tbody/tr/td[3]/a/span[1]")).getText();
        Assert.assertEquals(dashbordItemText, "Multi Configuration Test Project", "Project didn't create");
    }
}
