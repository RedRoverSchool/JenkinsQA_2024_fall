package school.redrover.page.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseConfigPage<Self extends BaseConfigPage<?, ?>, ProjectPage extends BaseProjectPage> extends BasePage {

    public BaseConfigPage(WebDriver driver) {
        super(driver);
    }

    protected abstract ProjectPage createProjectPage();

    public Self enterDescription(String description) {
        getDriver().findElement(By.cssSelector("[name$='description']")).sendKeys(description);

        return (Self)this;
    }

    public ProjectPage clickSaveButton() {
        getDriver().findElement(By.name("Submit")).click();

        return createProjectPage();
    }
}
