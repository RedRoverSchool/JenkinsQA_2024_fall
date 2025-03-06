package school.redrover.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;
import java.util.NoSuchElementException;

public class ListViewConfigPage extends BasePage<ListViewConfigPage> {

    @FindBy(xpath = "//div[@class='repeated-chunk__header']")
    private List<WebElement> columnList;

    @FindBy(xpath = "//button[@suffix='columns']")
    private WebElement columnButton;

    @FindBy(xpath = "//div[@class = 'tabBar']//a")
    private List<WebElement> viewsList;

    @FindBy(xpath = "//button[@suffix='jobFilters']")
    private WebElement addJobFilterButton;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    public ListViewConfigPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigPage selectJobCheckBoxByName(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='%s']".formatted(name)))).click();

        return this;
    }

    @Step("Click 'Ok' button")
    public ViewPage clickOkButton() {
        saveButton.click();

        return new ViewPage(getDriver());
    }

    public ListViewConfigPage clickDeleteColumnByName(String name) {
        TestUtils.scrollToElementWithJS(getDriver(), addJobFilterButton);

        WebElement columnOption = columnList
                .stream()
                .filter(column -> column.getText().trim().equals(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(name + " is not listed in column options"));

        WebElement closeButton = columnOption.findElement(By.xpath(".//button"));
        closeButton.click();

        getWait10().until(driver -> columnList.size() == 6);

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

    @Step("Get list of existing views")
    public List<String> getViewsList() {
        getWait5().until(ExpectedConditions.visibilityOfAllElements(viewsList));

        return viewsList.stream().map(WebElement::getText).toList();
    }
}
