package school.redrover.page.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.HomePage;

public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage gotoHomePage() {
        getDriver().findElement(By.id("jenkins-home-link")).click();

        return new HomePage(getDriver());
    }
}
