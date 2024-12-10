package school.redrover.page.multiConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BaseConfigPage;

import java.util.List;

public class MultiConfigurationConfigPage extends BaseConfigPage<MultiConfigurationConfigPage, MultiConfigurationProjectPage> {

    @FindBy(xpath = "//span[@class='jenkins-checkbox']/label[text()='Throttle builds']")
    private WebElement throttleBuildsCheckbox;

    @FindBy(xpath = "//select[@name='_.durationName']")
    private WebElement durationItemsSelect;

    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultiConfigurationProjectPage createProjectPage() {

        return new MultiConfigurationProjectPage(getDriver());
    }

    public MultiConfigurationConfigPage clickThrottleBuildsCheckbox() {
        throttleBuildsCheckbox.click();

        return this;
    }

    public MultiConfigurationConfigPage selectDurationItem(String item) {
        Select select = new Select(durationItemsSelect);
        select.selectByValue(item);

        return this;
    }

    public String getSelectedDurationItemText() {
        Select select = new Select(durationItemsSelect);

        return select.getFirstSelectedOption().getText().toLowerCase();
    }

    public List<String> getAllDurationItemsFromSelect() {
        return new Select(durationItemsSelect).getOptions().stream()
                .map(option -> option.getAttribute("value"))
                .toList();
    }

}