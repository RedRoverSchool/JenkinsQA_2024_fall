package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewViewPage extends BasePage {

    @FindBy(id = "name")
    private WebElement nameInputField;

    @FindBy(css = "[for='hudson.model.ListView']")
    private WebElement radioButtonListView;

    @FindBy(css = "[for='hudson.model.MyView']")
    private WebElement radioButtonMyView;

    @FindBy(css = "button[id='ok']")
    private WebElement createButton;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@class='jenkins-radio']")
    private List<WebElement> typeOptionList;

    public NewViewPage(WebDriver driver) {
        super(driver);
    }

    public String getInputFromNameField() {

        return nameInputField.getAttribute("value");
    }

    public boolean isRadioButtonListViewSelected() {

        return radioButtonListView.isSelected();
    }

    public boolean isRadioButtonMyViewSelected() {

        return radioButtonMyView.isSelected();
    }

    public boolean isCreateButtonEnabled() {

        return createButton.isEnabled();
    }

    public NewViewPage typeNameIntoInputField(String name) {
        getWait2().until(ExpectedConditions.visibilityOf(nameInputField)).sendKeys(name);

        return this;
    }

    public NewViewPage selectListViewType() {
        getWait2().until(ExpectedConditions.elementToBeClickable(radioButtonListView)).click();

        return this;
    }

    public NewViewPage selectMyViewType() {
        getWait2().until(ExpectedConditions.elementToBeClickable(radioButtonMyView)).click();

        return this;
    }

    public ListViewConfigPage clickCreateButton() {
        submitButton.click();

        return new ListViewConfigPage(getDriver());
    }

    public Map<String, String> getTypeToDescriptionMap() {
        getWait2().until(ExpectedConditions.visibilityOfAllElements(typeOptionList));

        Map<String, String> typeToDescriptionMap = new HashMap<>();
        for (WebElement option : typeOptionList) {
            String optionText = option.findElement(By.xpath("./label")).getText();
            String descriptionText = option.findElement(By.xpath("./div")).getText();

            typeToDescriptionMap.put(optionText, descriptionText);
        }

        return typeToDescriptionMap;
    }

}