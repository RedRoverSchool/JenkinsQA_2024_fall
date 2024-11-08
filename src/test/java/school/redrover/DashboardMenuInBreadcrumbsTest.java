package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashboardMenuInBreadcrumbsTest extends BaseTest {

    @Test
    public void testDashboardDropdownMenu() throws InterruptedException {

        Actions actions = new Actions(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        ProjectUtils.log("Find 'Dashboard' element in breadcrumbs");
        WebElement dashboardInBreadcrumbs = getDriver().findElement(By.xpath("//li/a[@href='/']"));

        ProjectUtils.log("Hover over 'Dashboard' in breadcrumbs");
        actions.moveToElement(dashboardInBreadcrumbs).perform();

        ProjectUtils.log("Click on the dropdown icon");
        WebElement dropdownIcon = getDriver().findElement(By.xpath("//a[@href='/']/button[@class='jenkins-menu-dropdown-chevron']"));

        wait.until(ExpectedConditions.elementToBeClickable(dropdownIcon));
        /*
        wait for CSS property "right" to change, i.e. wait for the animation to finish
        */
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                String right = dropdownIcon.getCssValue("right");
                System.out.println("Меняющееся CSS свойство right: " + right);
                return "-22px".equals(right);
            }
        });
        dropdownIcon.click();

        ProjectUtils.log("Create list of dropdown elements");
        List<WebElement> dashboardDropdownMenuElements = getDriver().findElements(By.xpath("//*[@data-placement='bottom-start']/div/div/a[@class='jenkins-dropdown__item ']"));
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
