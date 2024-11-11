package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

public class DropdownTest extends BaseTest {

    private static final String HEADER_DROPDOWN = "//div/a/button[@class = 'jenkins-menu-dropdown-chevron']";
    private static final String BREAD_CRUMBS = "//ol/li[@class = 'jenkins-breadcrumbs__list-item'] [3]";

    @Test
    public void testBuild() {

        WebElement dropdownChevron = getDriver().findElement(By.xpath(HEADER_DROPDOWN));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dropdownChevron).click().perform();

        WebElement buildButton = getDriver().findElement(By.xpath("//a[normalize-space()='Builds']"));
        actions.moveToElement(buildButton)
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

        WebElement configureButton = getDriver().findElement(By.xpath("//a[normalize-space()='Configure']"));
        actions.moveToElement(configureButton)
                .click()
                .perform();

        WebElement thirdCrumb = getDriver().findElement(By.xpath(BREAD_CRUMBS));

        Assert.assertEquals(thirdCrumb.getText(), "Configure");
    }

}
