package school.redrover.page.base;

import org.openqa.selenium.WebDriver;

public class BaseComponent<Target extends BasePage<Target>> extends BaseModel {

    private final Target returnPage;

    public BaseComponent(WebDriver driver, Target owner) {
        super(driver);
        this.returnPage = owner;
    }

    public Target getReturnPage() {
        return returnPage;
    }
}
