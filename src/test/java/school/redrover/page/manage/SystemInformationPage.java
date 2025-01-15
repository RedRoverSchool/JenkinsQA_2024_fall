package school.redrover.page.manage;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class SystemInformationPage extends BasePage {

    @FindBy(xpath = "//*[contains(text(),'Thread Dumps')]")
    private WebElement threadDumpsTab;

    @FindBy(xpath = "//a[@href ='threadDump']")
    private WebElement thisPageLink;

    public SystemInformationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Thread Dumps tab")
    public SystemInformationPage openThreadDumpsTab() {
        getWait5().until(ExpectedConditions.visibilityOf(threadDumpsTab)).click();

        return this;
    }

    @Step("Click on this page link")
    public ThreadDumpPage clickThisPageLink() {
        getWait5().until(ExpectedConditions.visibilityOf(thisPageLink)).click();

        return new ThreadDumpPage(getDriver());
    }
}
