package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.page.base.BasePage;

import java.util.List;

public class BuildHistoryPage extends BasePage {

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public String getBuildName(){
        return getDriver().findElement(By.xpath("//a[@class='jenkins-table__link model-link']/span")).getText();
    }

    public List<String> getListOfStatuses() {

        return getDriver().findElements(By.xpath("//*[@id='projectStatus']/tbody/tr/td[4]"))
                .stream().map(WebElement::getText).toList();
    }
}
