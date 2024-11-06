package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class DashboardItemsDropdownTest extends BaseTest {

    private WebDriverWait setWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(20));
    }

    @Test
    public void testNewItem() {
        WebDriverWait wait = setWait();
        Actions actions = new Actions(getDriver());

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        actions.moveToElement(buttonDropdown).click().perform();

        wait.until(ExpectedConditions.attributeToBe(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a "),
                "class",
                "model-link model-link--open"));

        List<WebElement> dropDownList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement newItem = dropDownList.get(0);
        actions.moveToElement(newItem).click().perform();
   }
}