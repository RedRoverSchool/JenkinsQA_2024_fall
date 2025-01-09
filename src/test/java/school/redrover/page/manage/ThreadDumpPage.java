package school.redrover.page.manage;

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

    public String getTitle() {
        return getWait5().until(ExpectedConditions.visibilityOf(title)).getText();
    }
}
