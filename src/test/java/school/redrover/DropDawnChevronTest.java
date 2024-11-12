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
    public void testDropDawnNewItem() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(getDriver());
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        // Переход к странице создания нового элемента и ввод его имени
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.id("name")).sendKeys("TestDropDawnChevron");
        getDriver().findElement(By.cssSelector("#jenkins")).click();
        getDriver().findElement(By.id("ok-button")).click();

        // Ввод описания и сохранение
        getDriver().findElement(By.name("description")).sendKeys("TestDropDawnChevron");
        js.executeScript("window.scrollBy(0, 2000);");  // Скроллинг вниз страницы
        getDriver().findElement(By.name("Submit")).click();

        // Возвращение к главной странице
        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[1]/a")).click();

        // Наведение на основную кнопку и раскрытие выпадающего меню
        WebElement mainButton = getDriver().findElement(By.xpath("//*[@id='job_TestDropDawnChevron']/td[3]/a/span"));
        actions.moveToElement(mainButton).perform();

        WebElement hiddenButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='job_TestDropDawnChevron']/td[3]/a/button")));
        actions.moveToElement(hiddenButton).pause(Duration.ofSeconds(1)).click().perform();

        // Текст для поиска элемента в выпадающем меню
        String searchText = "Delete Pipeline";

        // Поиск и клик по элементу в выпадающем списке с помощью JavaScript
        WebElement dropdownItem = (WebElement) js.executeScript(
                "return Array.from(document.querySelectorAll(arguments[0])).find(element => " +
                        "element.textContent.trim().replace(/\\s+/g, ' ') === arguments[1]);",
                "*", searchText
        );

        // Проверка наличия элемента и клик
        if (dropdownItem != null) {
            actions.moveToElement(dropdownItem).pause(Duration.ofSeconds(2)).click().perform();
        } else {
            Assert.fail("Элемент с текстом '" + searchText + "' не найден.");
        }

        // Ожидание появления окна подтверждения удаления и клик по кнопке подтверждения
        WebElement modalWin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='jenkins']/dialog")));
        WebElement yesButton = modalWin.findElement(By.xpath(".//button[1]"));
        yesButton.click();

        // Проверка, что элемент в меню стал невидимым после удаления
        boolean isElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id='tippy-6']/div/div/div/button[2]")));
        Assert.assertTrue(isElementInvisible, "Элемент с подтверждением удаления все еще видим.");
    }
}
