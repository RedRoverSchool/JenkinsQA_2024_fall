package school.redrover.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.CreateNewItemPage;

import java.util.List;

public abstract class BaseProjectPage<Self extends BaseProjectPage<?>> extends BasePage {

    @FindBy(id = "description-link")
    WebElement descriptionButton;

    @FindBy(xpath = "//span[text()='New Item']/ancestor::a")
    WebElement newItem;

    @FindBy(name = "description")
    WebElement descriptionField;

    @FindBy(name = "Submit")
    WebElement submitButton;

    @FindBy(id = "description")
    WebElement descriptionText;

    @FindBy(xpath = "//a[contains(@href,'rename')]")
    WebElement renameButtonViaSidebar;

    @FindBy(name = "newName")
    WebElement newNameField;

    @FindBy(xpath = "//div[@id='main-panel']/p")
    WebElement errorMessage;

    @FindBy(xpath = "//div[@class='task ']//span[2]")
    List<WebElement> sidebarElementList;

    @FindBy(xpath = "//*[@id='main-panel']/h1")
    WebElement itemName;

    public BaseProjectPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage clickNewItem() {
        newItem.click();

        return new CreateNewItemPage(getDriver());
    }

    public Self editDescription(String text) {
        descriptionButton.click();
        descriptionField.sendKeys(text);
        submitButton.click();

        getWait2().until(ExpectedConditions.textToBePresentInElement(descriptionText, text));

        return (Self) this;
    }

    public Self clearDescription() {
        descriptionButton.click();
        descriptionField.clear();
        submitButton.click();

        return (Self) this;
    }

    public Self renameItem(String newName) {
        renameButtonViaSidebar.click();
        newNameField.clear();
        newNameField.sendKeys(newName);
        submitButton.click();
        return (Self) this;
    }

    public String getRenameWarningMessage() {
        return getWait10().until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    public String getItemName() {
        return getWait10().until(ExpectedConditions.visibilityOf(itemName)).getText();
    }

    public String getDescription() {
        return descriptionText.getText();
    }

    public List<String> getSidebarOptionList() {
        return getWait5().until(ExpectedConditions.visibilityOfAllElements(sidebarElementList))
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
