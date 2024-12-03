package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;


public class CreateUserPage extends BasePage {

    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage clickCreateUserButton() {
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        return new CreateUserPage(getDriver());
    }

    public String getValidationNessage() {
        return getDriver().findElement(By.xpath("//div[@class='error jenkins-!-margin-bottom-2'] [1]")).getText();
    }

    public UsersPage fillFormByValidDataToCreateUser() {
        getDriver().findElement(By.cssSelector("#username")).sendKeys("Ivan");
        getDriver().findElement(By.xpath("//input[@name='password1']")).sendKeys("123");
        getDriver().findElement(By.xpath("//input[@name='password2']")).sendKeys("123");
        getDriver().findElement(By.xpath("//input[@name='fullname']")).sendKeys("Ivan Petrov");
        getDriver().findElement(By.xpath("//input[@name='email']")).sendKeys("Petrov@gmail.com");
        clickCreateUserButton();
        return new UsersPage(getDriver());
    }


}
