package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.time.Duration;

public class ChevronTest extends BaseTest {

    @Test
    public void testDropdownChevron() {

        Actions actions = new Actions(getDriver());

        WebElement dashboard = getDriver().findElement(By.xpath("//li/a[@href='/']"));
        WebElement chevronDashboard = getDriver().findElement(By.xpath("//li/a/button[@class='jenkins-menu-dropdown-chevron']"));
        ProjectUtils.log(chevronDashboard.getCssValue("opacity"));
        ProjectUtils.log(chevronDashboard.getLocation().toString());

//        actions
//                .moveToElement(dashboard)
//                .perform();

//        WebElement chevronDashboard = getDriver().findElement(By.xpath("//li/a/button[@class='jenkins-menu-dropdown-chevron']"));
        ProjectUtils.log(chevronDashboard.getCssValue("opacity"));
        ProjectUtils.log(chevronDashboard.getLocation().toString());
        actions
                .moveToElement(chevronDashboard)
                .click()
                .perform();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(
                By.xpath("//div[@class='tippy-content']"),
                By.xpath("//div[@class='tippy-content']/div")));

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div/a[@href='/view/all/builds']")).getText(),
                "Build History");

        ProjectUtils.log(getDriver().findElement(By.xpath("//div/a[@href='/view/all/builds']")).getText());

    }

}
