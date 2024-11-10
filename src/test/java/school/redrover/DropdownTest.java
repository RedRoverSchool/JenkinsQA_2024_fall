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

public class DropdownTest extends BaseTest {

    private static final String HEADER_DROPDOWN = "//div/a/button[@class = 'jenkins-menu-dropdown-chevron']";
    private static final String BREAD_CRUMBS = "//ol/li[@class = 'jenkins-breadcrumbs__list-item'] [3]";

    @Test
    public void testBuild() {

        WebElement dropdownChevron = getDriver().findElement(By.xpath(HEADER_DROPDOWN));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dropdownChevron).click().perform();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));

        WebElement content = wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//div[@class = 'tippy-content']")));

        actions.moveToElement(content);

        WebElement buildButton = getDriver().findElement(By.xpath("//*[@class = 'jenkins-dropdown']/a[1]"));
        actions.moveToElement(buildButton)
                .moveByOffset(-5, 0)
                .click()
                .perform();

        WebElement thirdCrumb = getDriver().findElement(By.xpath(BREAD_CRUMBS));

        Assert.assertEquals(thirdCrumb.getText(), "Builds");
    }

    @Test
    public void testConfigure() {

        WebElement dropdownChevron = getDriver().findElement(By.xpath(HEADER_DROPDOWN));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dropdownChevron).click().perform();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));

        WebElement content = wait.until(ExpectedConditions
                .visibilityOfElementLocated(
                        By.xpath("//div[@class = 'tippy-content']")));

        actions.moveToElement(content);

        WebElement configureButton = getDriver().findElement(By.xpath("//*[@class = 'jenkins-dropdown']/a[2]"));
        actions.moveToElement(configureButton)
                .moveByOffset(-5, 0)
                .click()
                .perform();

        WebElement thirdCrumb = getDriver().findElement(By.xpath(BREAD_CRUMBS));

        Assert.assertEquals(thirdCrumb.getText(), "Configure");
    }

}
