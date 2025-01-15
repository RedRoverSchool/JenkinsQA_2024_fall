package school.redrover.page.folder;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.base.BaseConfigPage;

public class FolderConfigPage extends BaseConfigPage<FolderConfigPage, FolderProjectPage> {

    @FindBy(xpath = "//div[contains(text(),'Display Name')]/following-sibling::div[1]/input")
    private WebElement displayNameField;

    public FolderConfigPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FolderProjectPage createProjectPage() {
        return new FolderProjectPage(getDriver());
    }

    @Step("Type '{name}' to Display Name field on Configuration page")
    public FolderConfigPage enterConfigurationName(String name) {
        displayNameField.sendKeys(name);

        return this;
    }
}
