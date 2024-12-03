package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class UsersPage extends BasePage {
    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']/h1")).getText();
    }
}
