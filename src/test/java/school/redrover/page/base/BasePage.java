package school.redrover.page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import school.redrover.page.component.HeaderComponent;

public abstract class BasePage<T extends BasePage<T>> extends BaseModel {

    private HeaderComponent<T> header;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HeaderComponent<T> getHeader() {
        if (header == null) {
            header = new HeaderComponent<>(getDriver(), (T) this);
        }
        return header;
    }

    @Step("Get current page url")
    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

}
