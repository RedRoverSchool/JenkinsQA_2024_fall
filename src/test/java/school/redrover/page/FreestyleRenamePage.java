package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class FreestyleRenamePage extends BasePage {

    private static final By inputField = By.xpath("//input[@checkdependson ='newName']");

    public FreestyleRenamePage(WebDriver driver) {
        super(driver);
    }

    public FreestyleRenamePage clearOldAndInputNewProjectName(String newName) {
        getDriver().findElement(inputField).clear();
        getDriver().findElement(inputField).sendKeys(newName);

        return this;
    }

    public FreestyleProjectPage clickRenameButton() {
        getDriver().findElement(By.name("Submit")).click();

        return new FreestyleProjectPage(getDriver());
    }
}
