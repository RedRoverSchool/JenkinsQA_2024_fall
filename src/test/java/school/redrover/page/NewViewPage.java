package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewViewPage extends BasePage {
    public NewViewPage(WebDriver driver) {
        super(driver);
    }

    public String getInputFromNameField() {

        return getDriver().findElement(By.id("name")).getAttribute("value");
    }

    public boolean isRadioButtonListViewSelected() {

        return getDriver().findElement(By.cssSelector("[for='hudson.model.ListView']")).isSelected();
    }

    public boolean isRadioButtonMyViewSelected() {

        return getDriver().findElement(By.cssSelector("[for='hudson.model.MyView']")).isSelected();
    }

    public boolean isCreateButtonEnabled() {

        return getDriver().findElement(By.cssSelector("button[id='ok']")).isEnabled();
    }

    public NewViewPage typeNameIntoInputField(String name) {
        WebElement inputNameField = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));
        inputNameField.sendKeys(name);

        return this;
    }

    public NewViewPage selectListViewType() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@for='hudson.model.ListView']"))).click();

        return this;
    }

    public ListViewConfigPage clickCreateButton() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        return new ListViewConfigPage(getDriver());
    }

    public Map<String, String> getTypeToDescriptionMap() {
        List<WebElement> typeOptionList = getWait2().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='jenkins-radio']"))
        );

        Map<String, String> typeToDescriptionMap = new HashMap<>();
        for (WebElement option : typeOptionList) {
            String optionText = option.findElement(By.xpath("./label")).getText();
            String descriptionText = option.findElement(By.xpath("./div")).getText();

            typeToDescriptionMap.put(optionText, descriptionText);
        }

        return typeToDescriptionMap;
    }

    public NewViewPage selectViewType(String viewType) {
        WebElement button  = getDriver().findElement(By.xpath("//input[@id = 'hudson.model." + viewType + "']"));
        boolean selectState = button.isSelected();
        if(selectState == false) {
            new Actions(getDriver()).moveToElement(button).click().build().perform();
        }
        return this;
    }
}