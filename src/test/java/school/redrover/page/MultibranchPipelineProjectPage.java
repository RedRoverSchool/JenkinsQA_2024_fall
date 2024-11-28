package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BaseProjectPage;

public class MultibranchPipelineProjectPage extends BaseProjectPage<MultibranchPipelineProjectPage> {

    public MultibranchPipelineProjectPage(WebDriver driver) {
        super(driver);
    }

    public String getDescription() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("view-message"))).getText();
    }

    public HomePage deleteItemBySidebar() {
        getDriver().findElement(By.xpath("//a[@data-title='Delete Multibranch Pipeline']")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        return new HomePage(getDriver());
    }

    public HomePage deleteJobUsingDropdownBreadcrumbJobPage() {
        WebElement jobName = getDriver().findElement(
                By.xpath("//a[contains(@href,'job')][@class='model-link']"));
        new Actions(getDriver()).moveToElement(jobName).perform();

        WebElement chevronButton = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@class='jenkins-menu-dropdown-chevron'][contains(@data-href,'job')]")));

        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('mouseenter'));", chevronButton);
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].dispatchEvent(new Event('click'));", chevronButton);

        getDriver().findElement(By.xpath("//button[contains(@href,'Delete')]")).click();
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        return new HomePage(getDriver());
    }
}