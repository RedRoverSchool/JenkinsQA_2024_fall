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
        int i = RandomValue.getRandomValue(1, 9);

        String path = String.format("//ul/li/div/div/div[@aria-label=\"%s\"]", CoffeeList.getGoffeeNameActual(i));
        driver.findElement(By.xpath(path)).click();

        String Cart = driver.findElement(By.className("pay")).getText();
        int index = Cart.indexOf("$");
        Cart = Cart.substring(index + 1, Cart.length());

        System.out.println(Cart);


    }


}
