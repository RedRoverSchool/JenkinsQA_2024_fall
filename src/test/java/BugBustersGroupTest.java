import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BugBustersGroupTest {

    @Test
    public void testSearchCheeseInVkusville(){
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://vkusvill.ru/");

        WebElement search = driver.findElement(By.cssSelector(".HeaderSearchBlock__Input.js-vv21-search__search-input.js-search-autotyping"));

        search.sendKeys("Сыр");

        WebElement searchButton = driver.findElement(By.cssSelector(".HeaderSearchBlock__BtnSearchImg.js-vv21-seacrh__icon-btn"));
        searchButton.click();

        WebElement title = driver.findElement(By.cssSelector(".VV21_SearchPage__Title.h4_desktop.h4_tablet.js-search-total-num-container"));
        Assert.assertEquals(title.getText().substring(title.getText().indexOf("Сыр")), "Сыр»");
        driver.quit();
    }
}
