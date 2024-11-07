package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class MultiConfigurationProjectTest extends BaseTest {
    private static final String NAME_OF_PROJECT = " project";
    private static final String DESCRIPTIONS = "Descriptions of project";

    public WebDriverWait wait2() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(2));
    }

    @Test(description = "Create project without descriptions")
    public void testCreateProjectWithoutDescription() {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();
        WebElement itemName = getDriver().findElement(By.xpath("//input[@id='name']"));
        wait2().until(ExpectedConditions.visibilityOf(itemName));
        itemName.sendKeys("Multi-configuration" + NAME_OF_PROJECT);
        getDriver().findElement(By.xpath("//span[text()='Multi-configuration project']")).click();
        getDriver().findElement(By.xpath("//button[@id = 'ok-button']")).click();
        getDriver().findElement(By.xpath("//a[@id='jenkins-home-link']")).click();

        List<WebElement> itemList = getDriver().findElements
                (By.xpath("//a[@class = 'jenkins-table__link model-link inside'] /span"));
        List<String> itemListMap = itemList.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(itemListMap.contains("Multi-configuration project"));
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

        String actualErrorMessage = getDriver().findElement(By.id("itemname-required")).getText();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        WebElement okButton = getDriver().findElement(By.id("ok-button"));

        Assert.assertTrue(actualErrorMessage.contains(errorMessage));
        Assert.assertFalse(okButton.isEnabled());
    }

}
