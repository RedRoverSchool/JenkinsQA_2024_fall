package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class ErrorPage extends BasePage<ErrorPage> {

    @FindBy(tagName = "p")
    private WebElement errorMessage;

    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage() {
        getWait5().until(ExpectedConditions.visibilityOf(errorMessage));

        return errorMessage.getText();
    }
}
