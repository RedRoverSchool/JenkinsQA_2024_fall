package school.redrover.page;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class SignInPage extends BasePage<SignInPage> {

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1")
    private WebElement signInTitle;

    @Step("Get sing in title")
    public String getSignInTitle() {
        return getWait2().until(ExpectedConditions.visibilityOf(signInTitle)).getText();
    }
}
