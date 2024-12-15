package school.redrover.page.user;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class UsersPage extends BasePage {

    @FindBy(css = ".jenkins-button--primary")
    private WebElement createUserButton;

    @FindBy(xpath = "//table[@id='people']/tbody//td[3]")
    private List<WebElement> userNameList;

    @FindBy(xpath = "//div[@class='jenkins-app-bar__content']/h1")
    private WebElement usersTitle;

    @FindBy(xpath = "//a[@data-title='Users']")
    private WebElement deleteUser;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement okToDeleteButton;

    @FindBy(xpath = "//table[@id='people']/tbody//td[3]")
    private List<WebElement> usersTable;

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
        return userNameList.stream().map(WebElement::getText).toList();
    }

    public UserConfigPage clickToConfigureUser(String userName) {
        List<WebElement> elements = usersTable;
        for (WebElement elem : elements) {
            if (elem.getText().equals(userName)) {
                elem.findElement(By.xpath("following-sibling::*")).click();
            }
        }
        return new UserConfigPage(getDriver());
    }

    public UsersPage deleteUserFromUsersPage() {
        deleteUser.click();
        okToDeleteButton.click();
        return new UsersPage(getDriver());
    }

    public UsersPage createNewUser(String userName) {
        new UsersPage(getDriver())
                .clickCreateUser()
                .fillFormByValidDataToCreateUser(userName);
        return new UsersPage(getDriver());
    }

}
