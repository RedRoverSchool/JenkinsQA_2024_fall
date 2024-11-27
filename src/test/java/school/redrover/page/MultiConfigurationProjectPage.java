package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BaseProjectPage;

public class MultiConfigurationProjectPage extends BaseProjectPage {
    public MultiConfigurationProjectPage(WebDriver driver) {
        super(driver);
    }

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


}
