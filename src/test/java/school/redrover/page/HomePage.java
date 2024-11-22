package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.FolderTest;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ProjectPage openProject(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();

        return new ProjectPage(getDriver());
    }

    public NewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::a")).click();

        return new NewItemPage(getDriver());
    }

    public String getItemNameByOrder(int order) {

        return getDriver().findElements(By.xpath("//td/a/span")).stream()
                .skip(order - 1)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Некорректный порядок: " + order))
                .getText();
    }

    // //div[@class='jenkins-dropdown__item__icon']/parent::*[contains(., 'Rename')] по имени в меню
    // //td/a/span[text() = 'ipo']/../button
    public HomePage selectFolderMenuByChevron(String folderName, FolderTest.FolderMenu folderMenu) {

        WebElement chevron = getDriver().findElement(
                By.xpath("//a[@class='jenkins-table__link model-link inside']//button[@class='jenkins-menu-dropdown-chevron']"));

        new Actions(getDriver()).moveToElement(getDriver().findElement(By.xpath(ITEM_LOCATOR_BY_NAME
                .formatted(folderName)))).perform();
        TestUtils.moveAndClickWithJavaScript(getDriver(), chevron);

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@class='jenkins-dropdown__item '][%s]"
                        .formatted(folderMenu.getMenuNumber())))).click();

    }



}
