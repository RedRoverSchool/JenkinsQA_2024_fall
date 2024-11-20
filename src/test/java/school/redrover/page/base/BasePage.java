package school.redrover.page.base;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    private final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebDriver getDriver() {
        return driver;
    }
}
