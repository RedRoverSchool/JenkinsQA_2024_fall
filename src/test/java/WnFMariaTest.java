import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;



    public class WnFMariaTest {

        @Test
        public void test() {

            WebDriver driver = new ChromeDriver();
            driver.get("https://shop.mango.com/uz/en");
            driver.getTitle();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            // Нашли кнопку с кукисами и нажали "accept all cookies"
            WebElement submitButton = driver.findElement(By.xpath("//*[@id=\"cookies.button.acceptAll\"]/span"));
            submitButton.click();

            // Нашли кнопку Woman и перешли в раздел женской одежды
            WebElement submitButton2 = driver.findElement(By.xpath("//*[@id=\"app\"]/main/div/div[1]/div/ul/li[1]/a"));
            submitButton2.click();

            // Сравниваем URL ожидаемый и фактический
            String expectedUrl = "https://shop.mango.com/uz/en/c/women/promotion_7914393e";
            String actualUrl = driver.getCurrentUrl();
            Assert.assertEquals(actualUrl, expectedUrl, "URL не соответствует ожидаемому!");

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            // Ищем кнопку "добавить в избранное" (сердечко wishlist под товаром)
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebElement favouritesElement = driver.findElement(By.xpath("/html/body/div/main/div/div[4]/ul/li[2]/div/div/div[1]/div[2]/div[1]/div/button"));
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            favouritesElement.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            Assert.assertNotNull(favouritesElement, "Кнопка 'Добавить в избранное' не найдена!");

            // Проверяем, что элемент видим и доступен для клика
            Assert.assertTrue(favouritesElement.isDisplayed(), "Кнопка 'Добавить в избранное' не видима!");
            Assert.assertTrue(favouritesElement.isEnabled(), "Кнопка 'Добавить в избранное' недоступна для клика!");


            // Ищем и нажимаем кнопку wishlist
            WebElement wishListElement = driver.findElement(By.xpath("/html/body/div/header/div[1]/div[3]/ul/li[3]/a"));
            wishListElement.click();

            // Сравниваем второй URL
            String expectedUrl2 = "https://shop.mango.com/uz/en/favorites";
            String actualUrl2 = driver.getCurrentUrl();
            Assert.assertEquals(actualUrl2, expectedUrl2, "URL не соответствует ожидаемому!");

            driver.quit();
        }
    }