package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.TestUtils;

import java.time.Duration;

public class DropDawnChevronTest extends BaseTest {

    @Test
    public void testDropDawnNewItem() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(getDriver());
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        WebElement newItemLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("New Item")));
        newItemLink.click();

        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        nameInput.sendKeys("TestDropDawnChevron");

        WebElement jenkinsLogo = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#jenkins")));
        jenkinsLogo.click();

        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button")));
        okButton.click();

        WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
        descriptionInput.sendKeys("TestDropDawnChevron");

        js.executeScript("window.scrollBy(0, 2000);");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("Submit")));
        submitButton.click();

        WebElement breadcrumbsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='breadcrumbs']/li[1]/a")));
        breadcrumbsLink.click();

        WebElement mainButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='job_TestDropDawnChevron']/td[3]/a/span")));
        actions.moveToElement(mainButton).pause(5000).perform();

        WebElement hiddenButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='job_TestDropDawnChevron']/td[3]/a/button")));
        actions.moveToElement(hiddenButton).pause(Duration.ofSeconds(3)).click().perform();

        String searchText = "Delete Pipeline";

        WebElement dropdownItem = (WebElement) js.executeScript(
                "return Array.from(document.querySelectorAll(arguments[0])).find(element => " +
                        "element.textContent.trim().replace(/\\s+/g, ' ') === arguments[1]);",
                "*", searchText
        );

        if (dropdownItem != null) {
            TestUtils.moveAndClickWithJavaScript(getDriver(), dropdownItem);
        } else {
            Assert.fail("Элемент с текстом '" + searchText + "' не найден.");
        }

        WebElement modalWin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jenkins']/dialog")));
        WebElement yesButton = modalWin.findElement(By.xpath(".//button[1]"));
        yesButton.click();

        boolean isElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='tippy-6']/div/div/div/button[2]")));
        Assert.assertTrue(isElementInvisible, "Элемент с подтверждением удаления все еще видим.");
    }
}
