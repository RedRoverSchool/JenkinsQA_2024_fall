package AliakseiKTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeeList {

    static WebDriver driver = CoffeeCart.driver;

    //Получаем список вэбэлементов (название кофе и цена) и записываем в лист CoffeeList
    public static List<WebElement> getCoffeeListActual(){
        List<WebElement> coffeeListActual = driver.findElements(By.xpath("//div[2]/ul/li/h4"));
        return coffeeListActual;
    }

    //Получаем цену кофе из листа CoffeeList
    public static String getCoffeePriceActual(int i){
        String path = String.format("//div[2]/ul/li[%s]/h4/small", i+1);
        String price = driver.findElement(By.xpath(path)).getText();
         //= Coffee.findElement().getText();
        price = price.substring(1);
        return price;
    }

    //Получаем название кофе из листа CoffeeList
    public static String getGoffeeNameActual(int i){
        String Coffee = getCoffeeListActual().get(i).getText();
        int index = Coffee.indexOf("\n");
        Coffee = Coffee.substring(0, index);
        return Coffee;
    }

    //Получаем количество элементов в листе CoffeeList
    public static int getCoffeeListActualSize(){
        int size = getCoffeeListActual().size();
        return size;
    }

    //Map содержащий ожидаемые значения названия кофе и цены.
    //На сайте эти значения меняются несколько раз за день.
    public static Map<String, String> getCoffeMapExpected(){
        Map<String, String> coffeeMapExpected = new HashMap<>();
        coffeeMapExpected.put("Espresso", "10.00");
        coffeeMapExpected.put("Espresso Macchiato", "10.00");
        coffeeMapExpected.put("Cappuccino", "19.00");
        coffeeMapExpected.put("Mocha", "8.00");
        coffeeMapExpected.put("Flat White", "18.00");
        coffeeMapExpected.put("Americano", "7.00");
        coffeeMapExpected.put("Cafe Latte", "10.00");
        coffeeMapExpected.put("Espresso Con Panna", "14.00");
        coffeeMapExpected.put("Cafe Breve", "10.00");
        return coffeeMapExpected;
    }

    //Получаем количество элементов в map CoffeeMapExpected
    public static int getCoffeeMapSizeExpected(){
        return getCoffeMapExpected().size();
    }
}

