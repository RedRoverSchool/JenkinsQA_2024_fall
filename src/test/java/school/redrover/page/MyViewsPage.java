package school.redrover.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.page.base.BasePage;
import school.redrover.runner.TestUtils;

import static java.sql.DriverManager.getDriver;

public class MyViewsPage extends BasePage {

    public MyViewsPage(WebDriver driver) {
        super(driver);
    }

    public MyViewsPage deleteAnyJobViaChevron(String name) {

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath("//span[contains(text(), '" + name + "')]")))
                .perform();

        TestUtils.moveAndClickWithJavaScript(getDriver(),
                getDriver().findElement(By.xpath("//button[@data-href='http://localhost:8080/me/my-views/view/all/job/" + name + "/']")) );

        WebElement deleteButton = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(@href, 'doDelete')]")));
        actions
                .moveToElement(deleteButton)
                .click()
                .perform();

        WebElement buttonOk = getWait5().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath(
                "//button[@data-id= 'ok']"))));
        buttonOk.click();

        return this;
    }


}
