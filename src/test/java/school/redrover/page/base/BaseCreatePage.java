package school.redrover.page.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.*;

public abstract class BaseCreatePage<Self extends BaseConfigPage<?, ?>> extends BasePage {

    private static String type;

    @FindBy(id = "ok-button")
    WebElement okButton;

    public BaseCreatePage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage selectTypeProject(String typeProject) {
        type = typeProject;
        getDriver().findElement(By.xpath("//span[text()='" + typeProject + "']")).click();

        return (CreateNewItemPage) this;
    }

    public Self clickOkButton() {
        okButton.click();

        if (type.equals("Freestyle project")) {
            return (Self) new FreestyleConfigPage(getDriver());
        } else if (type.equals("Pipeline")) {
            return (Self) new PipelineConfigPage(getDriver());
        } else if (type.equals("Multi-configuration project")) {
            return (Self) new MultiConfigurationConfigPage(getDriver());
        } else if (type.equals("Folder")) {
            return (Self) new FolderConfigPage(getDriver());
        } else if (type.equals("Multibranch Pipeline")) {
            return (Self) new MultibranchPipelineConfigPage(getDriver());
        } else {
            return (Self) new OrganizationFolderConfigPage(getDriver());
        }
    }
}
