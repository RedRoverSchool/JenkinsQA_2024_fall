package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

public class FreestyleProjectPage extends BaseProjectPage<FreestyleProjectPage> {

    public FreestyleProjectPage(WebDriver driver) {
        super(driver);
    }

    By getHomeIcon = By.xpath("//a[@id='jenkins-home-link']");

    public String getProjectName() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public HomePage goHome() {
        getDriver().findElement(getHomeIcon).click();

        return new HomePage(getDriver());
    }

    public FreestyleRenamePage clickRenameOnSidebar() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Rename']/.."))).click();

        return new FreestyleRenamePage(getDriver());
    }
}
