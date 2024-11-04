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
        return new WebDriverWait(getDriver(), Duration.ofSeconds(12));
    }

    @Test
    public void testNewItem() {
        WebDriverWait wait = setWait();

        WebElement dashboardButton = getDriver().findElement(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#breadcrumbs > li.jenkins-breadcrumbs__list-item > a > button")));
        buttonDropdown.click();

        //проверяю что спиннер уже пропал чтобы перейти к видимости дропдауна
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("#tippy-3 > div > div > p")));

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector("#tippy-3 > div > div > div > a")));
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement newItem = dropDownList.get(0);
        newItem.click();
    }
}