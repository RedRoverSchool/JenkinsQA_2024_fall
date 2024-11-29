package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class ErrorPage extends BasePage {
    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    By errorMessage = By.tagName("p");

    public String getErrorMessage() {
        getWait5().until(ExpectedConditions.visibilityOf(getDriver().findElement(errorMessage)));
        return getDriver().findElement(errorMessage).getText();
    }
}
