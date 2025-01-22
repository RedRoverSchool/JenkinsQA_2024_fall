package school.redrover.page.user;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class CreateUserPage extends BasePage<CreateUserPage> {

    @FindBy(css = ".jenkins-submit-button")
    private WebElement createUserButton;

    @FindBy(xpath = "//div[@class='error jenkins-!-margin-bottom-2']")
    private List<WebElement> validationMessage;

    @FindBy(css = "#username")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@name='password1']")
    private WebElement password1Field;

    @FindBy(xpath = "//input[@name='password2']")
    private WebElement password2Field;

    @FindBy(xpath = "//input[@name='fullname']")
    private WebElement fullnameField;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click create user button")
    public <T extends BasePage<T>> T clickCreateUserFormSubmit(Class<T> pageClass) {
        createUserButton.click();

        if (!validationMessage.isEmpty() && pageClass.isAssignableFrom(CreateUserPage.class)) {
            return pageClass.cast(new CreateUserPage(getDriver()));
        } else {
            return pageClass.cast(new UsersPage(getDriver()));
        }
    }

    @Step("Get validation messages")
    public List<String> getValidationMessages() {
        return validationMessage.stream().map(WebElement::getText).toList();
    }

    @Step("Enter username {username}")
    public CreateUserPage enterUsername(String username) {
        usernameField.sendKeys(username);
        return new CreateUserPage(getDriver());
    }

    @Step("Enter password {password}")
    public CreateUserPage enterPassword(String password) {
        password1Field.sendKeys(password);
        return new CreateUserPage(getDriver());
    }

    @Step("Enter confirm password {password}")
    public CreateUserPage enterConfirmPassword(String password) {
        password2Field.sendKeys(password);
        return new CreateUserPage(getDriver());
    }

    @Step("Enter full name {fullName}")
    public CreateUserPage enterFullName(String fullName) {
        fullnameField.sendKeys(fullName);
        return new CreateUserPage(getDriver());
    }

    @Step("Enter email {email}")
    public CreateUserPage enterEmail(String email) {
        emailField.sendKeys(email);
        return new CreateUserPage(getDriver());
    }
}
