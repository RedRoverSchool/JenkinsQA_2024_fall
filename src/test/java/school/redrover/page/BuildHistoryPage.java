package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

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
                .stream()
                .map(WebElement::getText)
                .toList();
    }
    public BuildHistoryPage addBuildSteps(String projectName, String step) {
        new HomePage(getDriver()).selectConfigureFromItemMenu(projectName);
        TestUtils.scrollToBottom(getDriver());
        getDriver().findElement(By.xpath("//button[normalize-space()='Add build step']")).click();
        getWait2().until(ExpectedConditions.
                elementToBeClickable(By.xpath("//button[normalize-space()='%s']".formatted(step)))).click();
        getDriver().findElement(By.xpath("//button[contains(@name,'Submit')]")).click();

        return this;
    }
}
