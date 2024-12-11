package school.redrover.page.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class UsersPage extends BasePage {

    @FindBy(css = ".jenkins-button--primary")
    private WebElement createUserButton;

    @FindBy(css = ".jenkins-table > tbody > tr > td:nth-child(3)")
    private List<WebElement> userNameList;

    @FindBy(xpath = "//div[@class='jenkins-app-bar__content']/h1")
    private WebElement usersTitle;

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return usersTitle.getText();
    }

    public CreateUserPage clickCreateUser() {
        createUserButton.click();
        return new CreateUserPage(getDriver());
    }

    public List<String> getCreatedUserName() {
        return  userNameList.stream().map(WebElement::getText).toList();
    }
}
