package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class MyViewsPage extends BasePage {

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement yesButton;

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    private void selectMenuFromItemDropdown(String itemName, String menuName) {
        TestUtils.moveAndClickWithJavaScript(getDriver(), getDriver().findElement(
                By.xpath("//td/a/span[text() = '%s']/../button".formatted(itemName))));

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='jenkins-dropdown__item__icon']/parent::*[contains(., '%s')]"
                        .formatted(menuName)))).click();
    }

    public MyViewsPage selectDeleteFromItemMenuAndClickYes(String itemName) {
        selectMenuFromItemDropdown(itemName, "Delete");
        getWait5().until(ExpectedConditions.visibilityOf(yesButton)).click();

        return this;
    }

    public MyViewsPage openDropdownViaChevron(String projectName) {
        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath("//a[@href='job/%s/']/span".formatted(projectName))))
                .pause(500)
                .perform();
        WebElement chevron = getDriver().findElement(By.xpath("//td//button[@aria-expanded='false']"));
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);
        getWait5().until(ExpectedConditions.attributeToBe(chevron, "aria-expanded", "true"));

        return this;
    }

    public MyViewsPage clickDeleteInProjectDropdown(String projectName) {
        getDriver().findElement(By.xpath("//button[@href='/me/my-views/view/all/job/%s/doDelete']".formatted(projectName))).click();

        return this;
    }

    public WebElement getDeletionPopup() {
        return getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//footer/following-sibling::dialog"))));
    }

    public String getTextEmptyFolder() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".h4"))).getText();
    }
}
