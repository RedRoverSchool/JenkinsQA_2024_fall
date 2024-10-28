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

public class GroupBugBustersTest {

    WebDriver driver = new ChromeDriver();

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

    @BeforeTest // Den
    public void baseURL() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");

        driver.get("https://tasnet.uz"); // - Открыть страницу
        driver.manage().window().maximize(); // - Открывает всегда во весь экран
    }

    @Test //Den
    public void test() {
        String pageTitle = driver.getTitle();
        System.out.println(pageTitle);
    }
    @Test //Den
    public void test2() {
        WebElement cost = driver.findElement(By.xpath("//*[@id=\"__next\"]/main/div[1]/ul/li[3]/a"));
        cost.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test //Den
    public void test3() {

        driver.get("https://tasnet.uz/business");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement searchEmail = driver.findElement(By.xpath("//*[@id=\"email\"]"));
        searchEmail.sendKeys("Java", Keys.ENTER);
    }

    @AfterTest //Den
    public void closeBrowse() {
        driver.quit();
    }

    @Test
    public void loginTest() {
// Олег Шулаев тест
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/login");

        WebElement userNameBox = driver.findElement(By.xpath("//*[@id=\"username\"]"));
        userNameBox.sendKeys("tomsmith");

        WebElement passwordBox = driver.findElement(By.xpath("//*[@id=\"password\"]"));
        passwordBox.sendKeys("SuperSecretPassword!");

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login\"]/button"));
        loginButton.click();

        WebElement message = driver.findElement(By.xpath("//*[@id=\"content\"]/div/h4"));
        Assert.assertEquals(message.getText(), "Welcome to the Secure Area. When you are done click logout below.");

        driver.quit();

        // проверка пуш
    }
}
