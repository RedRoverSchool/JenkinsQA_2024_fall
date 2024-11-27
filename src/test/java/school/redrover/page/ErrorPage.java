package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class ErrorPage extends BasePage {
    public ErrorPage(WebDriver driver) {
        super(driver);
    }

    public String getErrorMessage() {
        return getDriver().findElement(By.tagName("p")).getText();
    }
}
