package school.redrover.page.systemConfiguration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class SystemPage extends BasePage {

    @FindBy(id = "breadcrumbs")
    private WebElement breadcrumbs;

    @FindBy(xpath = "//h1")
    private WebElement pageTitle;

    public SystemPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadCrumbs() {

        return breadcrumbs.getText();
    }

    public String getTitle() {
        return getWait5().until(ExpectedConditions.visibilityOf(pageTitle)).getText();
    }
}
