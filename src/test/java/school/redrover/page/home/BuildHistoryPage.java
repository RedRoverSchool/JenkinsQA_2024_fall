package school.redrover.page.home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.ConsoleOutputPage;
import school.redrover.page.base.BasePage;

import java.util.List;

public class BuildHistoryPage extends BasePage {

    @FindBy(xpath = "//a[@class='jenkins-table__link model-link']/span")
    private WebElement lastProjectName;

    @FindBy(xpath = "//*[@id='projectStatus']/tbody/tr/td[4]")
    private List<WebElement> statusList;

    @FindBy(xpath = "//div[@class='jenkins-table__cell__button-wrapper']/*[@id]")
    private WebElement buildStatusSign;

    @FindBy(xpath = "//a[@class='jenkins-table__link jenkins-table__badge model-link inside']")
    private WebElement buildDisplayName;

    @FindBy(xpath = "//span[text()='Console output']/..")
    private WebElement consoleOutputButton;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public String getProjectName() {
        return lastProjectName.getText();
    }

    public List<String> getListOfStatuses() {

        return statusList.stream().map(WebElement::getText).toList();
    }

    public String getCssValueOfStatusByIndex(int index, String cssProperty) {
        return statusList.get(index).getCssValue(cssProperty);
    }

    public String getBuildStatusSignColor() {
        return buildStatusSign.getAttribute("id");
    }

    public String getBuildDisplayName() {
        return buildDisplayName.getText();
    }

    public ConsoleOutputPage clickConsoleOutput() {
        consoleOutputButton.click();

        return new ConsoleOutputPage(getDriver());
    }

}
