import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class IrinaNappTest {
    WebDriver driver = new ChromeDriver();

    @BeforeTest
    public void testUrl(){
        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().window();
    }

    @Test
    public void testHomePage () {
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, "Home Page");
    }

    @Test
    public void testAddCart() throws InterruptedException {
        // Переход на категорию товара
        WebElement buttonNavigation = driver.findElement(By.xpath("//*[@id='ui-id-2']/li[1]"));
        buttonNavigation.click();

        // Переход на конкретную категорию товаров
        WebElement linkButton = driver.findElement(By.xpath("//*[@id='maincontent']/div[4]/div[2]/div/div/ul[1]/li[1]/a"));
        linkButton.click();

        // Нажатие на конкретный товар
        WebElement itemButton = driver.findElement(By.xpath("//*[@id='maincontent']/div[3]/div[1]/div[3]/ol/li[1]"));
        itemButton.click();

        // Выбор размера товара
        WebElement sizeButton = driver.findElement(By.xpath("//*[@id='option-label-size-143-item-166']"));
        sizeButton.click();

        // Выбор цвета товара
        WebElement colourButton = driver.findElement(By.xpath("//*[@id='option-label-color-93-item-53']"));
        colourButton.click();

        // Нажатие кнопки "Добавить в корзину"
        WebElement addCartButton = driver.findElement(By.xpath("//*[@id='product-addtocart-button']"));
        addCartButton.click();

        // Используем явное ожидание для появления сообщения об успешном добавлении товара в корзину
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='maincontent']/div[1]/div[2]/div/div")));

        // Проверяем, что сообщение содержит текст о добавлении товара в корзину
        String expectedMessage = "You added Circe Hooded Ice Fleece to your shopping cart.";
        String actualMessage = successMessage.getText();

        // Проверка того, что сообщение содержит нужный текст
        Assert.assertTrue(actualMessage.contains("You added Circe Hooded Ice Fleece to your shopping cart."),
                "Ожидалось сообщение: " + expectedMessage + ", но получено: " + actualMessage);
    }

    @AfterTest
    public void closeBrowser(){
        driver.quit();
    }


}
