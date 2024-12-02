package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import school.redrover.page.base.BasePage;

public class PipelineSyntaxPage extends BasePage {
    public PipelineSyntaxPage(WebDriver driver) { super(driver); }

    public String getBreadCrumb(String projectName) {
        return getDriver().findElement(By.xpath("//a[@href = '/job/%s/pipeline-syntax/']".formatted(projectName))).getText();
    }

    public PipelineSyntaxPage selectNewStep(String value) {
        final WebElement selectList = getDriver().findElement(By.xpath("//select[@class='jenkins-select__input dropdownList']"));
        Select select = new Select(selectList);
        select.selectByValue(value);

        return this;
    }

    public PipelineSyntaxPage clickGeneratePipelineScript() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("generatePipelineScript")))
                .click();

        return this;
    }

    public PipelineSyntaxPage clickCopy() {
        getDriver().findElement(By.xpath("//button[@tooltip = 'Copy']")).click();

        return this;
    }

    public String getTitleOfSelectedScript(String selectName) {
        String value = getDriver().findElement(By.xpath("//option[@value = '%s']".formatted(selectName)))
                .getText();
        String result = value.split(":")[0].trim();

        return getDriver().findElement(By.xpath("//div[contains(text(),'%s')]".formatted(result))).getText();
    }
}
