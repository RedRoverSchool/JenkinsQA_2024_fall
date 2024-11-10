package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        ProjectUtils.log("Find 'Dashboard' element");
        WebElement dashboard = getDriver().findElement(By.xpath("//li[@class='jenkins-breadcrumbs__list-item']"));
        System.out.println(dashboard.getSize());

        ProjectUtils.log("Hover over 'Dashboard' in breadcrumbs, move to chevron and click");
        actions
                .moveToElement(dashboard)
                .pause(2000)
                .perform();
        System.out.println(dashboard.getSize());

        ProjectUtils.log("Click on the dropdown icon");
        WebElement dropdownIcon = getDriver().findElement(By.xpath("//ol/li/a[@href='/']/button[@class='jenkins-menu-dropdown-chevron']"));
        actions
                .moveToElement(dropdownIcon)
                .pause(Duration.ofSeconds(3))
                .click()
                .pause(Duration.ofSeconds(5))
                .perform();

//        try {
//            // JavaScript для визуализации нажатия
//            String script = "function visualizeClick(x, y) {" +
//                    "var indicator = document.createElement('div');" +
//                    "indicator.style.position = 'absolute';" +
//                    "indicator.style.width = '5px';" +
//                    "indicator.style.height = '5px';" +
//                    "indicator.style.backgroundColor = 'red';" +
//                    "indicator.style.borderRadius = '150%';" +
//                    "indicator.style.left = x + 'px';" +
//                    "indicator.style.top = y + 'px';" +
//                    "indicator.style.zIndex = 1000;" +
//                    "document.body.appendChild(indicator);" +
//                    "setTimeout(function() { document.body.removeChild(indicator); }, 2000);" +
//                    "}" +
//                    "visualizeClick(100, 79);";
//            // Выполнение JavaScript через Selenium
//            JavascriptExecutor js = (JavascriptExecutor) getDriver();
//            js.executeScript(script);
//            Thread.sleep(10000);
//        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println("warning");;
//        }

        ProjectUtils.log("Create list of dropdown elements");
        List<WebElement> dashboardDropdownMenuElements = getDriver().findElements(By.xpath("//a[@class='jenkins-dropdown__item ']"));
        wait.until(ExpectedConditions.visibilityOfAllElements(dashboardDropdownMenuElements));

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
