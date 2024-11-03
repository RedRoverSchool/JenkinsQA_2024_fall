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
                By.xpath("//*[@id=\"breadcrumbs\"]/li[1]"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(dashboardButton).perform();

        WebElement buttonDropdown = getDriver().findElement(
                By.xpath("//*[@id=\"breadcrumbs\"]/li[1]/a/button"));
        buttonDropdown.click();

        List<WebElement> dropDownList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//*[@id=\"tippy-3\"]/div/div/div")));
        xpath: //*[@id="breadcrumbs"]/li[1]/a/button]
        Assert.assertFalse(dropDownList.isEmpty(), "Dropdown - empty");

        WebElement newItem = dropDownList.get(0);
        newItem.click();
    }
}