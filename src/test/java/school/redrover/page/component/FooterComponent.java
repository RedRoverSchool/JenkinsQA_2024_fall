package school.redrover.page.component;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.AboutPage;
import school.redrover.page.base.BaseComponent;
import school.redrover.page.base.BasePage;

public class FooterComponent<T extends BasePage<T>> extends BaseComponent<T> {

    @FindBy(css = "[class$='jenkins_ver']")
    private WebElement jenkinsVersion;

    @FindBy(xpath = "//a[normalize-space()='About Jenkins']")
    private WebElement aboutJenkinsButton;

    public FooterComponent(WebDriver driver, T owner) {
        super(driver, owner);
    }

    @Step("Get Jenkins version number")
    public String getJenkinsVersion() {
        return jenkinsVersion.getText();
    }

    @Step("Click Jenkins version number button")
    public FooterComponent<T> clickJenkinsVersionButton() {
        jenkinsVersion.click();

        return this;
    }

    @Step("Click 'About Jenkins' option in dropdown menu of Jenkins version number")
    public AboutPage gotoAboutPage() {
        getWait5().until(ExpectedConditions.visibilityOf(aboutJenkinsButton)).click();

        return new AboutPage(getDriver());
    }

    @Step("Get 'About Jenkins' option in dropdown menu of Jenkins version number")
    public String getAboutJenkinsDropdownLabelText() {
        return getWait5().until(ExpectedConditions.visibilityOf(aboutJenkinsButton)).getText();
    }

}
