package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.time.Duration;
import java.util.List;

public class DashboardItemsDropdownTest extends BaseTest {

    private WebDriverWait setWait() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        return wait;

    }

    @Test
    public void testNewItem() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonOpen = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button"));
        actions.moveToElement(buttonOpen).click().perform();

        List<WebElement> dropDownVisible = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//*[@id=\"tippy-3\"]/div/div/div")));
        Assert.assertFalse(dropDownVisible.isEmpty(), "Dropdown - empty");

        WebElement listFistSection = dropDownVisible.get(0);
        actions.moveToElement(listFistSection).perform();

        List<WebElement> itemsList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//*[@id=\"tippy-3\"]/div/div/div/a")));
        Assert.assertFalse(itemsList.isEmpty(), "Items not found");

        WebElement newItem = itemsList.get(0);
        newItem.click();
    }
}