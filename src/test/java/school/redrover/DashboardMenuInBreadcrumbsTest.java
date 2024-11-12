package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import javax.swing.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashboardMenuInBreadcrumbsTest extends BaseTest {

    @Test
    public void testDashboardDropdownMenu() throws InterruptedException {

        Actions actions = new Actions(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMinutes(5));

        ProjectUtils.log("Find 'Dashboard' element");
        WebElement dashboard = getDriver().findElement(By.xpath("//ol/li/a[@href='/']"));

        ProjectUtils.log("Hover over 'Dashboard' in breadcrumbs");
        actions
                .moveToElement(dashboard)
                .perform();

        ProjectUtils.log("Wait animation of the dropdown chevron icon and click");
        WebElement dropdownIcon = getDriver().findElement(By.xpath("//ol/li/a[@href='/']/button[@class='jenkins-menu-dropdown-chevron']"));
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String initialCssValue = dropdownIcon.getCssValue("right");
                ProjectUtils.log(initialCssValue);
                return "-22px".equals(initialCssValue);
            }
        });
        actions
                .pause(Duration.ofSeconds(3))
                .moveToElement(dropdownIcon)
                .pause(Duration.ofSeconds(3))
                .click()
                .perform();

        ProjectUtils.log("Wait animation of dropdown menu");
        WebElement parentOfDropdownMenu = getDriver().findElement(By.xpath("//div[@class='tippy-content']"));
        WebElement nestedDropdownMenu = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parentOfDropdownMenu, By.className("jenkins-dropdown")));

        ProjectUtils.log("Create list of dropdown elements");
        List<WebElement> dashboardDropdownMenuElements = getDriver().findElements(By.xpath("//a[@class='jenkins-dropdown__item ']"));
        List<String> actualElements = new ArrayList<>();
        for (WebElement eachElement: dashboardDropdownMenuElements) {
            actualElements.add(eachElement.getText());
        }
        System.out.println("actual: " + actualElements);

        ProjectUtils.log("Compare actual vs expected lists of dropdown elements");
        List<String> expectedElements = List.of("New Item", "Build History", "Manage Jenkins", "My Views");
        Assert.assertEquals(actualElements, expectedElements);
    }
}
