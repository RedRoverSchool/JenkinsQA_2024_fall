package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class AppearancePage extends BasePage {

    @FindBy(xpath = "//label[@for='radio-block-1']")
    private WebElement selectDarkThemes;

    @FindBy(css = "[class='attach-previous ']")
    private WebElement checkboxDifferentTheme;

    @FindBy(css = "html[data-theme]")
    private WebElement dataTheme;

    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    public AppearancePage clickSelectDarkThemes() {
        selectDarkThemes.click();

        return this;
    }

    public AppearancePage clickCheckboxDifferentTheme() {
        checkboxDifferentTheme.click();

        return this;
    }

    public Boolean isThemeApplied() {
        return dataTheme.getAttribute("data-theme").contains("system");
    }
}
