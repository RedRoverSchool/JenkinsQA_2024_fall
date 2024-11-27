package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationConfigPage extends BaseProjectPage {
    public MultiConfigurationConfigPage(WebDriver driver) {
        super(driver);
    }

    By getHomePageIcon = By.xpath("//a[@id='jenkins-home-link']");


    public HomePage goHome() {
        getDriver().findElement(getHomePageIcon).click();

        return new HomePage(getDriver());
    }


}
