package school.redrover.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BasePage;

public class FreestyleRenamePage extends BasePage {

    @FindBy(xpath = "//input[@checkdependson ='newName']")
    private WebElement inputField;

    @FindBy(name = "Submit")
    private WebElement renameButton;

    public FreestyleRenamePage(WebDriver driver) {
        super(driver);
    }

    public FreestyleRenamePage clearOldAndInputNewProjectName(String newName) {
        inputField.clear();
        inputField.sendKeys(newName);

        return this;
    }

    public FreestyleProjectPage clickRenameButton() {
        renameButton.click();

        return new FreestyleProjectPage(getDriver());
    }
}
