package school.redrover.page.manage.node;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseConfigPage;

import java.util.List;

public class NodesConfigPage extends BaseConfigPage<NodesConfigPage, NodesProjectPage> {

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement buttonCreate;

    public NodesConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected NodesProjectPage createProjectPage() {
        return new NodesProjectPage(getDriver());
    }

    public List<String> getNodeList() {
        return getDriver().findElements(By.xpath("//a[@class='jenkins-table__link model-link inside']")).stream().map(x -> x.getText()).toList();
    }

    public NodesConfigPage clickButtonCreate() {
        buttonCreate.click();

        return new NodesConfigPage(getDriver());
    }
}
