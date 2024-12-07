package school.redrover.page.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.page.CreateNewItemPage;

public abstract class BaseCreatePage<Self extends BaseCreatePage<?>> extends BasePage {

    @FindBy(id = "ok-button")
    private WebElement okButton;

    public BaseCreatePage(WebDriver driver) {
        super(driver);
    }

    public CreateNewItemPage selectTypeProject(String typeProject) {
        getDriver().findElement(By.xpath("//span[text()='" + typeProject + "']")).click();

        return new CreateNewItemPage(getDriver());
    }

    public Self clickOkButton() {
        okButton.click();

        return (Self) this;
    }
}
