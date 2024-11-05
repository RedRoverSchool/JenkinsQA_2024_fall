package school.redrover;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import school.redrover.runner.BaseTest;

import java.time.Duration;

public class DropDownTest extends BaseTest {

    private static final String HEADER_DROPDOWN = "//div/a/button[@class = 'jenkins-menu-dropdown-chevron']";
    private static final String BREAD_CRUMBS = "//ol/li[@class = 'jenkins-breadcrumbs__list-item'] [3]";

    @Test
    public void testBuild() {

        WebElement dropdownChevron = getDriver().findElement(By.xpath(HEADER_DROPDOWN));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dropdownChevron).click().perform();

        WebElement buildButton = getDriver().findElement(By.xpath("//div/a[@class = 'jenkins-dropdown__item '] [1]"));
        WebDriverWait w = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        w.until(ExpectedConditions.visibilityOfAllElements(buildButton));
        buildButton.click();

        WebElement thirdCrumb = getDriver().findElement(By.xpath(BREAD_CRUMBS));

        Assert.assertEquals(thirdCrumb.getText(), "Builds");
    }

    @Test
    public void testConfigure() {

        WebElement dropdownChevron = getDriver().findElement(By.xpath(HEADER_DROPDOWN));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dropdownChevron).click().perform();

        WebElement configureButton = getDriver().findElement(By.xpath("//div/a[@class = 'jenkins-dropdown__item '] [2]"));
        WebDriverWait w = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        w.until(ExpectedConditions.visibilityOfAllElements(configureButton));
        configureButton.click();

        WebElement thirdCrumb = getDriver().findElement(By.xpath(BREAD_CRUMBS));

        Assert.assertEquals(thirdCrumb.getText(), "Configure");
    }
}