package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

import java.util.List;


public class PipelineBuildPage extends BasePage {

    public PipelineBuildPage(WebDriver driver) {
        super(driver);
    }

    public PipelineBuildPage clickKeepThisBuildForever() {
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@name='Submit']"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[normalize-space(text())=\"Don't keep this build forever\"]")));

        return this;
    }

    public boolean isDeleteBuildOptionSidebarPresent(String name) {
        List<WebElement> sidebarTaskList = getWait10().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@id='tasks']/div")));

        return sidebarTaskList.stream()
                .anyMatch(element -> element.getAttribute("href") != null &&
                        element.getAttribute("href").contains("/job/%s/1/confirmDelete".formatted(name)));
    }
}
