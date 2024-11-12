package school.redrover;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
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
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashboardMenuInBreadcrumbsTest extends BaseTest {
    // Метод для создания скриншотов
    public static void takeScreenshot(WebDriver driver, String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDashboardDropdownMenu() throws InterruptedException {

        Actions actions = new Actions(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofMinutes(5));

        ProjectUtils.log("Find 'Dashboard' element");
        WebElement dashboard = getDriver().findElement(By.xpath("//ol/li/a[@href='/']"));
        WebElement dropdownIcon = getDriver().findElement(By.xpath("//ol/li/a[@href='/']/button[@class='jenkins-menu-dropdown-chevron']"));

        ProjectUtils.log("Chevron size: " + dropdownIcon.getSize());
        ProjectUtils.log("Top left chevron corner location before hovering: " + dropdownIcon.getLocation().toString());
        ProjectUtils.log("Css value before hovering: " + dropdownIcon.getCssValue("right"));

        ProjectUtils.log("Hover over 'Dashboard' in breadcrumbs");
        actions
                .moveToElement(dashboard)
                .perform();

        ProjectUtils.log("Wait animation of the dropdown chevron icon");
        ProjectUtils.log("Top left chevron corner location during animation: " + dropdownIcon.getLocation().toString());
        ProjectUtils.log("Css value during animation: " + dropdownIcon.getCssValue("right"));

        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String initialPositionCssValue = dropdownIcon.getCssValue("right");
                ProjectUtils.log("Css value during animation: " + initialPositionCssValue);
                return "-22px".equals(initialPositionCssValue);
            }
        });

//        wait.until(ExpectedConditions.visibilityOf(dropdownIcon));
//        wait.until(ExpectedConditions.elementToBeClickable(dropdownIcon));

        ProjectUtils.log("Chevron size: " + dropdownIcon.getSize());
        ProjectUtils.log("Top left chevron corner location after animation: " + dropdownIcon.getLocation().toString());
        ProjectUtils.log("Css value after animation: " + dropdownIcon.getCssValue("right"));

        ProjectUtils.log("Click on the dropdown chevron icon");
        dropdownIcon.click();

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
