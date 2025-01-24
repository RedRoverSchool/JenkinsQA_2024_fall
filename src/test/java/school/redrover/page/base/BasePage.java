package school.redrover.page.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import school.redrover.page.component.FooterComponent;
import school.redrover.page.component.HeaderComponent;

public abstract class BasePage<T extends BasePage<T>> extends BaseModel {

    private HeaderComponent<T> header;
    private FooterComponent<T> footer;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HeaderComponent<T> getHeader() {
        if (header == null) {
            header = new HeaderComponent<>(getDriver(), (T) this);
        }
        return header;
    }

    public FooterComponent<T> getFooter() {
        if (footer == null) {
            footer = new FooterComponent<>(getDriver(), (T) this);
        }
        return footer;
    }

    @Step("Get current page url")
    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

}
