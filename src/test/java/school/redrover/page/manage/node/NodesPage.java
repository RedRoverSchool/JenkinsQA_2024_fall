package school.redrover.page.manage.node;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

import java.util.List;

public class NodesPage extends BasePage {

    @FindBy(xpath = "//a[@href='new']")
    private WebElement buttonNewNode;

    public NodesPage(WebDriver driver) {
        super(driver);
    }

    public NodesProjectPage clickButtonNewNode() {
        buttonNewNode.click();

        return new NodesProjectPage(getDriver());
    }

    public List<String> getNodeList() {
        return getDriver().findElements(By.xpath("//a[@class='jenkins-table__link model-link inside']")).stream().map(x -> x.getText()).toList();
    }
}
