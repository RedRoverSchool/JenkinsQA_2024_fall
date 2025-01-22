package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class SearchPage extends BasePage<SearchPage> {

    @FindAll({
        @FindBy(xpath = "//li[@id = 'item_Built-In Node']/a"),
        @FindBy(xpath = "//a[@class='jenkins-table__link']")
    })
    private WebElement searchResult;

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(className = "error")
    private WebElement messageError;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getSearchResult() {
        return searchResult.getText();
    }

    public String getTitle() {
        return title.getText();
    }

    public String getMessageError() {
        return messageError.getText();
    }
}
