package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

public class ChevronTest extends BaseTest {

    @Test
    public void testDropdownChevron() {

        Actions actions = new Actions(getDriver());

        WebElement dashboard = getDriver().findElement(By.xpath("//li/a[@href='/']"));
        WebElement chevronDashboard = getDriver().findElement(By.xpath("//li/a/button[@class='jenkins-menu-dropdown-chevron']"));
        ProjectUtils.log(chevronDashboard.getCssValue("opacity"));
        ProjectUtils.log(chevronDashboard.getLocation().toString());

        actions
                .moveToElement(dashboard)
                .perform();

//        WebElement chevronDashboard = getDriver().findElement(By.xpath("//li/a/button[@class='jenkins-menu-dropdown-chevron']"));
        ProjectUtils.log(chevronDashboard.getCssValue("opacity"));
        ProjectUtils.log(chevronDashboard.getLocation().toString());
        chevronDashboard.click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/a[@href='/view/all/builds']")).getText(),
                "Build History");

        ProjectUtils.log(getDriver().findElement(By.xpath("//div/a[@href='/view/all/builds']")).getText());

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String browserVersion = (String) js.executeScript("return navigator.userAgent;");
        ProjectUtils.log(browserVersion);

    }

}
