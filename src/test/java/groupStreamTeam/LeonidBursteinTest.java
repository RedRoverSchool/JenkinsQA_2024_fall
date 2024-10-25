package groupStreamTeam;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LeonidBursteinTest {
    String url = "https://www.saucedemo.com/";

    @Test
    public void testLogin() {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(driver.findElement(By.className("title")).getText(), "Products");
        driver.quit();
    }

    @Test
    public void testItemPage() {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//DIV/*[@data-test='inventory-item-name']")).getText(), "Sauce Labs Backpack");
        driver.quit();
    }

    @Test
    public void testAddToCart() {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span")).getText(), "1");
        driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div")).getText(), "Sauce Labs Backpack");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[1]")).getText(), "1");
        driver.quit();
    }

}


