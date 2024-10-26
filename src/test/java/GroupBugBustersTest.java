import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupBugBustersTest{

    private WebDriver driver;

    @BeforeTest // Den
    public void baseURL() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--start-maximized");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test //Den
    public void testSearchCheeseInVkusville(){
        driver.get("https://vkusvill.ru/");

        WebElement search = driver.findElement(By.cssSelector(".HeaderSearchBlock__Input.js-vv21-search__search-input.js-search-autotyping"));
        search.sendKeys("Сыр");

        WebElement searchButton = driver.findElement(By.cssSelector(".HeaderSearchBlock__BtnSearchImg.js-vv21-seacrh__icon-btn"));
        searchButton.click();

        WebElement title = driver.findElement(By.cssSelector(".VV21_SearchPage__Title.h4_desktop.h4_tablet.js-search-total-num-container"));
        Assert.assertEquals(title.getText().substring(title.getText().indexOf("Сыр")), "Сыр»");
    }


    @Test //Den
    public void test() {
        driver.get("https://tasnet.uz"); // - Открыть страницу
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Tasnet - Wi-Fi authorization via SMS and social networks Hotspot", "Tittle doesn't match");
    }
    @Test //Den
    public void test2(){
        driver.get("https://tasnet.uz"); // - Открыть страницу
        WebElement cost = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[1]/ul/li[3]/a"));
        cost.click();

        // regex для проверки ссылки сайта. Включает в себя https://tasnet.uz/pricing и https://tasnet.uz/en/pricing
        Assert.assertTrue(driver.getCurrentUrl().matches("^https://tasnet.uz(/en)?/pricing"), "Wrong page");
    }

    @Test //Den
    public void test3() {
        driver.get("https://tasnet.uz/business");
        WebElement searchEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        searchEmail.sendKeys("Java", Keys.ENTER);
    }

    @AfterTest //Den
    public void closeBrowse() {
        driver.quit();
    }
}
