package school.redrover.page;

<<<<<<< HEAD
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage {
=======
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage<MultiConfigurationProjectPage> {

>>>>>>> e7357be367a870d0f61f746714286a6795ede335
    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

<<<<<<< HEAD
    By getDescriptionLink = By.xpath("//a[@id='description-link']");
    By getTextArea = By.xpath("//textarea[@name = 'description']");
    By getSubmitButton = By.xpath("//div/button[@name = 'Submit']");
    By getDescriptionsTitle = By.xpath("//div[@id='description']/div[1]");

    public MultiConfigurationProjectPage addDescriptions(String Descriptions) {
        getDriver().findElement(getDescriptionLink).click();
        getDriver().findElement(getTextArea).sendKeys(Descriptions);
        getDriver().findElement(getSubmitButton).submit();

        return this;
    }

    public String showDescriptions() {
        return getDriver().findElement(getDescriptionsTitle).getText();
    }


=======
>>>>>>> e7357be367a870d0f61f746714286a6795ede335
}
