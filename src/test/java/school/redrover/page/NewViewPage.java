package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

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
}