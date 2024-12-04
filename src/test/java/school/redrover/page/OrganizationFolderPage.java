package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;

public class OrganizationFolderPage extends BasePage {

    public OrganizationFolderPage(WebDriver driver) {
        super(driver);
    }

    private final By GET_CONFIGURE = By.xpath("//a[@href='./configure']");
    private final By NAME_H1 = By.tagName("h1");

    public OrganizationFolderConfigurationPage clickConfigure() {
        getDriver().findElement(GET_CONFIGURE).click();

        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public String getName() {
        return getDriver().findElement(NAME_H1).getText();
    }
}
