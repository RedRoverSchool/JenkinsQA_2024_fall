package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class ProjectPage extends BasePage {

    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    public ProjectPage editDescription(String text) {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).sendKeys(text);
        getDriver().findElement(By.name("Submit")).click();

        getWait2().until(ExpectedConditions.textToBe(By.id("description"), text));

        return this;
    }

    public ProjectPage clearDescription() {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.name("description")).clear();
        getDriver().findElement(By.name("Submit")).click();

        return this;
    }

    public String getDescription() {
        return getDriver().findElement(By.id("description")).getText();
    }

    public String getConfigurationName() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }
}
