package school.redrover;

import org.openqa.selenium.By;
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
    private final static String NAME_OF_PROJECT = " project";

    public WebDriverWait wait2() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(2));
    }

    @Test
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
        List<String> itemListMap =  itemList.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertTrue(itemListMap.contains("Multi-configuration project"));
    }
}
