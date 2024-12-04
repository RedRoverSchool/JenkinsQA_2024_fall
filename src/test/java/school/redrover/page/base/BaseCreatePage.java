package school.redrover.page.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.CreateNewItemPage;

public abstract class BaseCreatePage<Self extends BaseCreatePage<?>> extends BasePage {

    public static final By GET_OK_BUTTON = By.id("ok-button");

    public BaseCreatePage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage selectTypeProject(String typeProject) {
        getDriver().findElement(By.xpath("//span[text()='" + typeProject + "']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public Self clickOkButton() {
        getDriver().findElement(GET_OK_BUTTON).click();

        return (Self) this;
    }
}