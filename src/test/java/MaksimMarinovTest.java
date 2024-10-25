import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.WatchEvent;

public class MaksimMarinovTest {

    @Test
    public void testGoToPageCallCenterSchedule(){

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        String TEXT_OF_TITLE = "Войдите с Яндекс ID";

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.drive2.ru/");

        WebElement login = driver.findElement(By.xpath("//div[@class='o-group']/a[1]"));
        login.click();

        WebElement yandex = driver.findElement(By.xpath("//div[@CLASS='c-button__icon m-icon i-yandex']"));
        yandex.click();

        WebElement titleOfYandexId = driver.findElement(By.xpath("//h1[@CLASS='Title Title_align_center Title_view_default']"));
        String actualResult = titleOfYandexId.getText();

        Assert.assertEquals(actualResult,TEXT_OF_TITLE);
    }
}
