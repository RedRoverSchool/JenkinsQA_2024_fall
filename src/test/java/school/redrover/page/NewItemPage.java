package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

public class NewItemPage extends BasePage {
    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public enum ItemType {
        FOLDER("Folder"),
        FREESTYLE_PROJECT("Freestyle project");

        private final String itemName;

        ItemType(String itemName) {
            this.itemName = itemName;
        }

        public String getItemName() {
            return itemName;
        }
    }

    public NewItemPage enterItemName(String name) {
        getDriver().findElement(By.id("name")).sendKeys(name);

        return this;
    }

    public NewItemPage scrollToBottom() {
        TestUtils.scrollToBottom(getDriver());

        return this;
    }

    public ConfigurationPage selectProjectTypeAndSave(ItemType itemType) {
        getDriver().findElement(By.xpath("//span[text()='%s']".formatted(itemType.getItemName()))).click();
        getDriver().findElement(By.id("ok-button")).click();

        return new ConfigurationPage(getDriver());
    }
}
