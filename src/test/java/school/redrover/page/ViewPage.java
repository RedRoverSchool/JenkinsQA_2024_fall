package school.redrover.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.home.HomePage;

import java.util.List;

public class ViewPage extends HomePage {

    @FindBy(xpath = "//thead//th")
    private List<WebElement> viewList;

    @FindBy(xpath = "//a[@class= 'task-link  confirmation-link']")
    private WebElement deleteOption;

    @FindBy(xpath = "//dialog[@class = 'jenkins-dialog']")
    private WebElement confirmationDialog;

    @FindBy(xpath = "//button[@data-id = 'ok']")
    private WebElement yesButton;

    @FindBy(xpath = "//div[@class = 'tabBar']//a")
    private List<WebElement> viewsList;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigPage clickEditView(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view/%s/configure']".formatted(name)))).click();

        return new ListViewConfigPage(getDriver());
    }

    public List<String> getColumnList() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElements(viewList))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Select '{viewType}' view")
    public ViewPage selectViewTypeToDelete(String viewType) {
        getDriver().findElement(By.linkText(viewType)).click();
        return this;
    }

    @Step("Click 'Delete View' in sidebar menu")
    public ViewPage deleteView() {
        deleteOption.click();

        return this;
    }

    @Step("Click 'Yes' in 'Delete view' window")
    public ViewPage clickYesInPopUp() {
        getWait5().until(ExpectedConditions.visibilityOf(confirmationDialog));
        yesButton.click();

        return this;
    }

    @Step("Get list of existing views")
    public List<String> getViewsList() {
        getWait5().until(ExpectedConditions.visibilityOfAllElements(viewsList));

        return viewsList.stream().map(WebElement::getText).toList();
    }
}
