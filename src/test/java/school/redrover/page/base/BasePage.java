package school.redrover.page.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.HomePage;

public abstract class BasePage extends BaseModel {

    @FindBy(id = "jenkins-home-link")
    private WebElement logo;

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage gotoHomePage() {
        logo.click();

        return new HomePage(getDriver());
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

}
