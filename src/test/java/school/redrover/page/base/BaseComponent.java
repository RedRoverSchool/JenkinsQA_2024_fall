package school.redrover.page.base;

import org.openqa.selenium.WebDriver;

public class BaseComponent<T extends BasePage<T>> extends BaseModel {

    private final T returnPage;

    public BaseComponent(WebDriver driver, T owner) {
        super(driver);
        this.returnPage = owner;
    }

    public T getReturnPage() {
        return returnPage;
    }

}
