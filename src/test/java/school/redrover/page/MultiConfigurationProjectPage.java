package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage<MultiConfigurationProjectPage> {

    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

    By getHomeIcon = By.xpath( "//a[@id='jenkins-home-link']");
    public HomePage goHome() {
        getDriver().findElement(getHomeIcon).click();

        return  new HomePage(getDriver());

    }

    public MultiConfigurationProjectPage clickDeleteProject() {
        getDriver().findElement(
                By.xpath("//div[@id='side-panel']//span[text()='Delete Multi-configuration project']")).click();

        return this;
    }

    public WebElement getDeletionPopup() {
        return getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(
                By.xpath("//footer/following-sibling::dialog"))));
    }
}
