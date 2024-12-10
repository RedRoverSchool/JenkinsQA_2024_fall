package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseModel;

public class SearchBoxPage extends BaseModel {

    @FindBy(xpath = "//h1")
    private WebElement title;

    public SearchBoxPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return title.getText();
    }
}
