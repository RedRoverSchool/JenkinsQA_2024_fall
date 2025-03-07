package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class SearchPage extends BasePage<SearchPage> {

    @FindBy (xpath = "//h1")
    private WebElement searchResult;

    @FindBy(xpath = "//h1")
    private WebElement title;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getSearchResult() {
        return searchResult.getText();
    }

    public String getTitle() {
        return title.getText();
    }

}
