package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ViewPage extends HomePage {

    @FindBy(xpath = "//a[@class= 'task-link  confirmation-link']")
    private WebElement deleteMenuButton;

    @FindBy(xpath = "//dialog[@class = 'jenkins-dialog']")
    private WebElement deleteDialog;

    @FindBy(xpath = "//thead//th")
    private List <WebElement> listOfColumnNamesForExistingProject;

    @FindBy(xpath = "//button[@data-id = 'ok']")
    private WebElement okButtonInDeleteDialog;

    public ViewPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigPage clickEditView(String name) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/view/%s/configure']".formatted(name)))).click();

        return new ListViewConfigPage(getDriver());
    }

    public List<String> getColumnList() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElements(listOfColumnNamesForExistingProject))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public ViewPage selectViewTypeToDelete(String viewType) {
        getDriver().findElement(By.linkText(viewType)).click();
        return this;
    }

    public ViewPage deleteView() {

        deleteMenuButton.click();
        getWait5().until(ExpectedConditions.visibilityOf(deleteDialog));
        okButtonInDeleteDialog.click();
        return this;
    }

}
