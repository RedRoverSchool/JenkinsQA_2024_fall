package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.NoSuchElementException;

public class ListViewConfigPage extends BasePage {

    @FindBy(xpath = "//div[@class='repeated-chunk__header']")
    private List<WebElement> columnList;

    @FindBy(xpath = "//button[@suffix='columns']")
    private WebElement columnButton;

    public ListViewConfigPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigPage selectJobCheckBoxByName(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@title='%s']".formatted(name)))).click();

        return this;
    }

    public ViewPage clickOkButton() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        return new ViewPage(getDriver());
    }

    public ListViewConfigPage clickDeleteColumnByName(String name) {
        TestUtils.scrollToBottomWithJS(getDriver());

        WebElement columnOption = columnList
                .stream()
                .filter(column -> column.getText().trim().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(name + " is not listed in column options"));

        WebElement closeButton = columnOption.findElement(By.xpath(".//button"));
        closeButton.click();

        getWait10().until(ExpectedConditions.numberOfElementsToBe(
                By.xpath("//div[@class='repeated-chunk__header']"), 6));

        return this;
    }

    public ListViewConfigPage clickAddColumn() {
        TestUtils.scrollToBottomWithJS(getDriver());

        getWait5().until(ExpectedConditions.elementToBeClickable(columnButton)).click();

        return this;
    }

    public ListViewConfigPage selectColumnByName(String name) {
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'%s')]".formatted(name)))).click();

        return this;
    }

}
