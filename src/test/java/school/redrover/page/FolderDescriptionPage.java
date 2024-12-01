package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;

public class FolderDescriptionPage extends BasePage {

    public FolderDescriptionPage(WebDriver driver) {
        super(driver);
    }

    public FolderDescriptionPage createFolder (String folderName, String buttonXpath) {
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[1]/span/a")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath(buttonXpath)).click();
        getDriver().findElement(By.id("ok-button")).click();
        return this;
    };

    public FolderDescriptionPage openFolder (String folderName) {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='job_" + folderName + "']/td[3]/a/span"))).click();
        return this;
    };

    public FolderDescriptionPage clickOnSave () {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();
        return this;
    }

    public FolderDescriptionPage enablePreview () {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='description']/form/div[1]/div[1]/div[1]/a[1]"))).click();
        return this;
    }

    public FolderDescriptionPage clearDescription () {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("description"))).clear();
        return this;
    }

    public FolderDescriptionPage provideDescription (String descriptionText) {
        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("description"))).sendKeys(descriptionText);
        return this;
    }

    public String getCurrentDescription() {
        return getDriver().findElement(By.xpath("//*[@id='description']/div[1]")).getText();
    }

    public String getDescriptionViaPreview () {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='description']/form/div[1]/div[1]/div[2]"))).getText();
    }
}
