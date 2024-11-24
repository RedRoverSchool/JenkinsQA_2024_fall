package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import java.util.List;

public class PipelineConfigurePage extends BasePage {

    public PipelineConfigurePage(WebDriver driver) {
        super(driver);
    }

    public PipelineProjectPage clickSaveButton() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        return new PipelineProjectPage(getDriver());
    }

    public List<String> getSidebarConfigurationOption() {
        return getWait2().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='task']//span[2]"))).stream().map(WebElement::getText).toList();
    }

    public PipelineConfigurePage addScriptToPipeline(String script) {

        TestUtils.scrollToBottom(getDriver());
        getDriver().findElement(By.cssSelector("textarea[class='ace_text-input']")).sendKeys(script);

        return this;
    }
}
