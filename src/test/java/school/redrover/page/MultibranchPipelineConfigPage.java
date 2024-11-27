package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class MultibranchPipelineConfigPage extends BasePage {

    public MultibranchPipelineConfigPage(WebDriver driver) {
        super(driver);
    }

    public MultibranchPipelineConfigPage enterDescription(String description) {
        getDriver().findElement(By.cssSelector("[name$='description']")).sendKeys(description);

        return this;
    }

    public MultibranchPipelineProjectPage clickSaveButton() {
        getDriver().findElement(By.name("Submit")).click();

        return new MultibranchPipelineProjectPage(getDriver());
    }
}
