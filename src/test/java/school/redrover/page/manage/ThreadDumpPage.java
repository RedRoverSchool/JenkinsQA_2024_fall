package school.redrover.page.manage;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class ThreadDumpPage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement title;

    public ThreadDumpPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get title")
    public String getTitle() {
        return getWait5().until(ExpectedConditions.visibilityOf(title)).getText();
    }
}
