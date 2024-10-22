import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class KateATest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAddToCart() throws InterruptedException {
        driver.get("https://coffee-cart.app/");

        Thread.sleep(1000);

        List<WebElement> cups = driver.findElements(By.className("cup-body"));
        WebElement anyCup = cups.get(0);
        anyCup.click();

        Thread.sleep(1000);

        WebElement cartPage = driver.findElement(
                By.xpath("//*[@aria-label='Cart page']"));
        cartPage.click();

        Thread.sleep(1000);

        WebElement listCart = driver.findElement(
                By.xpath("//div[@data-v-8965af83 and text()='Espresso']"));
        Assert.assertEquals(listCart.getText(), "Espresso");
    }

    @Test
    public void testCartEmpty() throws InterruptedException {
        driver.get("https://coffee-cart.app/");

        Thread.sleep(1000);

        WebElement cartPage = driver.findElement(
                By.xpath("//*[@aria-label='Cart page']"));
        cartPage.click();

        Thread.sleep(1000);

        WebElement emptyCart = driver.findElement(
                By.xpath("//p[@data-v-8965af83]"));
        Assert.assertEquals(emptyCart.getText(), "No coffee, go add some.");
    }

    @Test
    public void testCostAmount() throws InterruptedException {
        driver.get("https://coffee-cart.app/");

        Thread.sleep(1000);

        List<WebElement> prices = driver.findElements(By.xpath("//small[@data-v-a9662a08]"));
        String priceCoffeePage = prices.get(0).getText();

        WebElement pageCoffee = driver.findElement(By.className("cup-body"));
        pageCoffee.click();

        Thread.sleep(1000);

        WebElement cartPage = driver.findElement(
                By.xpath("//*[@aria-label='Cart page']"));
        cartPage.click();

        Thread.sleep(1000);

        WebElement priceCart = driver.findElement(
                By.xpath("//li[@class='list-item']/div[3]"));
        String priceCoffeeCart = priceCart.getText();

        System.out.println(priceCoffeeCart);
        System.out.println(priceCoffeePage);

        Assert.assertEquals(priceCoffeePage, priceCoffeeCart);
    }

    @Test
    public void testHoverOverTotal() throws InterruptedException {
        driver.get("https://coffee-cart.app/");

        Actions actions = new Actions(driver);

        List<WebElement> cups = driver.findElements(
                By.className("cup-body"));
        WebElement anyCup = cups.get(0);
        anyCup.click();

        Thread.sleep(1000);

        WebElement total = driver.findElement(
                By.xpath("//*[@aria-label='Proceed to checkout']"));
        actions.moveToElement(total).perform();

        Thread.sleep(1000);

        WebElement totalPreview = driver.findElement(
                By.className("cart-preview"));

        Assert.assertTrue(totalPreview.isDisplayed());
    }

    @Test
    public void testHoverOverEmpty() throws InterruptedException {
        driver.get("https://coffee-cart.app/");

        Actions action = new Actions(driver);

        WebElement total = driver.findElement(
                By.xpath("//*[@aria-label='Proceed to checkout']"));

        action.moveToElement(total).perform();

        Thread.sleep(2000);

        boolean isPreviewDisplayed = !driver.findElements(
                By.className("cart-preview")).isEmpty();
        Assert.assertFalse(isPreviewDisplayed);
    }

    @Test
    public void testButtonTotalModal() throws InterruptedException {
        driver.get("https://coffee-cart.app/");

        WebElement buttonTotal = driver.findElement(
                By.xpath("//*[@aria-label='Proceed to checkout']")
        );
        buttonTotal.click();

        Thread.sleep(2000);

        WebElement headerModal = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(headerModal.getText(), "Payment details");
    }
}
