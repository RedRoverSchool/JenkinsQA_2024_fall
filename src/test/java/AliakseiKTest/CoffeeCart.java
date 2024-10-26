package AliakseiKTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CoffeeCart {
    public String baseUrl = "https://coffee-cart.app/";
    static WebDriver driver;

    @BeforeTest
    public void launchBrowser() {
        driver = new ChromeDriver();
        driver.get(baseUrl);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    @Test //Проверяем title
    public void testTitle() {
        driver.getTitle();
        Assert.assertEquals(driver.getTitle(), "Coffee cart");
    }

    @Test //Проверяем оличество сущностей и пары значений имя - цена
    public void testList() {
        Map<String, String> coffeeMapActual = new HashMap<>();

        for (int i = 0; i < CoffeeList.getCoffeeListActualSize(); i++) {
            coffeeMapActual.put(CoffeeList.getGoffeeNameActual(i), CoffeeList.getCoffeePriceActual(i));
        }

        Assert.assertEquals(CoffeeList.getCoffeeMapSizeExpected(), coffeeMapActual.size());
        Assert.assertEquals(CoffeeList.getCoffeMapExpected(), coffeeMapActual);
    }

    @Test //Проверяем стоимость товаров в корзине
    public void testCart() {
        int i = RandomValue.getRandomValue(0, 8);

        String coffeeItem = String.format("//ul/li/div/div/div[@aria-label=\"%s\"]", CoffeeList.getGoffeeNameActual(i));
        driver.findElement(By.xpath(coffeeItem)).click();

        String cart = driver.findElement(By.className("pay")).getText();
        int index = cart.indexOf("$");
        cart = cart.substring(index + 1, cart.length());

        Assert.assertEquals(cart,CoffeeList.getCoffeePriceActual(i));



    }


}
