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

import org.openqa.selenium.JavascriptExecutor;

public class DashboardItemsDropdownTest extends BaseTest {

    private WebDriverWait setWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(30));
    }

    private void clickUseJS(WebElement element) {
        JavascriptExecutor clickJS = (JavascriptExecutor) getDriver();
        clickJS.executeScript("arguments[0].click();", element);
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
        clickUseJS(buttonDropdown);

        wait.until(ExpectedConditions.attributeToBe(buttonDropdown, "aria-expanded", "true"));

        List<WebElement> listDD = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));

        WebElement newItem = listDD.get(0);
        actions.moveToElement(newItem).click().perform();
   }
}