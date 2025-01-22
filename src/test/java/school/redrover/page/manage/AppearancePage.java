package school.redrover.page.manage;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class AppearancePage extends BasePage<AppearancePage> {

    @FindBy(xpath = "//label[@for='radio-block-0']")
    private WebElement selectDarkThemes;

    @FindBy(xpath = "//label[@for='radio-block-1']")
    private WebElement selectSystemThemes;

    @FindBy(xpath = "//label[@for='radio-block-2']")
    private WebElement selectDefaultThemes;

    @FindBy(xpath = "//button[@name='Apply']")
    private WebElement selectApplyButton;

    @FindBy(css = "[class='attach-previous ']")
    private WebElement checkboxDifferentTheme;

    @FindBy(css = "html[data-theme]")
    private WebElement dataTheme;

    @FindBy(xpath = "//section[@class='jenkins-section']")
    private WebElement colorBackground;

    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    @Step("Select dark themes")
    public AppearancePage clickSelectDarkThemes() {
        selectDarkThemes.click();

        return this;
    }

    @Step("Select system themes")
    public AppearancePage clickSelectSystemThemes() {
        selectSystemThemes.click();

        return this;
    }

    @Step("Select default themes")
    public AppearancePage clickSelectDefaultThemes() {
        selectDefaultThemes.click();

        return this;
    }

    @Step("Click apply button")
    public AppearancePage clickApplyButton() {
        selectApplyButton.click();

        return this;
    }

    @Step("Click checkbox different theme")
    public AppearancePage clickCheckboxDifferentTheme() {
        checkboxDifferentTheme.click();

        return this;
    }

    @Step("Get attribute data")
    public String getAttributeData() {
        return dataTheme.getAttribute("data-theme");
    }

    @Step("Get color background")
    public String getColorBackground() {
        return colorBackground.getCssValue("background");
    }

    @Step("Get theme list")
    public List<WebElement> getThemeList() {
        return getDriver().findElements(By.xpath("//section[@class='jenkins-section']"));
    }
}
