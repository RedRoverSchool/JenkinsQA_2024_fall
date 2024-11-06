package school.redrover;

import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import school.redrover.runner.BaseTest;

public class DropDownTest extends BaseTest {

    private static final String HEADER_DROPDOWN = "//div/a/button[@class = 'jenkins-menu-dropdown-chevron']";
    private static final String BREAD_CRUMBS = "//ol/li[@class = 'jenkins-breadcrumbs__list-item'] [3]";

    @Test
    public void testBuild() throws InterruptedException {

        WebElement dropdownChevron = getDriver().findElement(By.xpath(HEADER_DROPDOWN));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dropdownChevron).click().perform();

        Thread.sleep(5000);

        WebElement buildButton = getDriver().findElement(By.xpath("//*[@class = 'jenkins-dropdown']/a[1]"));
        actions.moveToElement(buildButton).click().perform();

        WebElement thirdCrumb = getDriver().findElement(By.xpath(BREAD_CRUMBS));

        Assert.assertEquals(thirdCrumb.getText(), "Builds");
    }

    @Test
    public void testConfigure() throws InterruptedException {

        WebElement dropdownChevron = getDriver().findElement(By.xpath(HEADER_DROPDOWN));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dropdownChevron).click().perform();

        Thread.sleep(5000);

        WebElement configureButton = getDriver().findElement(By.xpath("//*[@class = 'jenkins-dropdown']/a[2]"));
        configureButton.click();

        WebElement thirdCrumb = getDriver().findElement(By.xpath(BREAD_CRUMBS));

        Assert.assertEquals(thirdCrumb.getText(), "Configure");
    }
}