import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OlegGTest {

    @Test
    public static void searchCartCompareTest() throws InterruptedException {

        int priceWithWalletFromSearch;
        int priceWithWalletCart;
        int cartCount;
        String goodInSearch;
        String goodBrandInSearch;
        String goodFullNameInSearch;

        ChromeDriver driver = new ChromeDriver();

        driver.get("https://www.wildberries.ru/");
        Thread.sleep(500);

        driver.findElement(By.id("searchInput")).sendKeys("Xiaomi 14T телефон");
        Thread.sleep(500);

        driver.findElement(By.id("applySearchBtn")).click();
        Thread.sleep(500);

        goodBrandInSearch = driver.findElement(By.xpath("//article[@id='c268672333']" +
                "//span[@class='product-card__brand']")).getText();
        goodInSearch = driver.findElement(By.xpath("//article[@id='c268672333']" +
                        "//span[text()='14T 12 512GB, Blue Black            ']")).getText().substring(2);
        goodFullNameInSearch = goodBrandInSearch + " " + goodInSearch;

        priceWithWalletFromSearch = Integer.parseInt(driver.findElement(By.xpath("//article" +
                        "[@id='c268672333']//span/ins[@class='price__lower-price wallet-price']"))
                .getText().replaceAll(" ", "").replaceAll("₽", ""));

        if(driver.findElement(By.xpath("/html/body/div[4]/div/div/div/button[@class='cookies__btn btn-minor-md']")) != null) {
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div/button[@class='cookies__btn btn-minor-md']")).click();
        }
        Thread.sleep(500);

        driver.findElement(By.xpath("//article[@id='c268672333']//a[@class='product-card__add-basket j-add-to-basket btn-main']")).click();
        Thread.sleep(500);

        cartCount = Integer.parseInt(driver.findElement(By.xpath("//*[@id='basketContent']//span[@class='navbar-pc__notify']"))
                .getText());

        driver.findElement(By.xpath("//*[@id='basketContent']//a[@data-wba-header-name='Cart']")).click();
        Thread.sleep(3000);

        String goodCart = driver.findElement(By.xpath("//span[@class='good-info__good-name']")).getText().replaceAll("/", " ");
        priceWithWalletCart = Integer.parseInt(driver.findElement(By.xpath("//div[@class='list-item__price-wallet']"))
                .getText().replaceAll(" ", "").replaceAll("₽", ""));

        Assert.assertEquals(cartCount, 1);
        Assert.assertEquals(priceWithWalletFromSearch, priceWithWalletCart);
        Assert.assertEquals(goodCart, goodFullNameInSearch);

        driver.quit();

    }

    @Test
    public static void cartSummingTest() throws InterruptedException {

        int priceFirstItem;
        int priceSecondItem;
        int cartCount;
        int overallCartPrice;

        ChromeDriver driver = new ChromeDriver();

        driver.get("https://www.wildberries.ru/");
        Thread.sleep(500);

        driver.findElement(By.id("searchInput")).sendKeys("Xiaomi 14T телефон");
        Thread.sleep(500);

        driver.findElement(By.id("applySearchBtn")).click();
        Thread.sleep(500);

        priceSecondItem = Integer.parseInt(driver.findElement(By.xpath("//article[@id='c266736655']" +
                        "//span/ins[@class='price__lower-price']"))
                .getText().replaceAll(" ", "").replaceAll("₽", ""));

        if(driver.findElement(By.xpath("/html/body/div[4]/div/div/div/button[@class='cookies__btn btn-minor-md']")) != null) {
            driver.findElement(By.xpath("/html/body/div[4]/div/div/div/button[@class='cookies__btn btn-minor-md']")).click();
        }
        Thread.sleep(500);

        driver.findElement(By.xpath("//article[@id='c268672333']//a[@class='product-card__add-basket j-" +
                "add-to-basket btn-main']")).click();
        Thread.sleep(500);

        driver.findElement(By.xpath("//article[@id='c266736655']//a[@class='product-card__add-basket j-" +
                "add-to-basket btn-main']")).click();
        Thread.sleep(500);

        cartCount = Integer.parseInt(driver.findElement(By.xpath("//*[@id='basketContent']" +
                        "//span[@class='navbar-pc__notify']")).getText());

        driver.findElement(By.xpath("//*[@id='basketContent']//a[@data-wba-header-name='Cart']")).click();
        Thread.sleep(3000);

        priceFirstItem = Integer.parseInt(driver.findElement(By.xpath("//div[@class='list-item__" +
                        "price-new wallet']")).getText().replaceAll(" ", "")
                .replaceAll("₽", ""));
        overallCartPrice = Integer.parseInt(driver.findElement(By.xpath("//p/span[2]/span[starts-with" +
                        "(@data-link, '{formatMoneyAnim')]")).getText().replaceAll(" ", "")
                .replaceAll("₽", ""));

        Assert.assertEquals(cartCount, 2);
        Assert.assertEquals(priceFirstItem + priceSecondItem, overallCartPrice);

        driver.quit();

    }
}
