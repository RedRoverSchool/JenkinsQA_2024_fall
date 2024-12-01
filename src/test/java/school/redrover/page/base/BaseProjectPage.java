package school.redrover.page.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.CreateNewItemPage;
import school.redrover.page.FreestyleConfigPage;

import java.util.List;

public abstract class BaseProjectPage<Self extends BaseProjectPage<?>> extends BasePage {

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//span[text()='New Item']/ancestor::a")).click();

        return new CreateNewItemPage(getDriver());
    }

    public Self editDescription(String text) {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        getWait2().until(ExpectedConditions.textToBe(By.id("description"), text));

        return (Self) this;
    }

    public Self clearDescription() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("Submit")).click();

        return (Self) this;
    }

    public Self renameProject (String newName) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[1]/div[1]/div[7]/span/a"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName"))).clear();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("newName"))).sendKeys(newName);
        getDriver().findElement(By.name("Submit")).click();
        return (Self) this;
    }

    public String getRenameWarningMessage () {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[2]/div[2]/p"))).getText();
    }

    public String getDescription() {
        return getDriver().findElement(By.id("description")).getText();
    }

    public List<String> getSidebarOptionList() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='task ']//span[2]"))).stream().map(WebElement::getText).toList();
    }
}
