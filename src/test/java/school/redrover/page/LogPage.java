package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class LogPage extends BasePage {

    @FindBy(xpath = "//a[@class='jenkins-table__link']")
    private WebElement resultSearch;

    public LogPage(WebDriver driver) {
        super(driver);
    }

    public String getResultSearch() {
        return resultSearch.getText();
    }
}
