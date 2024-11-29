package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

}
