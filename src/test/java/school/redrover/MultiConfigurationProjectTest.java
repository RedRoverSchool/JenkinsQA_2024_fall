package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.page.HomePage;
import school.redrover.page.MultiConfigurationConfigPage;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class MultiConfigurationProjectTest extends BaseTest {
    private static final String NAME_OF_PROJECT = "Multi-configuration project";
    private static final String DESCRIPTIONS = "Descriptions of project";

    private void waitTimeUntilVisibilityElement(Integer time, WebElement element) {
        new WebDriverWait(getDriver(), Duration.ofSeconds(time)).until(ExpectedConditions.visibilityOf(element));
    }

    private void createMultiConfigProject() {
        getDriver().findElement(By.cssSelector("[href$='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys("MultiConfigProject");
        getDriver().findElement(By.xpath("//span[text()='Multi-configuration project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test(description = "Create project without descriptions")
    public void testCreateProjectWithoutDescription() {
        List<String> itemList = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(NAME_OF_PROJECT)
                .selectMultiConfigurationAndClickOk()
                .gotoHomePage()
                .getItemList();

        Assert.assertEquals(itemList.size(), 1);
        Assert.assertEquals(itemList.get(0), NAME_OF_PROJECT);
    }

    @Test(description = " MultiConfigurationProjectTest | Add descriptions to existing project")
    public void testAddDescriptions() {
        testCreateProjectWithoutDescription();
        getDriver().findElement(By.xpath("//td/a[@href='job/Multi-configuration%20project/']")).click();
        getDriver().findElement(By.xpath("//a[@id='description-link']")).click();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(DESCRIPTIONS);
        getDriver().findElement(By.xpath("//div/button[@name = 'Submit']")).submit();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(), DESCRIPTIONS);

    }

    @Test
    public void testCreateProjectWithoutName() {
        final String errorMessage = "This field cannot be empty";

        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        Assert.assertTrue(getDriver().findElement(By.id("itemname-required")).getText().contains(errorMessage));
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled());
    }


    @Test
    public void testSelectTimePeriodThrottleBuilds() {
        MultiConfigurationConfigPage multiConfigPage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(NAME_OF_PROJECT)
                .selectMultiConfigurationAndClickOk()
                .clickThrottleBuildsCheckbox();

        List<String> selectItems = multiConfigPage.getAllDurationItemsFromSelect();

        for (String item : selectItems) {
            multiConfigPage.selectDurationItem(item);

            Assert.assertEquals(multiConfigPage.getSelectedDurationItemText(), item);
        }
    }

    @Test(dependsOnMethods = "testCreateProjectWithoutDescription")
    public void testCreateWithExistingName() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .enterItemName(NAME_OF_PROJECT)
                .selectFreestyleProject()
                .getErrorMessage();

        Assert.assertEquals(errorMessage, "» A job already exists with the name ‘%s’".formatted(NAME_OF_PROJECT));
    }
}