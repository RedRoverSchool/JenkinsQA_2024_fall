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

import java.time.Duration;

public class DropDawnChevronTest extends BaseTest {

    @Test
    public void testDropDawnNewItem () {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.linkText("New Item")).click();

        getDriver().findElement(By.id("name")).sendKeys("TestDropDawnChevron");
        getDriver().findElement(By.cssSelector("#jenkins")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("description")).sendKeys("TestDropDawnChevron");

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(0, 2000);");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        Actions actions = new Actions(getDriver());

        WebElement mainButton = getDriver().findElement(By.xpath("//*[@id='job_TestDropDawnChevron']/td[3]/a/span"));
        actions.moveToElement(mainButton).perform();

        WebElement hiddenButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='job_TestDropDawnChevron']/td[3]/a/button")));
        actions.moveToElement(hiddenButton)
                .pause(java.time.Duration.ofSeconds(1)).click().perform();

        String searchText = "\"\n" +
                "          Delete Pipeline\n" +
                "                    \n" +
                "          \n" +
                "      \"";

        WebElement dropdown = (WebElement) js.executeScript(
                "return Array.from(document.querySelectorAll('*')).find(element => " +
                        "element.textContent.trim().replace(/\\s+/g, ' ') === arguments[0]);", searchText);

        if (dropdown != null) {
            actions.moveToElement(dropdown)
                    .pause(java.time.Duration.ofSeconds(1))
                    .click()
                    .perform();
        } else {
            System.out.println("Элемент с текстом '" + searchText + "' не найден.");
        }

////        WebElement dropDownDel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tippy-6']/div/div/div")));
////        actions.moveToElement(dropDownDel)
////                .pause(java.time.Duration.ofSeconds(2)).click().perform();
//
//        WebElement modalWin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jenkins']/dialog")));
//        actions.moveToElement(modalWin).pause(java.time.Duration.ofSeconds(2)).perform();
//        WebElement yesButton = modalWin.findElement(By.xpath("//*[@id='jenkins']/dialog/div[3]/button[1]"));
//        yesButton.click();
//
//        boolean isElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='tippy-6']/div/div/div/button[2]")));
//        Assert.assertTrue(isElementInvisible);
    }
}
