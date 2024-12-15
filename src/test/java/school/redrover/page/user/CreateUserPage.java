package school.redrover.page.user;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class CreateUserPage extends BasePage {

    @FindBy(css = ".jenkins-submit-button")
    private WebElement createUserButton;

    @FindBy(xpath = "//div[@class='error jenkins-!-margin-bottom-2'][1]")
    private WebElement validationMessage;

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

    public CreateUserPage clickCreateUserButton() {
        createUserButton.click();

        return new CreateUserPage(getDriver());
    }

    public String getValidationMessage() {
        return validationMessage.getText();
    }

    public UsersPage fillFormByValidDataToCreateUser(String fullName) {
        usernameField.sendKeys("Ivan");
        password1Field.sendKeys("123");
        password2Field.sendKeys("123");
        fullnameField.sendKeys(fullName);
        emailField.sendKeys("Petrov@gmail.com");
        clickCreateUserButton();

        return new UsersPage(getDriver());
    }
}
