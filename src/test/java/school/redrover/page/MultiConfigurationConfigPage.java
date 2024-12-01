package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BaseConfigPage;

import java.util.List;

public class MultiConfigurationConfigPage extends BaseConfigPage<MultiConfigurationConfigPage, MultiConfigurationProjectPage> {

    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected MultiConfigurationProjectPage createProjectPage() {

        return new MultiConfigurationProjectPage(getDriver());
    }

    public MultiConfigurationConfigPage clickThrottleBuildsCheckbox() {
        getDriver().findElement(By.xpath("//span[@class='jenkins-checkbox']/label[text()='Throttle builds']")).click();

        return this;
    }

    public MultiConfigurationConfigPage selectDurationItem(String item) {
        WebElement durationItemsSelect = getDriver().findElement(By.xpath("//select[@name='_.durationName']"));
        Select select = new Select(durationItemsSelect);
        select.selectByValue(item);

        return this;
    }

    public String getSelectedDurationItemText() {
        WebElement durationItemsSelect = getDriver().findElement(By.xpath("//select[@name='_.durationName']"));
        Select select = new Select(durationItemsSelect);

        return select.getFirstSelectedOption().getText().toLowerCase();
    }

    public List<String> getAllDurationItemsFromSelect() {
        WebElement durationItemsSelect = getDriver().findElement(By.xpath("//select[@name='_.durationName']"));
        Select select = new Select(durationItemsSelect);

        return select.getOptions().stream()
                   .map(option -> option.getAttribute("value"))
                   .toList();
    }
}