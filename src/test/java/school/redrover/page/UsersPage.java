package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.page.base.BasePage;

import java.util.List;

public class UsersPage extends BasePage {

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return getDriver().findElement(By.xpath("//div[@class='jenkins-app-bar__content']/h1")).getText();
    }

    public CreateUserPage clickCreateUser() {
        getDriver().findElement(By.cssSelector(".jenkins-button--primary")).click();
        return new CreateUserPage(getDriver());
    }

    public String getCreatedUserName() {
        List<WebElement> UsersLists = getDriver().findElements(By.cssSelector(".jenkins-table > tbody > tr > td:nth-child(3)"));
        for (WebElement UsersList : UsersLists) {
            String userName = UsersList.getText();
            if (userName.equals("Ivan Petrov")) {
                return userName;
            }
        }
        return null;
    }
}
