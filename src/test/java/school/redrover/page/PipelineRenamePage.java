package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class PipelineRenamePage extends BasePage {

    public PipelineRenamePage(WebDriver driver) {
        super(driver);
    }

    public PipelineRenamePage cleanInputFieldAndTypeName(String name) {
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@checkdependson='newName']")).sendKeys(name);

        return this;
    }

    public PipelineProjectPage clickRenameButton() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        return new PipelineProjectPage(getDriver());
    }

    public ErrorPage clickRenameButtonAndRedirectToErrorPage() {
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        return new ErrorPage(getDriver());
    }
}
