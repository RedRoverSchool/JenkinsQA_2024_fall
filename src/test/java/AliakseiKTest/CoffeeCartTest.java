package AliakseiKTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.*;

public class CoffeeCartTest {
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

    @Ignore
    @Test //Проверяем title
    public void testTitle() {
        driver.getTitle();
        Assert.assertEquals(driver.getTitle(), "Coffee cart");
    }

    @Ignore
    @Test //Проверяем количество сущностей и пары значений имя - цена
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
        int randomItem = RandomValue.getRandomValue(0, 8);
        double expectedCartSumm = 0.2d;
        List<String> orderListActual = new ArrayList<>();
        List<String> orderListExpected = new ArrayList<>();
        Actions action = new Actions(driver);

        String coffeeItem = String.format("//ul/li/div/div/div[@aria-label=\"%s\"]", CoffeeList.getGoffeeNameActual(randomItem));
        driver.findElement(By.xpath(coffeeItem)).click();
        expectedCartSumm = Double.parseDouble(CoffeeList.getCoffeePriceActual(randomItem));
        orderListExpected.add(CoffeeList.getGoffeeNameActual(randomItem));

        //Проверяем общую сумму товаров в корзине после добавления туда одной покупки
        Assert.assertEquals(CoffeeList.getActualCartSumm(), expectedCartSumm);

        randomItem = RandomValue.getRandomValue(0, 8);
        coffeeItem = String.format("//ul/li/div/div/div[@aria-label=\"%s\"]", CoffeeList.getGoffeeNameActual(randomItem));
        driver.findElement(By.xpath(coffeeItem)).click();
        expectedCartSumm += Double.parseDouble(CoffeeList.getCoffeePriceActual(randomItem));
        orderListExpected.add(CoffeeList.getGoffeeNameActual(randomItem));
        Collections.sort(orderListExpected);

        //Проверяем общую сумму товаров в корзине после добавления туда второй покупки
        Assert.assertEquals(CoffeeList.getActualCartSumm(), expectedCartSumm);

        //Получаем список товаров в корзине
        action.moveToElement(driver.findElement(By.xpath("//div[1]/button"))).perform();
        for (WebElement i: driver.findElements(By.xpath("//ul/li/div[1]/span[1]"))){
            orderListActual.add(i.getText());
        }
        //Проверяем названия товаров в корзине
        Assert.assertEquals(orderListActual, orderListExpected);

        //Проверяем сумму товаров после увеличения количества товаров в корзине см помощью кнопки контекстного меню корзины

    }

}
