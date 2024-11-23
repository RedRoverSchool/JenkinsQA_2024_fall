package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class BuildHistoryPage extends BasePage {
    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public String getBuildHistory(){
        return getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link']/span")).getText();
    }
}
