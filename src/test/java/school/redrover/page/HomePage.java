package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public ProjectPage openProject(String name) {
        getDriver().findElement(By.xpath("//td/a/span[text() = '%s']/..".formatted(name))).click();

        return new ProjectPage(getDriver());
    }
}
