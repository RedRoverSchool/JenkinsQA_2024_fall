package school.redrover.page.user;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class UsersPage extends BasePage<UsersPage> {

    @FindBy(css = ".jenkins-button--primary")
    private WebElement createUserButton;

    @FindBy(xpath = "//table[@id='people']/tbody//td[3]")
    private List<WebElement> userNameList;

    @FindBy(xpath = "//div[@class='jenkins-app-bar__content']/h1")
    private WebElement usersTitle;

    @FindBy(xpath = "//button[@data-id='ok']")
    private WebElement okToDeleteButton;

    @FindBy(xpath = "//a[@data-title='Users']")
    private WebElement deleteUser;

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get title")
    public String getTitle() {
        return usersTitle.getText();
    }

    @Step("Click 'Create user' button")
    public CreateUserPage clickCreateUserButton() {
        createUserButton.click();
        return new CreateUserPage(getDriver());
    }

    @Step("Get user list")
    public List<String> getUserList() {
        return userNameList.stream().map(WebElement::getText).toList();
    }

    @Step("Click to configure user {userName}")
    public UserConfigPage clickToConfigureUser(String userName) {
        for (WebElement elem : userNameList) {
            if (elem.getText().equals(userName)) {
                elem.findElement(By.xpath("following-sibling::*")).click();
            }
        }
        return new UserConfigPage(getDriver());
    }

    @Step("Delete user from users page")
    public UsersPage deleteUserFromUsersPage() {
        deleteUser.click();
        okToDeleteButton.click();
        return new UsersPage(getDriver());
    }

}
