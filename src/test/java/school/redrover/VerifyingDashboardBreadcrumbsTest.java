package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class VerifyingDashboardBreadcrumbsTest extends BaseTest {

    @Test
    public void testDashboardDropdownMenu() throws InterruptedException {

        Actions actions = new Actions(getDriver());

        ProjectUtils.log("Find 'Dashboard' element");
        WebElement dashboard = getDriver().findElement(By.xpath("//ol/li/a[@href='/']"));

        Thread.sleep(1000);

        ProjectUtils.log("Hover over 'Dashboard' in breadcrumbs");
        actions.moveToElement(dashboard).perform();

        Thread.sleep(2000);

        ProjectUtils.log("Click on the dropdown icon");
        WebElement dropdownIcon = getDriver().findElement(By.xpath("//ol/li/a[@href='/']/button[@class='jenkins-menu-dropdown-chevron']"));
        dropdownIcon.click();

        Thread.sleep(2000);

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
